package com.ainutribox.module.member.controller.admin.vip;

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

import com.ainutribox.module.member.controller.admin.vip.vo.*;
import com.ainutribox.module.member.dal.dataobject.vip.MemberVipDO;
import com.ainutribox.module.member.service.vip.MemberVipService;

@Tag(name = "管理后台 - 用户vip")
@RestController
@RequestMapping("/member/vip")
@Validated
public class MemberVipController {

    @Resource
    private MemberVipService vipService;

    @PostMapping("/create")
    @Operation(summary = "创建用户vip")
    @PreAuthorize("@ss.hasPermission('member:vip:create')")
    public CommonResult<Long> createVip(@Valid @RequestBody MemberVipSaveReqVO createReqVO) {
        return success(vipService.createVip(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户vip")
    @PreAuthorize("@ss.hasPermission('member:vip:update')")
    public CommonResult<Boolean> updateVip(@Valid @RequestBody MemberVipSaveReqVO updateReqVO) {
        vipService.updateVip(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户vip")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:vip:delete')")
    public CommonResult<Boolean> deleteVip(@RequestParam("id") Long id) {
        vipService.deleteVip(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户vip")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:vip:query')")
    public CommonResult<MemberVipRespVO> getVip(@RequestParam("id") Long id) {
        MemberVipDO vip = vipService.getVip(id);
        return success(BeanUtils.toBean(vip, MemberVipRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户vip分页")
    @PreAuthorize("@ss.hasPermission('member:vip:query')")
    public CommonResult<PageResult<MemberVipRespVO>> getVipPage(@Valid MemberVipPageReqVO pageReqVO) {
        PageResult<MemberVipDO> pageResult = vipService.getVipPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MemberVipRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户vip Excel")
    @PreAuthorize("@ss.hasPermission('member:vip:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportVipExcel(@Valid MemberVipPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MemberVipDO> list = vipService.getVipPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户vip.xls", "数据", MemberVipRespVO.class,
                        BeanUtils.toBean(list, MemberVipRespVO.class));
    }

}