package com.ainutribox.module.member.controller.admin.payclass;

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

import com.ainutribox.module.member.controller.admin.payclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.payclass.PayClassDO;
import com.ainutribox.module.member.service.payclass.PayClassService;

@Tag(name = "管理后台 - 用户购买课程")
@RestController
@RequestMapping("/member/pay-class")
@Validated
public class PayClassController {

    @Resource
    private PayClassService payClassService;

    @PostMapping("/create")
    @Operation(summary = "创建用户购买课程")
    @PreAuthorize("@ss.hasPermission('member:pay-class:create')")
    public CommonResult<Long> createPayClass(@Valid @RequestBody PayClassSaveReqVO createReqVO) {
        return success(payClassService.createPayClass(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户购买课程")
    @PreAuthorize("@ss.hasPermission('member:pay-class:update')")
    public CommonResult<Boolean> updatePayClass(@Valid @RequestBody PayClassSaveReqVO updateReqVO) {
        payClassService.updatePayClass(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户购买课程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:pay-class:delete')")
    public CommonResult<Boolean> deletePayClass(@RequestParam("id") Long id) {
        payClassService.deletePayClass(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户购买课程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:pay-class:query')")
    public CommonResult<PayClassRespVO> getPayClass(@RequestParam("id") Long id) {
        PayClassDO payClass = payClassService.getPayClass(id);
        return success(BeanUtils.toBean(payClass, PayClassRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户购买课程分页")
    @PreAuthorize("@ss.hasPermission('member:pay-class:query')")
    public CommonResult<PageResult<PayClassRespVO>> getPayClassPage(@Valid PayClassPageReqVO pageReqVO) {
        PageResult<PayClassDO> pageResult = payClassService.getPayClassPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, PayClassRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户购买课程 Excel")
    @PreAuthorize("@ss.hasPermission('member:pay-class:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportPayClassExcel(@Valid PayClassPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<PayClassDO> list = payClassService.getPayClassPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户购买课程.xls", "数据", PayClassRespVO.class,
                        BeanUtils.toBean(list, PayClassRespVO.class));
    }

}