package com.ainutribox.module.member.controller.app.dietitionclass;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.member.controller.admin.dietitionclass.vo.ClassUpdateStatusReqVO;
import com.ainutribox.module.member.controller.admin.dietitionclass.vo.DietitionClassPageReqVO;
import com.ainutribox.module.member.controller.admin.dietitionclass.vo.DietitionClassRespVO;
import com.ainutribox.module.member.controller.admin.dietitionclass.vo.DietitionClassSaveReqVO;
import com.ainutribox.module.member.dal.dataobject.dietitionclass.DietitionClassDO;
import com.ainutribox.module.member.security.annotations.DietitionPreAuthenticated;
import com.ainutribox.module.member.service.dietitionclass.AppDietitionClassService;
import com.ainutribox.module.member.service.dietitionclass.DietitionClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 营养师课程")
@RestController
@RequestMapping("/member/dietition-class")
@Validated
public class AppDietitionClassController {

    @Resource
    private DietitionClassService dietitionClassService;

    @Resource
    private AppDietitionClassService appDietitionClassService;

    @PostMapping("/create")
    @Operation(summary = "创建营养师课程")
    @DietitionPreAuthenticated
    public CommonResult<Long> createDietitionClass(@Valid @RequestBody DietitionClassSaveReqVO createReqVO) {
        createReqVO.setDietitionId(getLoginUserId());
        createReqVO.setVirtualPeople(null);
        createReqVO.setActualPeople(null);
        return success(dietitionClassService.createDietitionClass(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新营养师课程")
    @DietitionPreAuthenticated
    public CommonResult<Boolean> updateDietitionClass(@Valid @RequestBody DietitionClassSaveReqVO updateReqVO) {
        updateReqVO.setVirtualPeople(null);
        updateReqVO.setActualPeople(null);
        dietitionClassService.updateDietitionClass(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除营养师课程")
    @Parameter(name = "id", description = "编号", required = true)
    @DietitionPreAuthenticated
    public CommonResult<Boolean> deleteDietitionClass(@RequestParam("id") Long id) {
        dietitionClassService.deleteDietitionClass(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得营养师课程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @DietitionPreAuthenticated
    public CommonResult<DietitionClassRespVO> getDietitionClass(@RequestParam("id") Long id) {
        DietitionClassDO dietitionClass = dietitionClassService.getDietitionClass(id);
        return success(BeanUtils.toBean(dietitionClass, DietitionClassRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得营养师课程分页")
    @DietitionPreAuthenticated
    public CommonResult<PageResult<DietitionClassRespVO>> getDietitionClassPage(@Valid DietitionClassPageReqVO pageReqVO) {
        pageReqVO.setDietitionId(getLoginUserId());
        PageResult<DietitionClassDO> pageResult = dietitionClassService.getDietitionClassPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitionClassRespVO.class));
    }

    @PutMapping("/update-status")
    @Operation(summary = "更新课程 Status")
    @DietitionPreAuthenticated
    public CommonResult<Boolean> updateStatus(@Valid @RequestBody ClassUpdateStatusReqVO updateReqVO) {
        appDietitionClassService.updateSpuStatus(updateReqVO);
        return success(true);
    }

}