package com.ainutribox.module.member.controller.admin.joinclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户课程 Response VO")
@Data
@ExcelIgnoreUnannotated
public class JoinClassRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31365")
    @ExcelProperty("主键编号")
    private Long id;

    @Schema(description = "用户id", example = "2046")
    @ExcelProperty("用户id")
    private Long memberId;

    @Schema(description = "课程id", example = "19646")
    @ExcelProperty("课程id")
    private Long classId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}