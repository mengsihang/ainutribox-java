package com.ainutribox.module.member.controller.admin.question.vo;

import com.ainutribox.module.member.controller.admin.questionanswer.vo.QuestionAnswerRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户题库 Response VO")
@Data
@ExcelIgnoreUnannotated
public class QuestionRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28257")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "问题类型：0 基础问题 1 专项问题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("问题类型：0 基础问题 1 专项问题")
    private Integer typeNum;

    @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("标题")
    private String title;

    @Schema(description = "图片", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @ExcelProperty("图片")
    private String picUrl;

    @Schema(description = "答案  （所有答案，字符串拼接）")
    @ExcelProperty("答案  （所有答案，字符串拼接）")
    private String answer;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("排序")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty("状态")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "多选答案数量 默认1", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("多选答案数量 默认1")
    private Integer limitNum;


}