package com.ainutribox.module.member.controller.admin.dietitionclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 营养师课程新增/修改 Request VO")
@Data
public class DietitionClassSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4534")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @Schema(description = "列表图片", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotEmpty(message = "列表图片不能为空")
    private String picUrl;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "描述", example = "你说的对")
    private String description;

    @Schema(description = "状态 0上架 1下架", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态 0上架 1下架不能为空")
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

    @Schema(description = "原价 存储单位分", example = "20486")
    private Integer price;

    @Schema(description = "会员价格 存储单位分", example = "8894")
    private Integer vipPrice;

    @Schema(description = "课程分类", example = "29230")
    private Long categoryId;

}