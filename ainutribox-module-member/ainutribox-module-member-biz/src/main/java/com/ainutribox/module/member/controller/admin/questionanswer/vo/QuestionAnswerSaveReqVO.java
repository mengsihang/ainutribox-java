package com.ainutribox.module.member.controller.admin.questionanswer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 用户题库答案新增/修改 Request VO")
@Data
public class QuestionAnswerSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15569")
    private Long id;

    @Schema(description = "图片", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "答案")
    private String answer;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "题目编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "978")
    @NotNull(message = "题目编号不能为空")
    private Long questionId;

    @Schema(description = "操作类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "操作类型不能为空")
    private Integer optionType;

    @Schema(description = "操作对应题目id", example = "22049")
    private String optionId;

}