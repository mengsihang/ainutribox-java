package com.ainutribox.module.member.controller.admin.dietitionclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 营养师课程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DietitionClassRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "4534")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("名称")
    private String name;

    @Schema(description = "列表图片", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("列表图片")
    private String picUrl;

    @Schema(description = "排序")
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "描述", example = "你说的对")
    @ExcelProperty("描述")
    private String description;

    @Schema(description = "状态 0上架 1下架", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态 0上架 1下架")
    private Integer status;

    @Schema(description = "营养师ID", example = "31418")
    @ExcelProperty("营养师ID")
    private Long dietitionId;

    @Schema(description = "课程标题")
    @ExcelProperty("课程标题")
    private String courseTitle;

    @Schema(description = "课程路径", example = "https://www.iocoder.cn")
    @ExcelProperty("课程路径")
    private String classUrl;

    @Schema(description = "课程类型 0 图文 1 视频", example = "2")
    @ExcelProperty("课程类型 0 图文 1 视频")
    private Integer classType;

    @Schema(description = "详情图片")
    @ExcelProperty("详情图片")
    private String infoPic;

    @Schema(description = "课程标签 字符串@分割")
    @ExcelProperty("课程标签 字符串@分割")
    private String tags;

    @Schema(description = "评论数")
    @ExcelProperty("评论数")
    private Long commentNum;

    @Schema(description = "点赞数")
    @ExcelProperty("点赞数")
    private Long likeNum;

    @Schema(description = "评分")
    @ExcelProperty("评分")
    private Integer score;

    @Schema(description = "实际人数")
    @ExcelProperty("实际人数")
    private Integer actualPeople;

    @Schema(description = "虚拟人数")
    @ExcelProperty("虚拟人数")
    private Integer virtualPeople;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "原价 存储单位分", example = "20486")
    @ExcelProperty("原价 存储单位分")
    private Integer price;

    @Schema(description = "会员价格 存储单位分", example = "8894")
    @ExcelProperty("会员价格 存储单位分")
    private Integer vipPrice;

    @Schema(description = "课程分类", example = "29230")
    @ExcelProperty("课程分类")
    private Long categoryId;

}