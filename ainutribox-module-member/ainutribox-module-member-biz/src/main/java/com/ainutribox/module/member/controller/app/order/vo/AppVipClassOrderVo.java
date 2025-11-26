package com.ainutribox.module.member.controller.app.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AppVipClassOrderVo
 *
 * @author lucifer
 * @date 2024-06-28 10:57
 */

@Schema(description = "用户 App - VIP或者课程订单计算价格")
@Data
public class AppVipClassOrderVo {

    /**
     * 付款总金额
     */
    private Integer payPrice;

    /**
     * 课程名或者vip套餐名
     */
    private String byName;

    /**
     * 营养师ID 如果是课程有该字段
     */
    private Long dietitonId;

    /**
     * 持续月数 vip有该字段
     */
    private Integer durationMonth;

}
