package com.ainutribox.module.member.controller.admin.dietitionclasschild.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 课程小节分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DietitionClassChildPageReqVO extends PageParam {

    @Schema(description = "课程主表id", example = "10413")
    private Long classId;

    @Schema(description = "小节名称", example = "芋艿")
    private String classHoureName;

    @Schema(description = "小节详情")
    private String classHoureDetail;

    @Schema(description = "营养师ID", example = "14366")
    private Long dietitionId;

    @Schema(description = "音频url", example = "https://www.iocoder.cn")
    private String videoUrl;

    @Schema(description = "音频时长")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private Integer[] videoTime;

    @Schema(description = "小节类型 0试听 1会员免费 2收费", example = "2")
    private Integer childType;

    @Schema(description = "小节序号")
    private Integer serialNumber;

    @Schema(description = "状态 0上架 1下架", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}