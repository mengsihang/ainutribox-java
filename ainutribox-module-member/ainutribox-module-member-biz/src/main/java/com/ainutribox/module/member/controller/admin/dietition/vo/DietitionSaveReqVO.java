package com.ainutribox.module.member.controller.admin.dietition.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 营养师记录新增/修改 Request VO")
@Data
public class DietitionSaveReqVO {
    public static final Integer status_unpass = 0;
    public static final Integer status_pass = 1;
    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "30120")
    private Long id;

    @Schema(description = "用户id", example = "14333")
    private Long userId;

    @Schema(description = "描述内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "申请状态", example = "1")
    private Integer status;

    @Schema(description = "手机号")
    private Long tel;

    @Schema(description = "身份证正面")
    private String idPicFront;

    @Schema(description = "身份证反面")
    private String idPicBack;

    @Schema(description = "证书")
    private String certificatePic;

}