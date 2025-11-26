package com.ainutribox.module.member.controller.admin.report;

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

import com.ainutribox.module.member.controller.admin.report.vo.*;
import com.ainutribox.module.member.dal.dataobject.report.ReportDO;
import com.ainutribox.module.member.service.report.ReportService;

@Tag(name = "管理后台 - 用户报告")
@RestController
@RequestMapping("/member/report")
@Validated
public class ReportController {

    @Resource
    private ReportService reportService;

    @PostMapping("/create")
    @Operation(summary = "创建用户报告")
    @PreAuthorize("@ss.hasPermission('member:report:create')")
    public CommonResult<Long> createReport(@Valid @RequestBody ReportSaveReqVO createReqVO) {
        return success(reportService.createReport(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户报告")
    @PreAuthorize("@ss.hasPermission('member:report:update')")
    public CommonResult<Boolean> updateReport(@Valid @RequestBody ReportSaveReqVO updateReqVO) {
        reportService.updateReport(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户报告")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:report:delete')")
    public CommonResult<Boolean> deleteReport(@RequestParam("id") Long id) {
        reportService.deleteReport(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户报告")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:report:query')")
    public CommonResult<ReportRespVO> getReport(@RequestParam("id") Long id) {
        ReportDO report = reportService.getReport(id);
        return success(BeanUtils.toBean(report, ReportRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户报告分页")
    @PreAuthorize("@ss.hasPermission('member:report:query')")
    public CommonResult<PageResult<ReportRespVO>> getReportPage(@Valid ReportPageReqVO pageReqVO) {
        PageResult<ReportDO> pageResult = reportService.getReportPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReportRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户报告 Excel")
    @PreAuthorize("@ss.hasPermission('member:report:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReportExcel(@Valid ReportPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReportDO> list = reportService.getReportPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户报告.xls", "数据", ReportRespVO.class,
                        BeanUtils.toBean(list, ReportRespVO.class));
    }

}