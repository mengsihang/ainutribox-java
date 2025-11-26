package com.ainutribox.module.product.controller.admin.tags.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 商品标签新增/修改 Request VO")
@Data
public class TagsSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17770")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "背景色", requiredMode = Schema.RequiredMode.REQUIRED)
    private String color;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "描述", example = "你猜")
    private String description;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态不能为空")
    private Integer status;

}