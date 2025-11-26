package com.ainutribox.module.member.controller.admin.joinclass;

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

import com.ainutribox.module.member.controller.admin.joinclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.joinclass.JoinClassDO;
import com.ainutribox.module.member.service.joinclass.JoinClassService;

@Tag(name = "管理后台 - 用户课程")
@RestController
@RequestMapping("/member/join-class")
@Validated
public class JoinClassController {

    @Resource
    private JoinClassService joinClassService;

    @PostMapping("/create")
    @Operation(summary = "创建用户课程")
    @PreAuthorize("@ss.hasPermission('member:join-class:create')")
    public CommonResult<Long> createJoinClass(@Valid @RequestBody JoinClassSaveReqVO createReqVO) {
        return success(joinClassService.createJoinClass(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户课程")
    @PreAuthorize("@ss.hasPermission('member:join-class:update')")
    public CommonResult<Boolean> updateJoinClass(@Valid @RequestBody JoinClassSaveReqVO updateReqVO) {
        joinClassService.updateJoinClass(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户课程")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:join-class:delete')")
    public CommonResult<Boolean> deleteJoinClass(@RequestParam("id") Long id) {
        joinClassService.deleteJoinClass(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户课程")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:join-class:query')")
    public CommonResult<JoinClassRespVO> getJoinClass(@RequestParam("id") Long id) {
        JoinClassDO joinClass = joinClassService.getJoinClass(id);
        return success(BeanUtils.toBean(joinClass, JoinClassRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户课程分页")
    @PreAuthorize("@ss.hasPermission('member:join-class:query')")
    public CommonResult<PageResult<JoinClassRespVO>> getJoinClassPage(@Valid JoinClassPageReqVO pageReqVO) {
        PageResult<JoinClassDO> pageResult = joinClassService.getJoinClassPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, JoinClassRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户课程 Excel")
    @PreAuthorize("@ss.hasPermission('member:join-class:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportJoinClassExcel(@Valid JoinClassPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<JoinClassDO> list = joinClassService.getJoinClassPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户课程.xls", "数据", JoinClassRespVO.class,
                        BeanUtils.toBean(list, JoinClassRespVO.class));
    }

}