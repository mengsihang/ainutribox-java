package com.ainutribox.module.member.controller.app.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AppVipClassOrderCreateReqVO
 *
 * @author lucifer
 * @date 2024-06-27 19:47
 */
@Schema(description = "用户 App - VIP或者课程订单入参 req VO")
@Data
public class AppVipClassOrderCreateReqVO {

    @Schema(description = "订单类型 1课程 2vip", example = "1")
    @NotNull(message = "类型不能为空")
    private Integer type;

    @Schema(description = "课程编号或者vip套餐编号", example = "3330")
    @NotNull(message = "编号不能为空")
    private Long buyId;
}
