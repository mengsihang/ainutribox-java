package com.ainutribox.module.member.controller.admin.dietitionclasschild;

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

import com.ainutribox.module.member.controller.admin.dietitionclasschild.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietitionclasschild.DietitionClassChildDO;
import com.ainutribox.module.member.service.dietitionclasschild.DietitionClassChildService;

@Tag(name = "管理后台 - 课程小节")
@RestController
@RequestMapping("/member/dietition-class-child")
@Validated
public class DietitionClassChildController {

    @Resource
    private DietitionClassChildService dietitionClassChildService;

    @PostMapping("/create")
    @Operation(summary = "创建课程小节")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-child:create')")
    public CommonResult<Long> createDietitionClassChild(@Valid @RequestBody DietitionClassChildSaveReqVO createReqVO) {
        return success(dietitionClassChildService.createDietitionClassChild(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新课程小节")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-child:update')")
    public CommonResult<Boolean> updateDietitionClassChild(@Valid @RequestBody DietitionClassChildSaveReqVO updateReqVO) {
        dietitionClassChildService.updateDietitionClassChild(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除课程小节")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:dietition-class-child:delete')")
    public CommonResult<Boolean> deleteDietitionClassChild(@RequestParam("id") Long id) {
        dietitionClassChildService.deleteDietitionClassChild(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得课程小节")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-child:query')")
    public CommonResult<DietitionClassChildRespVO> getDietitionClassChild(@RequestParam("id") Long id) {
        DietitionClassChildDO dietitionClassChild = dietitionClassChildService.getDietitionClassChild(id);
        return success(BeanUtils.toBean(dietitionClassChild, DietitionClassChildRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得课程小节分页")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-child:query')")
    public CommonResult<PageResult<DietitionClassChildRespVO>> getDietitionClassChildPage(@Valid DietitionClassChildPageReqVO pageReqVO) {
        PageResult<DietitionClassChildDO> pageResult = dietitionClassChildService.getDietitionClassChildPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitionClassChildRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出课程小节 Excel")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-child:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDietitionClassChildExcel(@Valid DietitionClassChildPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DietitionClassChildDO> list = dietitionClassChildService.getDietitionClassChildPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "课程小节.xls", "数据", DietitionClassChildRespVO.class,
                        BeanUtils.toBean(list, DietitionClassChildRespVO.class));
    }

}