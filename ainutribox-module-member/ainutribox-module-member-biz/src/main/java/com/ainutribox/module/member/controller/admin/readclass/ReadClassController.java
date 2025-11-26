package com.ainutribox.module.member.controller.admin.readclass;

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

import com.ainutribox.module.member.controller.admin.readclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.readclass.ReadClassDO;
import com.ainutribox.module.member.service.readclass.ReadClassService;

@Tag(name = "管理后台 - 用户阅读记录")
@RestController
@RequestMapping("/member/read-class")
@Validated
public class ReadClassController {

    @Resource
    private ReadClassService readClassService;

    @PostMapping("/create")
    @Operation(summary = "创建用户阅读记录")
    @PreAuthorize("@ss.hasPermission('member:read-class:create')")
    public CommonResult<Long> createReadClass(@Valid @RequestBody ReadClassSaveReqVO createReqVO) {
        return success(readClassService.createReadClass(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户阅读记录")
    @PreAuthorize("@ss.hasPermission('member:read-class:update')")
    public CommonResult<Boolean> updateReadClass(@Valid @RequestBody ReadClassSaveReqVO updateReqVO) {
        readClassService.updateReadClass(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户阅读记录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:read-class:delete')")
    public CommonResult<Boolean> deleteReadClass(@RequestParam("id") Long id) {
        readClassService.deleteReadClass(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户阅读记录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:read-class:query')")
    public CommonResult<ReadClassRespVO> getReadClass(@RequestParam("id") Long id) {
        ReadClassDO readClass = readClassService.getReadClass(id);
        return success(BeanUtils.toBean(readClass, ReadClassRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户阅读记录分页")
    @PreAuthorize("@ss.hasPermission('member:read-class:query')")
    public CommonResult<PageResult<ReadClassRespVO>> getReadClassPage(@Valid ReadClassPageReqVO pageReqVO) {
        PageResult<ReadClassDO> pageResult = readClassService.getReadClassPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ReadClassRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户阅读记录 Excel")
    @PreAuthorize("@ss.hasPermission('member:read-class:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportReadClassExcel(@Valid ReadClassPageReqVO pageReqVO,
                                     HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<ReadClassDO> list = readClassService.getReadClassPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户阅读记录.xls", "数据", ReadClassRespVO.class,
                BeanUtils.toBean(list, ReadClassRespVO.class));
    }

}