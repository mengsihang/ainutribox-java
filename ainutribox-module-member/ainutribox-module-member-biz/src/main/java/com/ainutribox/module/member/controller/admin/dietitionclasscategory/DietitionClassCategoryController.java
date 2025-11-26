package com.ainutribox.module.member.controller.admin.dietitionclasscategory;

import com.ainutribox.framework.common.enums.CommonStatusEnum;
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

import com.ainutribox.module.member.controller.admin.dietitionclasscategory.vo.*;
import com.ainutribox.module.member.dal.dataobject.dietitionclasscategory.DietitionClassCategoryDO;
import com.ainutribox.module.member.service.dietitionclasscategory.DietitionClassCategoryService;

@Tag(name = "管理后台 - 营养师课程分类")
@RestController
@RequestMapping("/member/dietition-class-category")
@Validated
public class DietitionClassCategoryController {

    @Resource
    private DietitionClassCategoryService dietitionClassCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建营养师课程分类")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-category:create')")
    public CommonResult<Long> createDietitionClassCategory(@Valid @RequestBody DietitionClassCategorySaveReqVO createReqVO) {
        return success(dietitionClassCategoryService.createDietitionClassCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新营养师课程分类")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-category:update')")
    public CommonResult<Boolean> updateDietitionClassCategory(@Valid @RequestBody DietitionClassCategorySaveReqVO updateReqVO) {
        dietitionClassCategoryService.updateDietitionClassCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除营养师课程分类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('member:dietition-class-category:delete')")
    public CommonResult<Boolean> deleteDietitionClassCategory(@RequestParam("id") Long id) {
        dietitionClassCategoryService.deleteDietitionClassCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得营养师课程分类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-category:query')")
    public CommonResult<DietitionClassCategoryRespVO> getDietitionClassCategory(@RequestParam("id") Long id) {
        DietitionClassCategoryDO dietitionClassCategory = dietitionClassCategoryService.getDietitionClassCategory(id);
        return success(BeanUtils.toBean(dietitionClassCategory, DietitionClassCategoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得营养师课程分类分页")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-category:query')")
    public CommonResult<PageResult<DietitionClassCategoryRespVO>> getDietitionClassCategoryPage(@Valid DietitionClassCategoryPageReqVO pageReqVO) {
        PageResult<DietitionClassCategoryDO> pageResult = dietitionClassCategoryService.getDietitionClassCategoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DietitionClassCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出营养师课程分类 Excel")
    @PreAuthorize("@ss.hasPermission('member:dietition-class-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDietitionClassCategoryExcel(@Valid DietitionClassCategoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DietitionClassCategoryDO> list = dietitionClassCategoryService.getDietitionClassCategoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "营养师课程分类.xls", "数据", DietitionClassCategoryRespVO.class,
                        BeanUtils.toBean(list, DietitionClassCategoryRespVO.class));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "获取品牌精简信息列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<DietitionClassCategoryRespVO>> getSimpleList() {
        // 获取品牌列表，只要开启状态的
        List<DietitionClassCategoryDO> list = dietitionClassCategoryService.getListByStatus(CommonStatusEnum.ENABLE.getStatus());
        // 排序后，返回给前端
        return success(BeanUtils.toBean(list, DietitionClassCategoryRespVO.class));
    }
}