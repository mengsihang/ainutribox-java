package com.ainutribox.module.member.controller.admin.order.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户购买课程和VIP订单分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberOrderPageReqVO extends PageParam {

    @Schema(description = "流水号")
    private String no;

    @Schema(description = "订单类型 1课程 2vip", example = "1")
    private Integer type;

    @Schema(description = "用户编号", example = "12447")
    private Long userId;

    @Schema(description = "订单来源终端")
    private Integer terminal;

    @Schema(description = "用户 IP")
    private String userIp;

    @Schema(description = "订单状态", example = "1")
    private Integer status;

    @Schema(description = "支付订单编号", example = "7774")
    private Long payOrderId;

    @Schema(description = "是否已支付：[0:未支付 1:已经支付过]", example = "1")
    private Boolean payStatus;

    @Schema(description = "订单支付时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] payTime;

    @Schema(description = "支付成功的支付渠道")
    private String payChannelCode;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "课程编号或者vip套餐编号", example = "3330")
    private Long buyId;

    @Schema(description = "课程名称或者套餐名", example = "李四")
    private String buyName;

    @Schema(description = "应付金额（总），单位：分", example = "2181")
    private Integer payPrice;

    @Schema(description = "营养师ID", example = "2181")
    private Long dietitionId;

    @Schema(description = "持续月份vip单有值", example = "2181")
    private Integer durationMonth;

    @Schema(description = "取消下单时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime cancelTime;

    @Schema(description = "取消下单类型", example = "2181")
    private Integer cancelType;

}