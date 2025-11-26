package com.ainutribox.module.member.controller.admin.payclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户购买课程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PayClassRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12557")
    @ExcelProperty("主键编号")
    private Long id;

    @Schema(description = "用户id", example = "14669")
    @ExcelProperty("用户id")
    private Long userId;

    @Schema(description = "课程id", example = "8430")
    @ExcelProperty("课程id")
    private Long classId;

    @Schema(description = "订单ID 哪个订单买的", example = "23001")
    @ExcelProperty("订单ID 哪个订单买的")
    private Long orderId;

    @Schema(description = "老师ID")
    @ExcelProperty("老师ID")
    private Long dietition;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}