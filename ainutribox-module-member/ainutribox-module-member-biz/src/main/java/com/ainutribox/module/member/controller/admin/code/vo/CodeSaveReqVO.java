package com.ainutribox.module.member.controller.admin.code.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 积分兑换码新增/修改 Request VO")
@Data
public class CodeSaveReqVO {

    @Schema(description = "自增主键", requiredMode = Schema.RequiredMode.REQUIRED, example = "21520")
    private Long id;

    @Schema(description = "可兑换积分数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "可兑换积分数量不能为空")
    private Integer point;

    @Schema(description = "兑换码")
    private Integer code;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态不能为空")
    private Integer status;

}