package com.ainutribox.module.trade.controller.admin.dietitianorderlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 营养师自组营养包售卖订单日志新增/修改 Request VO")
@Data
public class DietitianOrderLogSaveReqVO {

    @Schema(description = "日志主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13367")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21802")
    @NotNull(message = "用户编号不能为空")
    private Long userId;

    @Schema(description = "用户类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "用户类型不能为空")
    private Integer userType;

    @Schema(description = "营养师id", example = "23793")
    private Long dietitianId;

    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25396")
    @NotNull(message = "订单号不能为空")
    private Long orderId;

    @Schema(description = "订单流水号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "订单流水号不能为空")
    private String orderNo;

    @Schema(description = "状态 0不展示 1展示", example = "1")
    private Integer showStatus;

    @Schema(description = "结算状态 0未结算 1结算", example = "1")
    private Integer 
settlementStatus;

    @Schema(description = "应付金额（总），单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "5568")
    @NotNull(message = "应付金额（总），单位：分不能为空")
    private Integer payPrice;

    @Schema(description = "同一个订单下第几组整合")
    private Integer itemNumber;

    @Schema(description = "营养师组装商品名(dietitian_product)", example = "赵六")
    private String dietitianSpuName;

    @Schema(description = "营养师组装商品编号(dietitian_product)", example = "13002")
    private Long dietitianSpuId;

}