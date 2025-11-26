package com.ainutribox.module.trade.controller.admin.dietitianorderlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 营养师自组营养包售卖订单日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DietitianOrderLogRespVO {

    @Schema(description = "日志主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "13367")
    @ExcelProperty("日志主键")
    private Long id;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "21802")
    @ExcelProperty("用户编号")
    private Long userId;

    @Schema(description = "用户类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("用户类型")
    private Integer userType;

    @Schema(description = "营养师id", example = "23793")
    @ExcelProperty("营养师id")
    private Long dietitianId;

    @Schema(description = "订单号", requiredMode = Schema.RequiredMode.REQUIRED, example = "25396")
    @ExcelProperty("订单号")
    private Long orderId;

    @Schema(description = "订单流水号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("订单流水号")
    private String orderNo;

    @Schema(description = "状态 0不展示 1展示", example = "1")
    @ExcelProperty("状态 0不展示 1展示")
    private Integer showStatus;

    @Schema(description = "结算状态 0未结算 1结算", example = "1")
    @ExcelProperty("结算状态 0未结算 1结算")
    private Integer 
settlementStatus;

    @Schema(description = "应付金额（总），单位：分", requiredMode = Schema.RequiredMode.REQUIRED, example = "5568")
    @ExcelProperty("应付金额（总），单位：分")
    private Integer payPrice;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "同一个订单下第几组整合")
    @ExcelProperty("同一个订单下第几组整合")
    private Integer itemNumber;

    @Schema(description = "营养师组装商品名(dietitian_product)", example = "赵六")
    @ExcelProperty("营养师组装商品名(dietitian_product)")
    private String dietitianSpuName;

    @Schema(description = "营养师组装商品编号(dietitian_product)", example = "13002")
    @ExcelProperty("营养师组装商品编号(dietitian_product)")
    private Long dietitianSpuId;

}