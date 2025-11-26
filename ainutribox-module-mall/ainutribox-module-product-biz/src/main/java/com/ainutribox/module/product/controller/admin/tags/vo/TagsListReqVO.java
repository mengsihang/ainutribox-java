package com.ainutribox.module.product.controller.admin.tags.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 商品品牌分页 Request VO")
@Data
public class TagsListReqVO {
    @Schema(description = "标签id", example = "1")
    private String id;
    @Schema(description = "标签名称", example = "苹果")
    private String name;
}
