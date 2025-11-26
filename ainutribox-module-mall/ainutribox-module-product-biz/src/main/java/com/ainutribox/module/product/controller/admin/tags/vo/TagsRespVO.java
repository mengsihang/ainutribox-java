package com.ainutribox.module.product.controller.admin.tags.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.ainutribox.framework.excel.core.annotations.DictFormat;
import com.ainutribox.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 商品标签 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TagsRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "17770")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "背景色", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("背景色")
    private String color;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "描述", example = "你猜")
    @ExcelProperty("描述")
    private String description;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}