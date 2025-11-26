package com.ainutribox.module.product.controller.admin.dietitian;

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

import com.ainutribox.module.product.controller.admin.dietitian.vo.*;
import com.ainutribox.module.product.dal.dataobject.dietitian.DietitianDO;
import com.ainutribox.module.product.service.dietitian.DietitianService;

@Tag(name = "管理后台 - 营养师自组营养包")
@RestController
@RequestMapping("/product/dietitian")
@Validated
public class DietitianController {

    @Resource
    private DietitianService dietitianService;

    @PostMapping("/create")
    @Operation(summary = "创建营养师自组营养包")
    @PreAuthorize("@ss.hasPermission('product:dietitian:create')")
    public CommonResult<Long> createDietitian(@Valid @RequestBody DietitianSaveReqVO createReqVO) {
        return success(dietitianService.createDietitian(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新营养师自组营养包")
    @PreAuthorize("@ss.hasPermission('product:dietitian:update')")
    public CommonResult<Boolean> updateDietitian(@Valid @RequestBody DietitianSaveReqVO updateReqVO) {
        dietitianService.updateDietitian(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除营养师自组营养包")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('product:dietitian:delete')")
    public CommonResult<Boolean> deleteDietitian(@RequestParam("id") Long id) {
        dietitianService.deleteDietitian(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得营养师自组营养包")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:dietitian:query')")
    public CommonResult<DietitianRespVO> getDietitian(@RequestParam("id") Long id) {
        DietitianDO dietitian = dietitianService.getDietitian(id);
        return success(BeanUtils.toBean(dietitian, DietitianRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得营养师自组营养包分页")
    @PreAuthorize("@ss.hasPermission('product:dietitian:query')")
    public CommonResult<PageResult<DietitianRespVO>> getDietitianPage(@Valid DietitianPageReqVO pageReqVO) {
        PageResult<DietitianDO> pageResult = dietitianService.getDietitianPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitianRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出营养师自组营养包 Excel")
    @PreAuthorize("@ss.hasPermission('product:dietitian:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDietitianExcel(@Valid DietitianPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DietitianDO> list = dietitianService.getDietitianPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "营养师自组营养包.xls", "数据", DietitianRespVO.class,
                        BeanUtils.toBean(list, DietitianRespVO.class));
    }

}