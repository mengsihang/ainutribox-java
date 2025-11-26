package com.ainutribox.module.member.controller.admin.readclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 用户阅读记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ReadClassRespVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18319")
    @ExcelProperty("主键编号")
    private Long id;

    @Schema(description = "用户ID", example = "21006")
    @ExcelProperty("用户ID")
    private Long userId;

    @Schema(description = "课程id", example = "15307")
    @ExcelProperty("课程id")
    private Long classId;

    @Schema(description = "阅读最大小节数")
    @ExcelProperty("阅读最大小节数")
    private Integer maxSerialNumber;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}