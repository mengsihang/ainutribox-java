package com.ainutribox.module.member.controller.admin.questionanswer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.ainutribox.framework.excel.core.annotations.DictFormat;
import com.ainutribox.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 用户题库答案 Response VO")
@Data
@ExcelIgnoreUnannotated
public class QuestionAnswerRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "15569")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "图片", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("图片")
    private String picUrl;

    @Schema(description = "答案")
    @ExcelProperty("答案")
    private String answer;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "题目编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "978")
    @ExcelProperty("题目编号")
    private Long questionId;

    @Schema(description = "操作类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "操作类型", converter = DictConvert.class)
    @DictFormat("member_question_answer_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer optionType;

    @Schema(description = "操作对应题目id", example = "22049")
    @ExcelProperty("操作对应题目id")
    private String optionId;

}