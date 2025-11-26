package com.ainutribox.module.member.controller.admin.dietition;

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

import com.ainutribox.module.member.controller.admin.dietition.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import com.ainutribox.module.member.service.dietition.DietitionInfoService;

@Tag(name = "管理后台 - 营养师信息")
@RestController
@RequestMapping("/member/dietition-info")
@Validated
public class DietitionInfoController {

    @Resource
    private DietitionInfoService dietitionInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建营养师信息")
    @PreAuthorize("@ss.hasPermission('member:dietition-info:create')")
    public CommonResult<Long> createDietitionInfo(@Valid @RequestBody DietitionInfoSaveReqVO createReqVO) {
        return success(dietitionInfoService.createDietitionInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新营养师信息")
    @PreAuthorize("@ss.hasPermission('member:dietition-info:update')")
    public CommonResult<Boolean> updateDietitionInfo(@Valid @RequestBody DietitionInfoSaveReqVO updateReqVO) {
        dietitionInfoService.updateDietitionInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除营养师信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:dietition-info:delete')")
    public CommonResult<Boolean> deleteDietitionInfo(@RequestParam("id") Long id) {
        dietitionInfoService.deleteDietitionInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得营养师信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:dietition-info:query')")
    public CommonResult<DietitionInfoRespVO> getDietitionInfo(@RequestParam("id") Long id) {
        DietitionInfoDO dietitionInfo = dietitionInfoService.getDietitionInfo(id);
        return success(BeanUtils.toBean(dietitionInfo, DietitionInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得营养师信息分页")
    @PreAuthorize("@ss.hasPermission('member:dietition-info:query')")
    public CommonResult<PageResult<DietitionInfoRespVO>> getDietitionInfoPage(@Valid DietitionInfoPageReqVO pageReqVO) {
        PageResult<DietitionInfoDO> pageResult = dietitionInfoService.getDietitionInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitionInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出营养师信息 Excel")
    @PreAuthorize("@ss.hasPermission('member:dietition-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDietitionInfoExcel(@Valid DietitionInfoPageReqVO pageReqVO,
                                         HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DietitionInfoDO> list = dietitionInfoService.getDietitionInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "营养师信息.xls", "数据", DietitionInfoRespVO.class,
                BeanUtils.toBean(list, DietitionInfoRespVO.class));
    }

}