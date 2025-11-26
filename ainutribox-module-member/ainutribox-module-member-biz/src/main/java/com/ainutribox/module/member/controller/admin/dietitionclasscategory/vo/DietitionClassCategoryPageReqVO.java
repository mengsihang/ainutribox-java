package com.ainutribox.module.member.controller.admin.dietitionclasscategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 营养师课程分类分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DietitionClassCategoryPageReqVO extends PageParam {

    @Schema(description = "名称", example = "李四")
    private String name;

    @Schema(description = "图片", example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "描述", example = "随便")
    private String description;

    @Schema(description = "状态", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}