package com.ainutribox.module.trade.controller.admin.dietitianorderlog.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 营养师自组营养包售卖订单日志分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DietitianOrderLogPageReqVO extends PageParam {

    @Schema(description = "用户编号", example = "21802")
    private Long userId;

    @Schema(description = "用户类型", example = "2")
    private Integer userType;

    @Schema(description = "营养师id", example = "23793")
    private Long dietitianId;

    @Schema(description = "订单号", example = "25396")
    private Long orderId;

    @Schema(description = "订单流水号")
    private String orderNo;

    @Schema(description = "状态 0不展示 1展示", example = "1")
    private Integer showStatus;

    @Schema(description = "结算状态 0未结算 1结算", example = "1")
    private Integer 
settlementStatus;

    @Schema(description = "应付金额（总），单位：分", example = "5568")
    private Integer payPrice;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "同一个订单下第几组整合")
    private Integer itemNumber;

    @Schema(description = "营养师组装商品名(dietitian_product)", example = "赵六")
    private String dietitianSpuName;

    @Schema(description = "营养师组装商品编号(dietitian_product)", example = "13002")
    private Long dietitianSpuId;

}