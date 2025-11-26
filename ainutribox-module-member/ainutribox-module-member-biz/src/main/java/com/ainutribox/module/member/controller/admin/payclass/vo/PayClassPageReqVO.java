package com.ainutribox.module.member.controller.admin.payclass.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户购买课程分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayClassPageReqVO extends PageParam {

    @Schema(description = "用户id", example = "14669")
    private Long userId;

    @Schema(description = "课程id", example = "8430")
    private Long classId;

    @Schema(description = "订单ID 哪个订单买的", example = "23001")
    private Long orderId;

    @Schema(description = "老师ID")
    private Long dietition;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}