package com.ainutribox.module.member.controller.admin.payclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 用户购买课程新增/修改 Request VO")
@Data
public class PayClassSaveReqVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12557")
    private Long id;

    @Schema(description = "用户id", example = "14669")
    private Long userId;

    @Schema(description = "课程id", example = "8430")
    private Long classId;

    @Schema(description = "订单ID 哪个订单买的", example = "23001")
    private Long orderId;

    @Schema(description = "老师ID")
    private Long dietition;

}