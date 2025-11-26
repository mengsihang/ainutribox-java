package com.ainutribox.module.member.controller.app.dietitionclass;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.member.controller.admin.dietition.vo.DietitionInfoPageReqVO;
import com.ainutribox.module.member.controller.admin.dietition.vo.DietitionInfoRespVO;
import com.ainutribox.module.member.controller.admin.dietitionclasscomment.vo.DietitionClassCommentPageReqVO;
import com.ainutribox.module.member.controller.admin.dietitionclasscomment.vo.DietitionClassCommentRespVO;
import com.ainutribox.module.member.controller.app.dietitionclass.dto.*;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.AppClassChildVO;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.AppDietitionInfoVo;
import com.ainutribox.module.member.controller.app.dietitionclass.vo.AppUserClassVO;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscomment.DietitionClassCommentDO;
import com.ainutribox.module.member.security.annotations.DietitionPreAuthenticated;
import com.ainutribox.module.member.service.dietition.DietitionInfoService;
import com.ainutribox.module.member.service.dietitionclass.AppUserClassService;
import com.ainutribox.module.member.service.dietitionclasscomment.DietitionClassCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

/**
 * AppUserClassController
 *
 * @author lucifer
 * @date 2024-06-29 13:29
 */


@Tag(name = "用户 APP - 用户课程")
@RestController
@RequestMapping("/member/user-class")
@Validated
public class AppUserClassController {

    @Resource
    private AppUserClassService userClassService;

    @Resource
    private DietitionClassCommentService dietitionClassCommentService;

    @Resource
    private DietitionInfoService dietitionInfoService;

    @GetMapping("/page")
    @Operation(summary = "获得课程分页")
    public CommonResult<PageResult<AppUserClassVO>> getAllClassPage(@Valid AppUserClassDTO pageReqDTO) {
        PageResult<AppUserClassVO> pageResult = userClassService.getAllClassPage(pageReqDTO);
        return success(pageResult);
    }

    @PostMapping("/joinClass")
    @Operation(summary = "添加指定课程")
    @PreAuthenticated
    public CommonResult<Boolean> joinClass(@Valid @RequestBody AppJoinOrCancelClassDTO joinOrCancelClassDTO){
        userClassService.joinClass(getLoginUserId(),joinOrCancelClassDTO);
        return success(true);
    }

    @PostMapping("/cancelClass")
    @Operation(summary = "删除指定课程")
    @PreAuthenticated
    public CommonResult<Boolean> cancelClass(@Valid @RequestBody AppJoinOrCancelClassDTO joinOrCancelClassDTO){
        userClassService.cancelClass(getLoginUserId(),joinOrCancelClassDTO);
        return success(true);
    }


    @GetMapping("/joinList")
    @Operation(summary = "加入课程列表")
    @PreAuthenticated
    public CommonResult<List<AppUserClassVO>> selectUserJoinClassList() {
        return success(userClassService.selectUserJoinClassList(getLoginUserId()));
    }

    @GetMapping("/buyList")
    @Operation(summary = "购买课程列表")
    @PreAuthenticated
    public CommonResult<List<AppUserClassVO>> selectUserBuyClassList() {
        return success(userClassService.selectUserBuyClassList(getLoginUserId()));
    }

    @GetMapping("/getClassChileVO")
    @Operation(summary = "获得课程详情及小节列表")
    @Parameter(name = "classId", description = "课程编号", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<AppClassChildVO> getClassChileVO(@RequestParam("classId") Long classId) {
        AppClassChildVO Result = userClassService.getClassChileVO(getLoginUserId(),classId);
        return success(Result);
    }

    @PostMapping("/userSpotClass")
    @Operation(summary = "指定课程点赞")
    @PreAuthenticated
    public CommonResult<Boolean> userSpotClass(@Valid @RequestBody AppSpotOrCancelClassDTO spotOrCancelClassDTO){
        userClassService.userSpotClass(getLoginUserId(),spotOrCancelClassDTO);
        return success(true);
    }

    @PostMapping("/userCreateComment")
    @Operation(summary = "创建营养师课程评论")
    @PreAuthenticated
    public CommonResult<Boolean> userCreateComment(@Valid @RequestBody AppClassCommentDTO appClassCommentDTO) {
        userClassService.userCreateComment(getLoginUserId(),appClassCommentDTO);
        return success(true);
    }


    @PostMapping("/dietitionReplyComment")
    @Operation(summary = "营养师回复课程评论")
    @DietitionPreAuthenticated
    public CommonResult<Boolean> dietitionReplyComment(@Valid @RequestBody AppClassReplyCommentDTO appClassReplyCommentDTO) {
        userClassService.dietitionReplyComment(getLoginUserId(),appClassReplyCommentDTO);
        return success(true);
    }

    @GetMapping("/commentPage")
    @Operation(summary = "获得营养师课程评论分页")
    @PreAuthenticated
    public CommonResult<PageResult<DietitionClassCommentRespVO>> getDietitionClassCommentPage(@Valid DietitionClassCommentPageReqVO pageReqVO) {
        PageResult<DietitionClassCommentDO> pageResult = dietitionClassCommentService.getDietitionClassCommentPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitionClassCommentRespVO.class));
    }

    @GetMapping("/dietitionPage")
    @Operation(summary = "获得营养师信息分页")
    //@PreAuthenticated
    public CommonResult<PageResult<DietitionInfoRespVO>> getDietitionInfoPage(@Valid AppUserClassDTO pageVO) {
        PageResult<DietitionInfoRespVO> pageResult = userClassService.getDietitionInfoList(pageVO);
        return success(pageResult);
    }


    @GetMapping("/getDietitionInfoData")
    @Operation(summary = "获取导师相关数据")
    @PreAuthenticated
    public CommonResult<AppDietitionInfoVo> getDietitionInfoData(AppDietitionInfoDTO appDietitionInfoDTO) {
        AppDietitionInfoVo Result = userClassService.getDietitionInfoData(appDietitionInfoDTO);
        return success(Result);
    }


}
