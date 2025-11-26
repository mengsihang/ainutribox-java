package com.ainutribox.module.member.controller.admin.vippackage.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - vip套餐分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VipPackagePageReqVO extends PageParam {

    @Schema(description = "价格", example = "13690")
    private Integer price;

    @Schema(description = "持续月数 年套餐请填写12月")
    private Integer durationMonth;

    @Schema(description = "套餐名", example = "李四")
    private String packageName;

    @Schema(description = "套餐简介")
    private String packageBrief;

    @Schema(description = "活动优惠价格", example = "31767")
    private Integer activityPrice;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}