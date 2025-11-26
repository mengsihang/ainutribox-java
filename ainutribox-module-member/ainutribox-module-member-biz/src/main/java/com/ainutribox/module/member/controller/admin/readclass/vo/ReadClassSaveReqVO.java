package com.ainutribox.module.member.controller.admin.readclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 用户阅读记录新增/修改 Request VO")
@Data
public class ReadClassSaveReqVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "18319")
    private Long id;

    @Schema(description = "用户ID", example = "21006")
    private Long userId;

    @Schema(description = "课程id", example = "15307")
    private Long classId;

    @Schema(description = "阅读最大小节数")
    private Integer maxSerialNumber;

}