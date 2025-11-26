package com.ainutribox.module.member.controller.admin.report.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 用户报告新增/修改 Request VO")
@Data
public class ReportSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14026")
    private Long id;

    @Schema(description = "报告内容")
    private String content;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序不能为空")
    private Integer sort;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态不能为空")
    private Integer status;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9090")
    @NotNull(message = "用户id不能为空")
    private Long memberId;

    @Schema(description = "报告名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "报告名称不能为空")
    private String reportName;

}