package com.ainutribox.module.product.controller.app.spu.vo;

import com.ainutribox.module.product.dal.dataobject.category.ProductCategoryDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "用户 App - 商品 SPU Response VO")
@Data
public class CategoryWithProductsRespVO {

    @Schema(description = "商品分类")
    private ProductCategoryDO category;

    @Schema(description = "商品列表")
    private List<AppProductSpuRespVO> products;
}
