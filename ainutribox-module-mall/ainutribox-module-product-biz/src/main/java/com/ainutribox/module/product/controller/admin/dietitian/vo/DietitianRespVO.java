package com.ainutribox.module.product.controller.admin.dietitian.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 营养师自组营养包 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DietitianRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25852")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "商品数据")
    @ExcelProperty("商品数据")
    private String productText;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "营养师id", example = "31382")
    @ExcelProperty("营养师id")
    private Long dietitianId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}