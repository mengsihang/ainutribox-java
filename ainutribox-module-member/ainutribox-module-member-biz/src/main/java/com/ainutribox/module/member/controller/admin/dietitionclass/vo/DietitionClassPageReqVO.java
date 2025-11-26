package com.ainutribox.module.member.controller.admin.dietitionclass.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 营养师课程分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DietitionClassPageReqVO extends PageParam {

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "列表图片", example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "描述", example = "你说的对")
    private String description;

    @Schema(description = "状态 0上架 1下架", example = "1")
    private Integer status;

    @Schema(description = "营养师ID", example = "31418")
    private Long dietitionId;

    @Schema(description = "课程标题")
    private String courseTitle;

    @Schema(description = "课程路径", example = "https://www.iocoder.cn")
    private String classUrl;

    @Schema(description = "课程类型 0 图文 1 视频", example = "2")
    private Integer classType;

    @Schema(description = "详情图片")
    private String infoPic;

    @Schema(description = "课程标签 字符串@分割")
    private String tags;

    @Schema(description = "评论数")
    private Long commentNum;

    @Schema(description = "点赞数")
    private Long likeNum;

    @Schema(description = "评分")
    private Integer score;

    @Schema(description = "实际人数")
    private Integer actualPeople;

    @Schema(description = "虚拟人数")
    private Integer virtualPeople;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "原价 存储单位分", example = "20486")
    private Integer price;

    @Schema(description = "会员价格 存储单位分", example = "8894")
    private Integer vipPrice;

    @Schema(description = "课程分类", example = "29230")
    private Long categoryId;

}