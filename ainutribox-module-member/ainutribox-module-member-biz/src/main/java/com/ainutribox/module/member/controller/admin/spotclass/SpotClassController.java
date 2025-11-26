package com.ainutribox.module.member.controller.admin.spotclass;

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

import com.ainutribox.module.member.controller.admin.spotclass.vo.*;
import com.ainutribox.module.member.dal.dataobject.spotclass.SpotClassDO;
import com.ainutribox.module.member.service.spotclass.SpotClassService;

@Tag(name = "管理后台 - 用户点赞表 本表删除使用物理删除")
@RestController
@RequestMapping("/member/spot-class")
@Validated
public class SpotClassController {

    @Resource
    private SpotClassService spotClassService;

    @PostMapping("/create")
    @Operation(summary = "创建用户点赞表 本表删除使用物理删除")
    @PreAuthorize("@ss.hasPermission('member:spot-class:create')")
    public CommonResult<Long> createSpotClass(@Valid @RequestBody SpotClassSaveReqVO createReqVO) {
        return success(spotClassService.createSpotClass(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新用户点赞表 本表删除使用物理删除")
    @PreAuthorize("@ss.hasPermission('member:spot-class:update')")
    public CommonResult<Boolean> updateSpotClass(@Valid @RequestBody SpotClassSaveReqVO updateReqVO) {
        spotClassService.updateSpotClass(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除用户点赞表 本表删除使用物理删除")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:spot-class:delete')")
    public CommonResult<Boolean> deleteSpotClass(@RequestParam("id") Long id) {
        spotClassService.deleteSpotClass(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得用户点赞表 本表删除使用物理删除")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:spot-class:query')")
    public CommonResult<SpotClassRespVO> getSpotClass(@RequestParam("id") Long id) {
        SpotClassDO spotClass = spotClassService.getSpotClass(id);
        return success(BeanUtils.toBean(spotClass, SpotClassRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户点赞表 本表删除使用物理删除分页")
    @PreAuthorize("@ss.hasPermission('member:spot-class:query')")
    public CommonResult<PageResult<SpotClassRespVO>> getSpotClassPage(@Valid SpotClassPageReqVO pageReqVO) {
        PageResult<SpotClassDO> pageResult = spotClassService.getSpotClassPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SpotClassRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出用户点赞表 本表删除使用物理删除 Excel")
    @PreAuthorize("@ss.hasPermission('member:spot-class:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSpotClassExcel(@Valid SpotClassPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SpotClassDO> list = spotClassService.getSpotClassPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "用户点赞表 本表删除使用物理删除.xls", "数据", SpotClassRespVO.class,
                        BeanUtils.toBean(list, SpotClassRespVO.class));
    }

}