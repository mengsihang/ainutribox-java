package com.ainutribox.module.member.controller.admin.dietition.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 营养师信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DietitionInfoPageReqVO extends PageParam {

    @Schema(description = "用户id", example = "27536")
    private Long userId;

    @Schema(description = "描述内容")
    private String content;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "评分")
    private Long score;

    @Schema(description = "昵称", example = "王五")
    private String nickName;

    @Schema(description = "经验")
    private Long experience;

    @Schema(description = "好评率")
    private Long favorableRate;

    @Schema(description = "咨询量")
    private Long adviceNum;

    @Schema(description = "案例数")
    private Long caseNum;

    @Schema(description = "营养师等级 0 初级 1 中级 2 高级")
    private Integer level;

    @Schema(description = "图片")
    private String pic;

    @Schema(description = "详情图片")
    private String infoPic;

    @Schema(description = "名片背景")
    private String cardPic;

    private Integer status;

    @Schema(description = "手机号")
    private String phone;

}