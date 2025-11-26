package com.ainutribox.module.member.controller.admin.code.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.ainutribox.framework.excel.core.annotations.DictFormat;
import com.ainutribox.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 积分兑换码 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CodeRespVO {

    @Schema(description = "自增主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21520")
    @ExcelProperty("自增主键")
    private Long id;

    @Schema(description = "可兑换积分数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("可兑换积分数量")
    private Integer point;

    @Schema(description = "兑换码")
    @ExcelProperty("兑换码")
    private Integer code;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer status;

}