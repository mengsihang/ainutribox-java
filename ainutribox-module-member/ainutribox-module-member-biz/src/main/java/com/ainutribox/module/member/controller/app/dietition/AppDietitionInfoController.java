package com.ainutribox.module.member.controller.app.dietition;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.member.controller.admin.dietition.vo.DietitionInfoPageReqVO;
import com.ainutribox.module.member.controller.admin.dietition.vo.DietitionInfoSaveReqVO;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import com.ainutribox.module.member.enums.ErrorCodeConstants;
import com.ainutribox.module.member.security.annotations.DietitionPreAuthenticated;
import com.ainutribox.module.member.service.dietition.DietitionInfoService;
import com.ainutribox.module.member.service.user.MemberUserService;
import com.alibaba.druid.wall.violation.ErrorCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.common.pojo.CommonResult.error;
import static com.ainutribox.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 营养师")
@RestController
@RequestMapping("/member/dietition/info")
@Validated
public class AppDietitionInfoController {

    @Resource
    private DietitionInfoService dietitionInfoService;

    @GetMapping("/get")
    @Operation(summary = "获取营养师信息")
    @PreAuthenticated
    public CommonResult<DietitionInfoDO> get(){
        DietitionInfoDO info = dietitionInfoService.getDietitionByUserId(getLoginUserId());
        if (info == null){
            return error(ErrorCodeConstants.DIETITION_NOT_EXISTS);
        }
        return success(info);
    }

    @GetMapping("/getPage")
    @Operation(summary = "获取营养师列表分页")
    public CommonResult<PageResult<DietitionInfoDO>> get(@Valid @RequestBody DietitionInfoPageReqVO pageReqVO){
        pageReqVO.setStatus(0);
        PageResult<DietitionInfoDO> pageResult = dietitionInfoService.getDietitionInfoPage(pageReqVO);
        return success(pageResult);
    }


    @PutMapping("/update")
    @Operation(summary = "更新营养师信息")
    @DietitionPreAuthenticated
    public CommonResult<DietitionInfoSaveReqVO> update(@Valid @RequestBody DietitionInfoSaveReqVO dietitionInfoSaveReqVO){
        dietitionInfoService.updateDietitionInfo(dietitionInfoSaveReqVO);
        return success(dietitionInfoSaveReqVO);
    }



}
