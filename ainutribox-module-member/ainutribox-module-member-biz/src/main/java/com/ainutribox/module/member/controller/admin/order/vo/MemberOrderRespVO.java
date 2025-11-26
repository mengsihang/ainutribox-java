package com.ainutribox.module.member.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户购买课程和VIP订单 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MemberOrderRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "16326")
    @ExcelProperty("主键编号")
    private Long id;

    @Schema(description = "流水号")
    @ExcelProperty("流水号")
    private String no;

    @Schema(description = "订单类型 1课程 2vip", example = "1")
    @ExcelProperty("订单类型 1课程 2vip")
    private Integer type;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "12447")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "订单来源终端", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单来源终端")
    private Integer terminal;

    @Schema(description = "用户 IP", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("用户 IP")
    private String userIp;

    @Schema(description = "订单状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("订单状态")
    private Integer status;

    @Schema(description = "支付订单编号", example = "7774")
    @ExcelProperty("支付订单编号")
    private Long payOrderId;

    @Schema(description = "是否已支付：[0:未支付 1:已经支付过]", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("是否已支付：[0:未支付 1:已经支付过]")
    private Boolean payStatus;

    @Schema(description = "订单支付时间")
    @ExcelProperty("订单支付时间")
    private LocalDateTime payTime;

    @Schema(description = "支付成功的支付渠道")
    @ExcelProperty("支付成功的支付渠道")
    private String payChannelCode;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "课程编号或者vip套餐编号", example = "3330")
    @ExcelProperty("课程编号或者vip套餐编号")
    private Long buyId;

    @Schema(description = "课程名称或者套餐名", example = "李四")
    @ExcelProperty("课程名称或者套餐名")
    private String buyName;

    @Schema(description = "应付金额（总），单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "2181")
    @ExcelProperty("应付金额（总），单位：分")
    private Integer payPrice;

    @Schema(description = "营养师ID", example = "2181")
    @ExcelProperty("营养师ID")
    private Long dietitionId;

    @Schema(description = "持续月份vip单有值", example = "2181")
    @ExcelProperty("持续月份vip单有值")
    private Integer durationMonth;

    @Schema(description = "取消下单时间")
    @ExcelProperty("取消下单时间")
    private LocalDateTime cancelTime;

    @Schema(description = "取消下单类型", example = "2181")
    @ExcelProperty("取消下单类型")
    private Integer cancelType;

}