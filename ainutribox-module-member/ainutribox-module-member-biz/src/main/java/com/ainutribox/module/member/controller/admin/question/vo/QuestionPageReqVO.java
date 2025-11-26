package com.ainutribox.module.member.controller.admin.question.vo;

import com.ainutribox.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户题库分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QuestionPageReqVO extends PageParam {

    @Schema(description = "问题类型：0 基础问题 1 专项问题")
    private Integer typeNum;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "图片", example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "答案  （所有答案，字符串拼接）")
    private String answer;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态", example = "1")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "多选答案数量 默认1")
    private Integer limitNum;
}