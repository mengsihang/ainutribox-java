package com.ainutribox.module.member.controller.app.user;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.member.controller.admin.report.vo.ReportPageReqVO;
import com.ainutribox.module.member.controller.admin.report.vo.ReportRespVO;
import com.ainutribox.module.member.controller.app.user.vo.*;
import com.ainutribox.module.member.convert.user.MemberUserConvert;
import com.ainutribox.module.member.dal.dataobject.level.MemberLevelDO;
import com.ainutribox.module.member.dal.dataobject.report.ReportDO;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.member.dal.mysql.report.ReportMapper;
import com.ainutribox.module.member.service.dietition.DietitionService;
import com.ainutribox.module.member.service.level.MemberLevelService;
import com.ainutribox.module.member.service.report.ReportService;
import com.ainutribox.module.member.service.user.MemberUserService;
import com.ainutribox.module.trade.api.push.ReportPushApi;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 用户个人中心")
@RestController
@RequestMapping("/member/user")
@Validated
@Slf4j
public class AppMemberUserController {

    @Resource
    private MemberUserService userService;
    @Resource
    private MemberLevelService levelService;

    @Resource
    private DietitionService dietitionService;

    @Resource
    private ReportPushApi reportPushApi;

    @Resource
    private ReportService reportService;

    @Resource
    private ReportMapper reportMapper;

    @GetMapping("/get")
    @Operation(summary = "获得基本信息")
    @PreAuthenticated
    public CommonResult<AppMemberUserInfoRespVO> getUserInfo() {
        MemberUserDO user = userService.getUser(getLoginUserId());
        MemberLevelDO level = levelService.getLevel(user.getLevelId());
        Integer dietitionStatus = userService.isMemberDietitionExpired(getLoginUserId());
        Integer agentStatus = userService.isMemberAgentExpired(getLoginUserId());
        AppMemberUserInfoRespVO appMemberUserInfoRespVO = MemberUserConvert.INSTANCE.convert(user, level);
        appMemberUserInfoRespVO.setDietitionType(dietitionStatus);
        appMemberUserInfoRespVO.setAgentType(agentStatus);
        appMemberUserInfoRespVO.setUserId(user.getId());

        return success(appMemberUserInfoRespVO);
    }

    @PutMapping("/update")
    @Operation(summary = "修改基本信息")
    @PreAuthenticated
    public CommonResult<Boolean> updateUser(@RequestBody @Valid AppMemberUserUpdateReqVO reqVO) {
        userService.updateUser(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-mobile")
    @Operation(summary = "修改用户手机")
    @PreAuthenticated
    public CommonResult<Boolean> updateUserMobile(@RequestBody @Valid AppMemberUserUpdateMobileReqVO reqVO) {
        userService.updateUserMobile(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-mobile-by-weixin")
    @Operation(summary = "基于微信小程序的授权码，修改用户手机")
    @PreAuthenticated
    public CommonResult<Boolean> updateUserMobileByWeixin(@RequestBody @Valid AppMemberUserUpdateMobileByWeixinReqVO reqVO) {
        userService.updateUserMobileByWeixin(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/update-password")
    @Operation(summary = "修改用户密码", description = "用户修改密码时使用")
    @PreAuthenticated
    public CommonResult<Boolean> updateUserPassword(@RequestBody @Valid AppMemberUserUpdatePasswordReqVO reqVO) {
        userService.updateUserPassword(getLoginUserId(), reqVO);
        return success(true);
    }

    @PutMapping("/reset-password")
    @Operation(summary = "重置密码", description = "用户忘记密码时使用")
    @PreAuthenticated
    public CommonResult<Boolean> resetUserPassword(@RequestBody @Valid AppMemberUserResetPasswordReqVO reqVO) {
        userService.resetUserPassword(reqVO);
        return success(true);
    }


    @GetMapping("/share-QRCode")
    @Operation(summary = "获取分享二维码", description = "分享使用")
    @PreAuthenticated
    public CommonResult<String> getShareQRCode() {
        return success(userService.getShareQRCode(getLoginUserId()));
    }

    @PostMapping("/reportSendGenerate")
    @Operation(summary = "生成报告")
    @PreAuthenticated
    public CommonResult<Object> reportSendGenerate(@RequestBody List<Map<String, Object>> reportMap) {
        return success(reportPushApi.reportSendGenerate(reportMap, getLoginUserId()));
    }

    @GetMapping("/reportGet")
    @Operation(summary = "获得用户报告")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<ReportRespVO> getReport(@RequestParam("id") Long id) {
        ReportDO report = reportMapper.selectOne("id",id,"member_id",getLoginUserId());
        return success(BeanUtils.toBean(report, ReportRespVO.class));
    }

    @GetMapping("/reportGetPage")
    @Operation(summary = "获得用户报告分页")
    @PreAuthenticated
    public CommonResult<PageResult<ReportRespVO>> getReportPage(@Valid ReportPageReqVO pageReqVO) {
        pageReqVO.setMemberId(getLoginUserId());
        PageResult<ReportDO> pageResult = reportService.getReportPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportRespVO.class));
    }

}

