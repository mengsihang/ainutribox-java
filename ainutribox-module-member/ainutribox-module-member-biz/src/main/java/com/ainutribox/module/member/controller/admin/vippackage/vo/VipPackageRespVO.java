package com.ainutribox.module.member.controller.admin.vippackage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - vip套餐 Response VO")
@Data
@ExcelIgnoreUnannotated
public class VipPackageRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22336")
    @ExcelProperty("主键编号")
    private Long id;

    @Schema(description = "价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "13690")
    @ExcelProperty("价格(分)")
    private Integer price;

    @Schema(description = "持续月数 年套餐请填写12月")
    @ExcelProperty("持续月数 年套餐请填写12月")
    private Integer durationMonth;

    @Schema(description = "套餐名", example = "李四")
    @ExcelProperty("套餐名")
    private String packageName;

    @Schema(description = "套餐简介")
    @ExcelProperty("套餐简介")
    private String packageBrief;

    @Schema(description = "活动优惠价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "31767")
    @ExcelProperty("活动优惠价格（分）")
    private Integer activityPrice;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}