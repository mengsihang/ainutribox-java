package com.ainutribox.module.member.service.user;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.ainutribox.framework.common.enums.CommonStatusEnum;
import com.ainutribox.framework.common.enums.UserTypeEnum;
import com.ainutribox.framework.common.exception.ErrorCode;
import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.module.member.controller.admin.user.vo.MemberUserPageReqVO;
import com.ainutribox.module.member.controller.admin.user.vo.MemberUserUpdateReqVO;
import com.ainutribox.module.member.controller.app.user.vo.*;
import com.ainutribox.module.member.convert.auth.AuthConvert;
import com.ainutribox.module.member.convert.user.MemberUserConvert;
import com.ainutribox.module.member.dal.dataobject.agent.AgentDO;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionDO;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import com.ainutribox.module.member.dal.dataobject.joinclass.JoinClassDO;
import com.ainutribox.module.member.dal.dataobject.payclass.PayClassDO;
import com.ainutribox.module.member.dal.dataobject.spotclass.SpotClassDO;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.member.dal.dataobject.vip.MemberVipDO;
import com.ainutribox.module.member.dal.mysql.dietition.DietitionInfoMapper;
import com.ainutribox.module.member.dal.mysql.dietition.DietitionMapper;
import com.ainutribox.module.member.dal.mysql.joinclass.JoinClassMapper;
import com.ainutribox.module.member.dal.mysql.payclass.PayClassMapper;
import com.ainutribox.module.member.dal.mysql.spotclass.SpotClassMapper;
import com.ainutribox.module.member.dal.mysql.user.MemberUserMapper;
import com.ainutribox.module.member.dal.mysql.vip.MemberVipMapper;
import com.ainutribox.module.member.enums.ErrorCodeConstants;
import com.ainutribox.module.member.mq.producer.user.MemberUserProducer;
import com.ainutribox.module.member.service.agent.AgentService;
import com.ainutribox.module.member.service.dietition.DietitionService;
import com.ainutribox.module.system.api.sms.SmsCodeApi;
import com.ainutribox.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import com.ainutribox.module.system.api.social.SocialClientApi;
import com.ainutribox.module.system.api.social.dto.SocialWxPhoneNumberInfoRespDTO;
import com.ainutribox.module.system.enums.sms.SmsSceneEnum;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import static com.ainutribox.framework.common.exception.enums.GlobalErrorCodeConstants.NOT_IMPLEMENTED;
import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception0;
import static com.ainutribox.framework.common.util.servlet.ServletUtils.getClientIP;
import static com.ainutribox.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 会员 User Service 实现类
 *
 * @author 河南小泉山科技
 */
@Service
@Valid
@Slf4j
public class MemberUserServiceImpl implements MemberUserService {

    @Resource
    private MemberUserMapper memberUserMapper;

    @Resource
    private SmsCodeApi smsCodeApi;

    @Resource
    private SocialClientApi socialClientApi;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private MemberUserProducer memberUserProducer;

    @Resource
    private WxMaService wxMaService;

    @Resource
    private DietitionInfoMapper dietitionInfoMapper;

    @Resource
    private MemberVipMapper memberVipMapper;

    @Resource
    private PayClassMapper payClassMapper;

    @Resource
    private JoinClassMapper joinClassMapper;

    @Resource
    private AgentService agentService;

    @Resource
    private SpotClassMapper spotClassMapper;

    @Resource
    private DietitionService dietitionService;

    @Override
    public MemberUserDO getUserByMobile(String mobile) {
        return memberUserMapper.selectByMobile(mobile);
    }

