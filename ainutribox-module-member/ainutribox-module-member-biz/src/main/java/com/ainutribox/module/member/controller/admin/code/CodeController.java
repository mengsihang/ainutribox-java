package com.ainutribox.module.member.controller.admin.code;

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

import com.ainutribox.module.member.controller.admin.code.vo.*;
import com.ainutribox.module.member.dal.dataobject.code.CodeDO;
import com.ainutribox.module.member.service.code.CodeService;

@Tag(name = "管理后台 - 积分兑换码")
@RestController
@RequestMapping("/member/code")
@Validated
public class CodeController {

    @Resource
    private CodeService codeService;

    @PostMapping("/create")
    @Operation(summary = "创建积分兑换码")
    @PreAuthorize("@ss.hasPermission('member:code:create')")
    public CommonResult<Long> createCode(@Valid @RequestBody CodeSaveReqVO createReqVO) {
        return success(codeService.createCode(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新积分兑换码")
    @PreAuthorize("@ss.hasPermission('member:code:update')")
    public CommonResult<Boolean> updateCode(@Valid @RequestBody CodeSaveReqVO updateReqVO) {
        codeService.updateCode(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除积分兑换码")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:code:delete')")
    public CommonResult<Boolean> deleteCode(@RequestParam("id") Long id) {
        codeService.deleteCode(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得积分兑换码")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:code:query')")
    public CommonResult<CodeRespVO> getCode(@RequestParam("id") Long id) {
        CodeDO code = codeService.getCode(id);
        return success(BeanUtils.toBean(code, CodeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得积分兑换码分页")
    @PreAuthorize("@ss.hasPermission('member:code:query')")
    public CommonResult<PageResult<CodeRespVO>> getCodePage(@Valid CodePageReqVO pageReqVO) {
        PageResult<CodeDO> pageResult = codeService.getCodePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, CodeRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出积分兑换码 Excel")
    @PreAuthorize("@ss.hasPermission('member:code:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportCodeExcel(@Valid CodePageReqVO pageReqVO,
                                HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<CodeDO> list = codeService.getCodePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "积分兑换码.xls", "数据", CodeRespVO.class,
                BeanUtils.toBean(list, CodeRespVO.class));
    }

}