package com.ainutribox.module.product.controller.admin.dietitian.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 营养师自组营养包新增/修改 Request VO")
@Data
public class DietitianSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25852")
    private Long id;

    @Schema(description = "商品数据")
    private String productText;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "营养师id", example = "31382")
    private Long dietitianId;

}