    @Override
    public List<MemberUserDO> getUserListByNickname(String nickname) {
        return memberUserMapper.selectListByNicknameLike(nickname);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberUserDO createUserIfAbsent(String mobile, String registerIp, Integer terminal) {
        // 用户已经存在
        MemberUserDO user = memberUserMapper.selectByMobile(mobile);
        if (user != null) {
            return user;
        }
        // 用户不存在，则进行创建
        return createUser(mobile, null, null, registerIp, terminal);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberUserDO createUser(String nickname, String avtar, String registerIp, Integer terminal) {
        return createUser(null, nickname, avtar, registerIp, terminal);
    }

    private MemberUserDO createUser(String mobile, String nickname, String avtar,
                                    String registerIp, Integer terminal) {
        // 生成密码
        String password = IdUtil.fastSimpleUUID();
        // 插入用户
        MemberUserDO user = new MemberUserDO();
        user.setMobile(mobile);
        user.setStatus(CommonStatusEnum.ENABLE.getStatus()); // 默认开启
        user.setPassword(encodePassword(password)); // 加密密码
        user.setRegisterIp(registerIp).setRegisterTerminal(terminal);
        user.setNickname(nickname).setAvatar(avtar); // 基础信息
        if (StrUtil.isEmpty(nickname)) {
            // 昵称为空时，随机一个名字，避免一些依赖 nickname 的逻辑报错，或者有点丑。例如说，短信发送有昵称时~
            user.setNickname("用户" + RandomUtil.randomNumbers(6));
        }
        memberUserMapper.insert(user);

        // 发送 MQ 消息：用户创建
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                memberUserProducer.sendUserCreateMessage(user.getId());
            }

        });
        return user;
    }

    @Override
    public void updateUserLogin(Long id, String loginIp) {
        memberUserMapper.updateById(new MemberUserDO().setId(id)
                .setLoginIp(loginIp).setLoginDate(LocalDateTime.now()));
    }

    @Override
    public MemberUserDO getUser(Long id) {
        return memberUserMapper.selectById(id);
    }

    @Override
    public List<MemberUserDO> getUserList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return memberUserMapper.selectBatchIds(ids);
    }

    @Override
    public void updateUser(Long userId, AppMemberUserUpdateReqVO reqVO) {
        MemberUserDO updateObj = BeanUtils.toBean(reqVO, MemberUserDO.class).setId(userId);
        memberUserMapper.updateById(updateObj);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserMobile(Long userId, AppMemberUserUpdateMobileReqVO reqVO) {
        // 1.1 检测用户是否存在
        MemberUserDO user = validateUserExists(userId);
        // 1.2 校验新手机是否已经被绑定
        validateMobileUnique(null, reqVO.getMobile());

        // 2.1 校验旧手机和旧验证码
        // 补充说明：从安全性来说，老手机也校验 oldCode 验证码会更安全。但是由于 uni-app 商城界面暂时没做，所以这里不强制校验
        if (StrUtil.isNotEmpty(reqVO.getOldCode())) {
            smsCodeApi.useSmsCode(new SmsCodeUseReqDTO().setMobile(user.getMobile()).setCode(reqVO.getOldCode())
                    .setScene(SmsSceneEnum.MEMBER_UPDATE_MOBILE.getScene()).setUsedIp(getClientIP()));
        }
        // 2.2 使用新验证码
        smsCodeApi.useSmsCode(new SmsCodeUseReqDTO().setMobile(reqVO.getMobile()).setCode(reqVO.getCode())
                .setScene(SmsSceneEnum.MEMBER_UPDATE_MOBILE.getScene()).setUsedIp(getClientIP()));

        // 3. 更新用户手机
        memberUserMapper.updateById(MemberUserDO.builder().id(userId).mobile(reqVO.getMobile()).build());
    }

    @Override
    public void updateUserMobileByWeixin(Long userId, AppMemberUserUpdateMobileByWeixinReqVO reqVO) {
        // 1.1 获得对应的手机号信息
        SocialWxPhoneNumberInfoRespDTO phoneNumberInfo = socialClientApi.getWxMaPhoneNumberInfo(
                UserTypeEnum.MEMBER.getValue(), reqVO.getCode());
        Assert.notNull(phoneNumberInfo, "获得手机信息失败，结果为空");
        // 1.2 校验新手机是否已经被绑定
        validateMobileUnique(userId, phoneNumberInfo.getPhoneNumber());

        // 2. 更新用户手机
        memberUserMapper.updateById(MemberUserDO.builder().id(userId).mobile(phoneNumberInfo.getPhoneNumber()).build());
    }

    @Override
    public void updateUserPassword(Long userId, AppMemberUserUpdatePasswordReqVO reqVO) {
        // 检测用户是否存在
        MemberUserDO user = validateUserExists(userId);
        // 校验验证码
        smsCodeApi.useSmsCode(new SmsCodeUseReqDTO().setMobile(user.getMobile()).setCode(reqVO.getCode())
                .setScene(SmsSceneEnum.MEMBER_UPDATE_PASSWORD.getScene()).setUsedIp(getClientIP()));

        // 更新用户密码
        memberUserMapper.updateById(MemberUserDO.builder().id(userId)
                .password(passwordEncoder.encode(reqVO.getPassword())).build());
    }

    @Override
    public void resetUserPassword(AppMemberUserResetPasswordReqVO reqVO) {
        // 检验用户是否存在
        MemberUserDO user = validateUserExists(reqVO.getMobile());

        // 使用验证码
        smsCodeApi.useSmsCode(AuthConvert.INSTANCE.convert(reqVO, SmsSceneEnum.MEMBER_RESET_PASSWORD,
                getClientIP()));

        // 更新密码
        memberUserMapper.updateById(MemberUserDO.builder().id(user.getId())
                .password(passwordEncoder.encode(reqVO.getPassword())).build());
    }

    private MemberUserDO validateUserExists(String mobile) {
        MemberUserDO user = memberUserMapper.selectByMobile(mobile);
        if (user == null) {
            throw exception(USER_MOBILE_NOT_EXISTS);
        }
        return user;
    }

    @Override
    public boolean isPasswordMatch(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 对密码进行加密
     *
     * @param password 密码
     * @return 加密后的密码
     */
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(MemberUserUpdateReqVO updateReqVO) {
        // 校验存在
        MemberUserDO memberUserDO = validateUserExists(updateReqVO.getId());
        // 校验手机唯一
        validateMobileUnique(updateReqVO.getId(), updateReqVO.getMobile());

        // 更新
        MemberUserDO updateObj = MemberUserConvert.INSTANCE.convert(updateReqVO);
        memberUserMapper.updateById(updateObj);

        if(memberUserDO.getStatus() != (int)updateReqVO.getStatus() ){
            DietitionInfoDO dietitionInfoDO = new DietitionInfoDO();
            dietitionInfoDO.setUserId(updateReqVO.getId());
            dietitionInfoDO.setStatus((int) updateReqVO.getStatus());
            LambdaQueryWrapperX<DietitionInfoDO> dietitionMapperLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            dietitionMapperLambdaQueryWrapperX.eq(DietitionInfoDO::getUserId,updateReqVO.getId());
            dietitionInfoMapper.update(dietitionInfoDO,dietitionMapperLambdaQueryWrapperX);
        }
    }

    @VisibleForTesting
    MemberUserDO validateUserExists(Long id) {
        if (id == null) {
            return null;
        }
        MemberUserDO user = memberUserMapper.selectById(id);
        if (user == null) {
            throw exception(USER_NOT_EXISTS);
        }
        return user;
    }

    @VisibleForTesting
    void validateMobileUnique(Long id, String mobile) {
        if (StrUtil.isBlank(mobile)) {
            return;
        }
        MemberUserDO user = memberUserMapper.selectByMobile(mobile);
        if (user == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的用户
        if (id == null) {
            throw exception(USER_MOBILE_USED, mobile);
        }
        if (!user.getId().equals(id)) {
            throw exception(USER_MOBILE_USED, mobile);
        }
    }

    @Override
    public PageResult<MemberUserDO> getUserPage(MemberUserPageReqVO pageReqVO) {
        return memberUserMapper.selectPage(pageReqVO);
    }

    @Override
    public void updateUserLevel(Long id, Long levelId, Integer experience) {
        // 0 代表无等级：防止UpdateById时，会被过滤掉的问题
        levelId = ObjectUtil.defaultIfNull(levelId, 0L);
        memberUserMapper.updateById(new MemberUserDO()
                .setId(id)
                .setLevelId(levelId).setExperience(experience)
        );
    }

    @Override
    public Long getUserCountByGroupId(Long groupId) {
        return memberUserMapper.selectCountByGroupId(groupId);
    }

    @Override
    public Long getUserCountByLevelId(Long levelId) {
        return memberUserMapper.selectCountByLevelId(levelId);
    }

    @Override
    public Long getUserCountByTagId(Long tagId) {
        return memberUserMapper.selectCountByTagId(tagId);
    }

    @Override
    public boolean updateUserPoint(Long id, Integer point) {
        if (point > 0) {
            memberUserMapper.updatePointIncr(id, point);
        } else if (point < 0) {
            return memberUserMapper.updatePointDecr(id, point) > 0;
        }
        return true;
    }

    @Override
    public String getShareQRCode(Long userId) {
        try {
            File qrCodeFile = wxMaService.getQrcodeService().createQrcode("/pages/index/index?userId="+userId, 430);
            // 将文件转换为字节数组
            byte[] qrCodeBytes = Files.readAllBytes(qrCodeFile.toPath());
            // 将字节数组转换为 Base64 编码的字符串
            return Base64.getEncoder().encodeToString(qrCodeBytes);
        }catch (Exception e){
            throw exception(SHAREQR_STATUS_OTHER);
        }
    }

    @Override
    public Integer isMemberVipExpired(Long userId) {
        MemberVipDO memberVipDO = memberVipMapper.selectOne("user_id", userId);
        //判断是否为vip
        if(ObjectUtil.isEmpty(memberVipDO)){
            return -1;
        }
        if(memberVipDO.getVipEndTime().isAfter(LocalDateTime.now())){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer isMemberPayClassExpired(Long userId, Long ClassId) {
        //判断用户是否已经购买过该课程
        LambdaQueryWrapperX<PayClassDO> payClassWrapperx = new LambdaQueryWrapperX<>();
        payClassWrapperx.eq(PayClassDO::getUserId,userId)
                .eq(PayClassDO::getClassId,ClassId);
        if(ObjectUtil.isNotEmpty(payClassMapper.selectOne(payClassWrapperx))){
           return 1;
        }
        return 0;
    }

    @Override
    public Integer isMemberJoinClassExpired(Long userId, Long ClassId) {
        //判断用户是否已经加入该课程
        LambdaQueryWrapperX<JoinClassDO> joinClassWrapperx = new LambdaQueryWrapperX<>();
        joinClassWrapperx.eq(JoinClassDO::getMemberId,userId)
                .eq(JoinClassDO::getClassId,ClassId);
        if(ObjectUtil.isNotEmpty(joinClassMapper.selectOne(joinClassWrapperx))){
            return 1;
        }
        return 0;
    }

    @Override
    public Integer isMemberDietitionExpired(Long userId) {
        //判断是否是营养师
        DietitionDO dietitionDO = dietitionService.getDietitionByUserId(getLoginUserId());
        if (null == dietitionDO || dietitionDO.getStatus() == 0 ||dietitionDO.getStatus() == 2) {
            return -1;
        }else{
           return dietitionDO.getStatus();
        }
    }

    public Integer isMemberAgentExpired(Long userId) {
        //判断是否是供应商
        AgentDO agentDO = agentService.getAgentByUserId(getLoginUserId());
        if (null == agentDO || agentDO.getStatus() == 0 ||agentDO.getStatus() == 2) {
            return -1;
        }else{
            return agentDO.getStatus();
        }
    }

    @Override
    public Integer isSpotExpired(Long userId, Long ClassId) {
        //判断用户是否已点赞
        LambdaQueryWrapperX<SpotClassDO> spotClassWrapperx = new LambdaQueryWrapperX<>();
        spotClassWrapperx.eq(SpotClassDO::getUserId,userId)
                .eq(SpotClassDO::getClassId,ClassId);
        if(ObjectUtil.isNotEmpty(spotClassMapper.selectOne(spotClassWrapperx))){
            return 1;
        }
        return 0;
    }

}
