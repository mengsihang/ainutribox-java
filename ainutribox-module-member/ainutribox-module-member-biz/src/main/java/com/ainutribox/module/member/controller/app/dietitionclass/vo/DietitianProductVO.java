package com.ainutribox.module.member.controller.app.dietitionclass.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 营养师自组营养包 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DietitianProductVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25852")
    private Long id;

    @Schema(description = "商品数据")
    private String productText;

    @Schema(description = "营养师id", example = "31382")
    private Long dietitianId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}