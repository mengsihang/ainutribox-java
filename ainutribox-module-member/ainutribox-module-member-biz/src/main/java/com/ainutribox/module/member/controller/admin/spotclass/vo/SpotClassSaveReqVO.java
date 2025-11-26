package com.ainutribox.module.member.controller.admin.spotclass.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 用户点赞表 本表删除使用物理删除新增/修改 Request VO")
@Data
public class SpotClassSaveReqVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1250")
    private Long id;

    @Schema(description = "用户id", example = "5338")
    private Long userId;

    @Schema(description = "课程id", example = "20284")
    private Long classId;

}