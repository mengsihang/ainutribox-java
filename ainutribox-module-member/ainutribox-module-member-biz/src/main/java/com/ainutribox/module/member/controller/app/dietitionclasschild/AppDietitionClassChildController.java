package com.ainutribox.module.member.controller.app.dietitionclasschild;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.member.controller.admin.dietitionclasschild.vo.DietitionClassChildPageReqVO;
import com.ainutribox.module.member.controller.admin.dietitionclasschild.vo.DietitionClassChildRespVO;
import com.ainutribox.module.member.controller.admin.dietitionclasschild.vo.DietitionClassChildSaveReqVO;
import com.ainutribox.module.member.dal.dataobject.dietitionclasschild.DietitionClassChildDO;
import com.ainutribox.module.member.security.annotations.DietitionPreAuthenticated;
import com.ainutribox.module.member.service.dietitionclass.AppDietitionClassService;
import com.ainutribox.module.member.service.dietitionclasschild.DietitionClassChildService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 课程小节")
@RestController
@RequestMapping("/member/dietition-class-child")
@Validated
public class AppDietitionClassChildController {

    @Resource
    private DietitionClassChildService dietitionClassChildService;

    @Resource
    private AppDietitionClassService appDietitionClassService;

    @PostMapping("/create")
    @Operation(summary = "创建课程小节")
    @DietitionPreAuthenticated
    public CommonResult<Long> createDietitionClassChild(@Valid @RequestBody DietitionClassChildSaveReqVO createReqVO) {
        createReqVO.setDietitionId(getLoginUserId());
        return success(dietitionClassChildService.createDietitionClassChild(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新课程小节")
    @DietitionPreAuthenticated
    public CommonResult<Boolean> updateDietitionClassChild(@Valid @RequestBody DietitionClassChildSaveReqVO updateReqVO) {
        dietitionClassChildService.updateDietitionClassChild(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除课程小节")
    @Parameter(name = "id", description = "编号", required = true)
    @DietitionPreAuthenticated
    public CommonResult<Boolean> deleteDietitionClassChild(@RequestParam("id") Long id) {
        dietitionClassChildService.deleteDietitionClassChild(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得课程小节")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @DietitionPreAuthenticated
    public CommonResult<DietitionClassChildRespVO> getDietitionClassChild(@RequestParam("id") Long id) {
        DietitionClassChildDO dietitionClassChild = dietitionClassChildService.getDietitionClassChild(id);
        return success(BeanUtils.toBean(dietitionClassChild, DietitionClassChildRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得课程小节分页")
    @DietitionPreAuthenticated
    public CommonResult<PageResult<DietitionClassChildRespVO>> getDietitionClassChildPage(@Valid DietitionClassChildPageReqVO pageReqVO) {
        PageResult<DietitionClassChildDO> pageResult = dietitionClassChildService.getDietitionClassChildPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitionClassChildRespVO.class));
    }


    @GetMapping("/list")
    @Operation(summary = "获得课程小节列表")
    @Parameter(name = "classId", description = "课程编号", required = true, example = "1024")
    @DietitionPreAuthenticated
    public CommonResult<List<DietitionClassChildRespVO>> getDietitionClassChildList(@RequestParam("classId") Long classId) {
        List<DietitionClassChildDO> ListResult = appDietitionClassService.getChildList(classId);
        return success(BeanUtils.toBean(ListResult, DietitionClassChildRespVO.class));
    }

}