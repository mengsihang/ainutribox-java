package com.ainutribox.module.product.controller.admin.tags;

import com.ainutribox.framework.common.enums.CommonStatusEnum;
import com.ainutribox.module.product.controller.admin.brand.vo.ProductBrandListReqVO;
import com.ainutribox.module.product.controller.admin.brand.vo.ProductBrandRespVO;
import com.ainutribox.module.product.controller.admin.brand.vo.ProductBrandSimpleRespVO;
import com.ainutribox.module.product.convert.brand.ProductBrandConvert;
import com.ainutribox.module.product.dal.dataobject.brand.ProductBrandDO;
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

import com.ainutribox.module.product.controller.admin.tags.vo.*;
import com.ainutribox.module.product.dal.dataobject.tags.TagsDO;
import com.ainutribox.module.product.service.tags.TagsService;

@Tag(name = "管理后台 - 商品标签")
@RestController
@RequestMapping("/product/tags")
@Validated
public class TagsController {

    @Resource
    private TagsService tagsService;

    @PostMapping("/create")
    @Operation(summary = "创建商品标签")
    @PreAuthorize("@ss.hasPermission('product:tags:create')")
    public CommonResult<Long> createTags(@Valid @RequestBody TagsSaveReqVO createReqVO) {
        return success(tagsService.createTags(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新商品标签")
    @PreAuthorize("@ss.hasPermission('product:tags:update')")
    public CommonResult<Boolean> updateTags(@Valid @RequestBody TagsSaveReqVO updateReqVO) {
        tagsService.updateTags(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除商品标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('product:tags:delete')")
    public CommonResult<Boolean> deleteTags(@RequestParam("id") Long id) {
        tagsService.deleteTags(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得商品标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('product:tags:query')")
    public CommonResult<TagsRespVO> getTags(@RequestParam("id") Long id) {
        TagsDO tags = tagsService.getTags(id);
        return success(BeanUtils.toBean(tags, TagsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得商品标签分页")
    @PreAuthorize("@ss.hasPermission('product:tags:query')")
    public CommonResult<PageResult<TagsRespVO>> getTagsPage(@Valid TagsPageReqVO pageReqVO) {
        PageResult<TagsDO> pageResult = tagsService.getTagsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, TagsRespVO.class));
    }



    @GetMapping("/list-all-simple")
    @Operation(summary = "获取品牌精简信息列表", description = "主要用于前端的下拉选项")
    public CommonResult<List<TagsRespVO>> getTagsList() {
        // 获取品牌列表，只要开启状态的
        List<TagsDO> list = tagsService.getTagsListByStatus(CommonStatusEnum.ENABLE.getStatus());
        // 排序后，返回给前端
        return success(BeanUtils.toBean(list, TagsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出商品标签 Excel")
    @PreAuthorize("@ss.hasPermission('product:tags:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportTagsExcel(@Valid TagsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<TagsDO> list = tagsService.getTagsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "商品标签.xls", "数据", TagsRespVO.class,
                        BeanUtils.toBean(list, TagsRespVO.class));
    }

}