package com.ainutribox.module.member.controller.admin.vippackage;

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

import com.ainutribox.module.member.controller.admin.vippackage.vo.*;
import com.ainutribox.module.member.dal.dataobject.vippackage.VipPackageDO;
import com.ainutribox.module.member.service.vippackage.VipPackageService;

@Tag(name = "管理后台 - vip套餐")
@RestController
@RequestMapping("/member/vip-package")
@Validated
public class VipPackageController {

    @Resource
    private VipPackageService vipPackageService;

    @PostMapping("/create")
    @Operation(summary = "创建vip套餐")
    @PreAuthorize("@ss.hasPermission('member:vip-package:create')")
    public CommonResult<Long> createVipPackage(@Valid @RequestBody VipPackageSaveReqVO createReqVO) {
        return success(vipPackageService.createVipPackage(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新vip套餐")
    @PreAuthorize("@ss.hasPermission('member:vip-package:update')")
    public CommonResult<Boolean> updateVipPackage(@Valid @RequestBody VipPackageSaveReqVO updateReqVO) {
        vipPackageService.updateVipPackage(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除vip套餐")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:vip-package:delete')")
    public CommonResult<Boolean> deleteVipPackage(@RequestParam("id") Long id) {
        vipPackageService.deleteVipPackage(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得vip套餐")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:vip-package:query')")
    public CommonResult<VipPackageRespVO> getVipPackage(@RequestParam("id") Long id) {
        VipPackageDO vipPackage = vipPackageService.getVipPackage(id);
        return success(BeanUtils.toBean(vipPackage, VipPackageRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得vip套餐分页")
    @PreAuthorize("@ss.hasPermission('member:vip-package:query')")
    public CommonResult<PageResult<VipPackageRespVO>> getVipPackagePage(@Valid VipPackagePageReqVO pageReqVO) {
        PageResult<VipPackageDO> pageResult = vipPackageService.getVipPackagePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, VipPackageRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出vip套餐 Excel")
    @PreAuthorize("@ss.hasPermission('member:vip-package:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVipPackageExcel(@Valid VipPackagePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<VipPackageDO> list = vipPackageService.getVipPackagePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "vip套餐.xls", "数据", VipPackageRespVO.class,
                        BeanUtils.toBean(list, VipPackageRespVO.class));
    }

}