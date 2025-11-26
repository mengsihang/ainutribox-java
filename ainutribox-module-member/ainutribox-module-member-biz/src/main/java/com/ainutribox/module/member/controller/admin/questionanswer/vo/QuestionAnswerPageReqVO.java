package com.ainutribox.module.member.controller.admin.questionanswer.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 用户题库答案分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class QuestionAnswerPageReqVO extends PageParam {

    @Schema(description = "图片", example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "答案")
    private String answer;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态", example = "2")
    private Integer status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "题目编号", example = "978")
    private Long questionId;

    @Schema(description = "操作类型")
    private Integer optionType;

    @Schema(description = "操作对应题目id", example = "22049")
    private String optionId;

}