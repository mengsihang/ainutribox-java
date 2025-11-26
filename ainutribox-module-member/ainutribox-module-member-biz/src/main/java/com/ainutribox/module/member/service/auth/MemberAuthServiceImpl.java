package com.ainutribox.module.member.service.auth;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import com.ainutribox.framework.common.enums.CommonStatusEnum;
import com.ainutribox.framework.common.enums.TerminalEnum;
import com.ainutribox.framework.common.enums.UserTypeEnum;
import com.ainutribox.framework.common.util.monitor.TracerUtils;
import com.ainutribox.framework.common.util.servlet.ServletUtils;
import com.ainutribox.module.member.controller.app.auth.vo.*;
import com.ainutribox.module.member.convert.auth.AuthConvert;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.member.dal.mysql.user.MemberUserMapper;
import com.ainutribox.module.member.service.user.MemberUserService;
import com.ainutribox.module.system.api.logger.LoginLogApi;
import com.ainutribox.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.ainutribox.module.system.api.oauth2.OAuth2TokenApi;
import com.ainutribox.module.system.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import com.ainutribox.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import com.ainutribox.module.system.api.sms.SmsCodeApi;
import com.ainutribox.module.system.api.social.SocialClientApi;
import com.ainutribox.module.system.api.social.SocialUserApi;
import com.ainutribox.module.system.api.social.dto.SocialUserBindReqDTO;
import com.ainutribox.module.system.api.social.dto.SocialUserRespDTO;
import com.ainutribox.module.system.api.social.dto.SocialWxPhoneNumberInfoRespDTO;
import com.ainutribox.module.system.enums.logger.LoginLogTypeEnum;
import com.ainutribox.module.system.enums.logger.LoginResultEnum;
import com.ainutribox.module.system.enums.oauth2.OAuth2ClientConstants;
import com.ainutribox.module.system.enums.sms.SmsSceneEnum;
import com.ainutribox.module.system.enums.social.SocialTypeEnum;
import com.ainutribox.module.trade.api.AppBrokerageUser.AppBrokerageUserApi;
import com.ainutribox.module.trade.dal.dataobject.brokerage.BrokerageUserDO;
import com.ainutribox.module.trade.dal.dataobject.config.TradeConfigDO;
import com.ainutribox.module.trade.dal.mysql.brokerage.BrokerageUserMapper;
import com.ainutribox.module.trade.enums.brokerage.BrokerageEnabledConditionEnum;
import com.ainutribox.module.trade.service.config.TradeConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.Resource;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.framework.common.util.servlet.ServletUtils.getClientIP;
import static com.ainutribox.framework.web.core.util.WebFrameworkUtils.getTerminal;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 会员的认证 Service 接口
 *
 * @author 河南小泉山科技
 */
@Service
@Slf4j
public class MemberAuthServiceImpl implements MemberAuthService {

    @Resource
    private MemberUserService userService;
    @Resource
    private MemberUserMapper memberUserMapper;
    @Resource
    private AppBrokerageUserApi appBrokerageUserApi;
    @Resource
    private SmsCodeApi smsCodeApi;
    @Resource
    private LoginLogApi loginLogApi;
    @Resource
    private SocialUserApi socialUserApi;
    @Resource
    private SocialClientApi socialClientApi;
    @Resource
    private OAuth2TokenApi oauth2TokenApi;

    @Resource
    private TradeConfigService tradeConfigService;

    @Resource
    private BrokerageUserMapper brokerageUserMapper;
    @Override
    public AppAuthLoginRespVO login(AppAuthLoginReqVO reqVO) {
        // 使用手机 + 密码，进行登录。
        MemberUserDO user = login0(reqVO.getMobile(), reqVO.getPassword());

        // 如果 socialType 非空，说明需要绑定社交用户
        String openid = null;
        if (reqVO.getSocialType() != null) {
            openid = socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user, reqVO.getMobile(), LoginLogTypeEnum.LOGIN_MOBILE, openid);
    }

