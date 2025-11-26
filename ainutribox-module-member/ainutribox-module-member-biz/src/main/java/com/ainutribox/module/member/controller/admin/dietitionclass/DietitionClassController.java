package com.ainutribox.module.member.controller.admin.dietitionclass;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import static com.ainutribox.framework.common.pojo.CommonResult.success;

import com.ainutribox.framework.excel.core.util.ExcelUtils;

import com.ainutribox.framework.apilog.core.annotation.ApiAccessLog;
import static com.ainutribox.framework.apilog.core.enums.OperateTypeEnum.*;

import com.ainutribox.module.member.controller.admin.dietitionclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietitionclass.DietitionClassDO;
import com.ainutribox.module.member.service.dietitionclass.DietitionClassService;

@Tag(name = "管理后台 - 营养师课程")
@RestController
@RequestMapping("/member/dietition-class")
@Validated
public class DietitionClassController {

    @Resource
    private DietitionClassService dietitionClassService;

    @PostMapping("/create")
    @Operation(summary = "创建营养师课程")
    @PreAuthorize("@ss.hasPermission('member:dietition-class:create')")
    public CommonResult<Long> createDietitionClass(@Valid @RequestBody DietitionClassSaveReqVO createReqVO) {
        return success(dietitionClassService.createDietitionClass(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新营养师课程")
    @PreAuthorize("@ss.hasPermission('member:dietition-class:update')")
    public CommonResult<Boolean> updateDietitionClass(@Valid @RequestBody DietitionClassSaveReqVO updateReqVO) {
        dietitionClassService.updateDietitionClass(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除营养师课程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:dietition-class:delete')")
    public CommonResult<Boolean> deleteDietitionClass(@RequestParam("id") Long id) {
        dietitionClassService.deleteDietitionClass(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得营养师课程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:dietition-class:query')")
    public CommonResult<DietitionClassRespVO> getDietitionClass(@RequestParam("id") Long id) {
        DietitionClassDO dietitionClass = dietitionClassService.getDietitionClass(id);
        return success(BeanUtils.toBean(dietitionClass, DietitionClassRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得营养师课程分页")
    @PreAuthorize("@ss.hasPermission('member:dietition-class:query')")
    public CommonResult<PageResult<DietitionClassRespVO>> getDietitionClassPage(@Valid DietitionClassPageReqVO pageReqVO) {
        PageResult<DietitionClassDO> pageResult = dietitionClassService.getDietitionClassPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitionClassRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出营养师课程 Excel")
    @PreAuthorize("@ss.hasPermission('member:dietition-class:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDietitionClassExcel(@Valid DietitionClassPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DietitionClassDO> list = dietitionClassService.getDietitionClassPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "营养师课程.xls", "数据", DietitionClassRespVO.class,
                        BeanUtils.toBean(list, DietitionClassRespVO.class));
    }

}