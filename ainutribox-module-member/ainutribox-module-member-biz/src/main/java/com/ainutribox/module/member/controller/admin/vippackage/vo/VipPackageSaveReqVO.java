package com.ainutribox.module.member.controller.admin.vippackage.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - vip套餐新增/修改 Request VO")
@Data
public class VipPackageSaveReqVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "22336")
    private Long id;

    @Schema(description = "价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "13690")
    @NotNull(message = "价格不能为空")
    private Integer price;

    @Schema(description = "持续月数 年套餐请填写12月")
    private Integer durationMonth;

    @Schema(description = "套餐名", example = "李四")
    private String packageName;

    @Schema(description = "套餐简介")
    private String packageBrief;

    @Schema(description = "活动优惠价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "31767")
    @NotNull(message = "活动优惠价格不能为空")
    private Integer activityPrice;

}