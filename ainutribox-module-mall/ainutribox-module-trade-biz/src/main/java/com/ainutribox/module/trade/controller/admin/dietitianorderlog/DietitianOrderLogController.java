package com.ainutribox.module.trade.controller.admin.dietitianorderlog;

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

import com.ainutribox.module.trade.controller.admin.dietitianorderlog.vo.*;
import com.ainutribox.module.trade.dal.dataobject.dietitianorderlog.DietitianOrderLogDO;
import com.ainutribox.module.trade.service.dietitianorderlog.DietitianOrderLogService;

@Tag(name = "管理后台 - 营养师自组营养包售卖订单日志")
@RestController
@RequestMapping("/trade/dietitian-order-log")
@Validated
public class DietitianOrderLogController {

    @Resource
    private DietitianOrderLogService dietitianOrderLogService;

    @PostMapping("/create")
    @Operation(summary = "创建营养师自组营养包售卖订单日志")
    @PreAuthorize("@ss.hasPermission('trade:dietitian-order-log:create')")
    public CommonResult<Long> createDietitianOrderLog(@Valid @RequestBody DietitianOrderLogSaveReqVO createReqVO) {
        return success(dietitianOrderLogService.createDietitianOrderLog(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新营养师自组营养包售卖订单日志")
    @PreAuthorize("@ss.hasPermission('trade:dietitian-order-log:update')")
    public CommonResult<Boolean> updateDietitianOrderLog(@Valid @RequestBody DietitianOrderLogSaveReqVO updateReqVO) {
        dietitianOrderLogService.updateDietitianOrderLog(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除营养师自组营养包售卖订单日志")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('trade:dietitian-order-log:delete')")
    public CommonResult<Boolean> deleteDietitianOrderLog(@RequestParam("id") Long id) {
        dietitianOrderLogService.deleteDietitianOrderLog(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得营养师自组营养包售卖订单日志")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:dietitian-order-log:query')")
    public CommonResult<DietitianOrderLogRespVO> getDietitianOrderLog(@RequestParam("id") Long id) {
        DietitianOrderLogDO dietitianOrderLog = dietitianOrderLogService.getDietitianOrderLog(id);
        return success(BeanUtils.toBean(dietitianOrderLog, DietitianOrderLogRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得营养师自组营养包售卖订单日志分页")
    @PreAuthorize("@ss.hasPermission('trade:dietitian-order-log:query')")
    public CommonResult<PageResult<DietitianOrderLogRespVO>> getDietitianOrderLogPage(@Valid DietitianOrderLogPageReqVO pageReqVO) {
        PageResult<DietitianOrderLogDO> pageResult = dietitianOrderLogService.getDietitianOrderLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitianOrderLogRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出营养师自组营养包售卖订单日志 Excel")
    @PreAuthorize("@ss.hasPermission('trade:dietitian-order-log:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDietitianOrderLogExcel(@Valid DietitianOrderLogPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DietitianOrderLogDO> list = dietitianOrderLogService.getDietitianOrderLogPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "营养师自组营养包售卖订单日志.xls", "数据", DietitianOrderLogRespVO.class,
                        BeanUtils.toBean(list, DietitianOrderLogRespVO.class));
    }

}