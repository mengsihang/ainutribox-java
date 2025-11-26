package com.ainutribox.module.member.controller.admin.joinclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 用户课程新增/修改 Request VO")
@Data
public class JoinClassSaveReqVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31365")
    private Long id;

    @Schema(description = "用户id", example = "2046")
    private Long memberId;

    @Schema(description = "课程id", example = "19646")
    private Long classId;

}