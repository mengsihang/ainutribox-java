package com.ainutribox.module.member.controller.app.dietitionclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * AppUserClassVo
 *
 * @author lucifer
 * @date 2024-06-29 12:25
 */
@Schema(description = "用户 app - 查询课程 显示")
@Data
public class AppUserClassVO {

    @Schema(description = "主键", example = "1")
    private Long id;

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "列表图片", example = "https://www.iocoder.cn")
    private String picUrl;

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
    private LocalDateTime createTime;

    @Schema(description = "原价 存储单位分", example = "20486")
    private Integer price;

    @Schema(description = "会员价格 存储单位分", example = "8894")
    private Integer vipPrice;

    @Schema(description = "课程分类", example = "29230")
    private Long categoryId;

    @Schema(description = "老师名称")
    private String nickName;

    @Schema(description = "老师形象照")
    private String pic;

    @Schema(description = "老师头像")
    private String dietitionInfoPic;

    @Schema(description = "导师等级")
    private Integer level;

    @Schema(description = "课程加入时间 我的课程列表有数据")
    private LocalDateTime joinTime;

    @Schema(description = "课程购买时间 购买课程列表有数据")
    private LocalDateTime buyTime;

    @Schema(description = "分类名称")
    private String categoryName;
}
