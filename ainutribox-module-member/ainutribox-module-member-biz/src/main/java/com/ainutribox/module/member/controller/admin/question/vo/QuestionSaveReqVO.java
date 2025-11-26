package com.ainutribox.module.member.controller.admin.question.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 用户题库新增/修改 Request VO")
@Data
public class QuestionSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28257")
    private Long id;

    @Schema(description = "问题类型：0 基础问题 1 专项问题", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "问题类型：0 基础问题 1 专项问题不能为空")
    private Integer typeNum;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "图片", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    private String picUrl;

    @Schema(description = "答案  （所有答案，字符串拼接）")
    private String answer;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "多选答案数量 默认1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer limitNum;


}