    @Override
    @Transactional
    public AppAuthLoginRespVO smsLogin(AppAuthSmsLoginReqVO reqVO) {
        // 校验验证码
        String userIp = getClientIP();
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.MEMBER_LOGIN.getScene(), userIp));

        // 获得获得注册用户
        MemberUserDO user = userService.createUserIfAbsent(reqVO.getMobile(), userIp, getTerminal());
        Assert.notNull(user, "获取用户失败，结果为空");

        // 如果 socialType 非空，说明需要绑定社交用户
        String openid = null;
        if (reqVO.getSocialType() != null) {
            openid = socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getSocialType(), reqVO.getSocialCode(), reqVO.getSocialState()));
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user, reqVO.getMobile(), LoginLogTypeEnum.LOGIN_SMS, openid);
    }

    @Override
    @Transactional
    public AppAuthLoginRespVO socialLogin(AppAuthSocialLoginReqVO reqVO) {
        // 使用 code 授权码，进行登录。然后，获得到绑定的用户编号
        SocialUserRespDTO socialUser = socialUserApi.getSocialUserByCode(UserTypeEnum.MEMBER.getValue(), reqVO.getType(),
                reqVO.getCode(), reqVO.getState());
        if (socialUser == null) {
            throw exception(AUTH_SOCIAL_USER_NOT_FOUND);
        }

        // 情况一：已绑定，直接读取用户信息
        MemberUserDO user;
        if (socialUser.getUserId() != null) {
            user = userService.getUser(socialUser.getUserId());
        // 情况二：未绑定，注册用户 + 绑定用户
        } else {
            user = userService.createUser(socialUser.getNickname(), socialUser.getAvatar(), getClientIP(), getTerminal());
            socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                    reqVO.getType(), reqVO.getCode(), reqVO.getState()));
        }
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user, user.getMobile(), LoginLogTypeEnum.LOGIN_SOCIAL, socialUser.getOpenid());
    }

    @Override
    public AppAuthLoginRespVO weixinMiniAppLogin(AppAuthWeixinMiniAppLoginReqVO reqVO) {
        // 获得对应的手机号信息
        SocialWxPhoneNumberInfoRespDTO phoneNumberInfo = socialClientApi.getWxMaPhoneNumberInfo(
                UserTypeEnum.MEMBER.getValue(), reqVO.getPhoneCode());
        Assert.notNull(phoneNumberInfo, "获得手机信息失败，结果为空");

        // 获得获得注册用户
        MemberUserDO user = userService.createUserIfAbsent(phoneNumberInfo.getPurePhoneNumber(),
                getClientIP(), TerminalEnum.WECHAT_MINI_PROGRAM.getTerminal());
        Assert.notNull(user, "获取用户失败，结果为空");


        TradeConfigDO tradeConfig = tradeConfigService.getTradeConfig();
        //人人分佣:把当前用户设置为推广员
        if (BrokerageEnabledConditionEnum.ALL.getCondition().equals(tradeConfig.getBrokerageEnabledCondition())) {
            if(ObjectUtil.isNull(brokerageUserMapper.selectById(user.getId()))) {
                BrokerageUserDO brokerageUserDO = new BrokerageUserDO().setId(user.getId()).setBrokerageEnabled(true).setBrokeragePrice(0).setFrozenPrice(0).setBrokerageTime(LocalDateTime.now());
                brokerageUserMapper.insert(brokerageUserDO);
            }
        }
        boolean isBrokerage = true;
        //根据type 处理绑定关系
        if (Objects.equals(reqVO.getType(), SocialTypeEnum.WECHAT_MEMBER_ID.getType())) {
            if(null == user.getMemberId() && null != reqVO.getUserId()){
                if(ObjectUtil.isNotEmpty(brokerageUserMapper.selectById(reqVO.getUserId()))){
                    try {
                        appBrokerageUserApi.bindBrokerageUser(user.getId(), reqVO.getUserId());
                    } catch (Exception e) {
                        log.error("绑定推广员失败", e);
                        isBrokerage = false;
                    }
                    if (isBrokerage){
                        user.setMemberId(reqVO.getUserId());
                        memberUserMapper.updateById(user);
                    }

                }
            }
        }
        /*if (Objects.equals(reqVO.getType(), SocialTypeEnum.WECHAT_DISTRIBUTOR_ID.getType())) {
            if(null == user.getMemberId() && null != reqVO.getUserId()){
                user.setDistributorId(reqVO.getUserId());
                memberUserMapper.updateById(user);
            }
        }
        if (Objects.equals(reqVO.getType(), SocialTypeEnum.WECHAT_NUTRITION_ID.getType())) {
            if(null == user.getMemberId() && null != reqVO.getUserId()){
                user.setNutritionId(reqVO.getUserId());
                memberUserMapper.updateById(user);
            }
        }*/

        // 绑定社交用户
        String openid = socialUserApi.bindSocialUser(new SocialUserBindReqDTO(user.getId(), getUserType().getValue(),
                SocialTypeEnum.WECHAT_MINI_APP.getType(), reqVO.getLoginCode(), reqVO.getState()));
        user.setOpenId(openid);
        memberUserMapper.updateById(user);

        // 创建 Token 令牌，记录登录日志
        return createTokenAfterLoginSuccess(user, user.getMobile(), LoginLogTypeEnum.LOGIN_SOCIAL, openid);
    }

    private AppAuthLoginRespVO createTokenAfterLoginSuccess(MemberUserDO user, String mobile,
                                                            LoginLogTypeEnum logType, String openid) {
        // 插入登陆日志
        createLoginLog(user.getId(), mobile, logType, LoginResultEnum.SUCCESS);
        // 创建 Token 令牌
        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.createAccessToken(new OAuth2AccessTokenCreateReqDTO()
                .setUserId(user.getId()).setUserType(getUserType().getValue())
                .setClientId(OAuth2ClientConstants.CLIENT_ID_DEFAULT));
        // 构建返回结果
        return AuthConvert.INSTANCE.convert(accessTokenRespDTO, openid);
    }

    @Override
    public String getSocialAuthorizeUrl(Integer type, String redirectUri) {
        return socialClientApi.getAuthorizeUrl(type, UserTypeEnum.MEMBER.getValue(), redirectUri);
    }

    private MemberUserDO login0(String mobile, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_MOBILE;
        // 校验账号是否存在
        MemberUserDO user = userService.getUserByMobile(mobile);
        if (user == null) {
            createLoginLog(null, mobile, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        if (!userService.isPasswordMatch(password, user.getPassword())) {
            createLoginLog(user.getId(), mobile, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw exception(AUTH_LOGIN_BAD_CREDENTIALS);
        }
        // 校验是否禁用
        if (ObjectUtil.notEqual(user.getStatus(), CommonStatusEnum.ENABLE.getStatus())) {
            createLoginLog(user.getId(), mobile, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw exception(AUTH_LOGIN_USER_DISABLED);
        }
        return user;
    }

    private void createLoginLog(Long userId, String mobile, LoginLogTypeEnum logType, LoginResultEnum loginResult) {
        // 插入登录日志
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(logType.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(mobile);
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(getClientIP());
        reqDTO.setResult(loginResult.getResult());
        loginLogApi.createLoginLog(reqDTO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            userService.updateUserLogin(userId, getClientIP());
        }
    }

    @Override
    public void logout(String token) {
        // 删除访问令牌
        OAuth2AccessTokenRespDTO accessTokenRespDTO = oauth2TokenApi.removeAccessToken(token);
        if (accessTokenRespDTO == null) {
            return;
        }
        // 删除成功，则记录登出日志
        createLogoutLog(accessTokenRespDTO.getUserId());
    }

    @Override
    public void sendSmsCode(Long userId, AppAuthSmsSendReqVO reqVO) {
        // 情况 1：如果是修改手机场景，需要校验新手机号是否已经注册，说明不能使用该手机了
        if (Objects.equals(reqVO.getScene(), SmsSceneEnum.MEMBER_UPDATE_MOBILE.getScene())) {
            MemberUserDO user = userService.getUserByMobile(reqVO.getMobile());
            if (user != null && !Objects.equals(user.getId(), userId)) {
                throw exception(AUTH_MOBILE_USED);
            }
        }
        // 情况 2：如果是重置密码场景，需要校验手机号是存在的
        if (Objects.equals(reqVO.getScene(), SmsSceneEnum.MEMBER_RESET_PASSWORD.getScene())) {
            MemberUserDO user = userService.getUserByMobile(reqVO.getMobile());
            if (user == null) {
                throw exception(USER_MOBILE_NOT_EXISTS);
            }
        }
        // 情况 3：如果是修改密码场景，需要查询手机号，无需前端传递
        if (Objects.equals(reqVO.getScene(), SmsSceneEnum.MEMBER_UPDATE_PASSWORD.getScene())) {
            MemberUserDO user = userService.getUser(userId);
            // TODO 芋艿：后续 member user 手机非强绑定，这块需要做下调整；
            reqVO.setMobile(user.getMobile());
        }

        // 执行发送
        smsCodeApi.sendSmsCode(AuthConvert.INSTANCE.convert(reqVO).setCreateIp(getClientIP()));
    }

    @Override
    public void validateSmsCode(Long userId, AppAuthSmsValidateReqVO reqVO) {
        smsCodeApi.validateSmsCode(AuthConvert.INSTANCE.convert(reqVO));
    }

    @Override
    public AppAuthLoginRespVO refreshToken(String refreshToken) {
        OAuth2AccessTokenRespDTO accessTokenDO = oauth2TokenApi.refreshAccessToken(refreshToken,
                OAuth2ClientConstants.CLIENT_ID_DEFAULT);
        return AuthConvert.INSTANCE.convert(accessTokenDO, null);
    }

    private void createLogoutLog(Long userId) {
        LoginLogCreateReqDTO reqDTO = new LoginLogCreateReqDTO();
        reqDTO.setLogType(LoginLogTypeEnum.LOGOUT_SELF.getType());
        reqDTO.setTraceId(TracerUtils.getTraceId());
        reqDTO.setUserId(userId);
        reqDTO.setUserType(getUserType().getValue());
        reqDTO.setUsername(getMobile(userId));
        reqDTO.setUserAgent(ServletUtils.getUserAgent());
        reqDTO.setUserIp(getClientIP());
        reqDTO.setResult(LoginResultEnum.SUCCESS.getResult());
        loginLogApi.createLoginLog(reqDTO);
    }

    private String getMobile(Long userId) {
        if (userId == null) {
            return null;
        }
        MemberUserDO user = userService.getUser(userId);
        return user != null ? user.getMobile() : null;
    }

    private UserTypeEnum getUserType() {
        return UserTypeEnum.MEMBER;
    }

}
