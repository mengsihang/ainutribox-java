package com.ainutribox.module.member.controller.admin.agent.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 代理商记录新增/修改 Request VO")
@Data
public class AgentSaveReqVO {
    public static final Integer status_unpass = 0;
    public static final Integer status_pass = 1;

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "11048")
    private Long id;

    @Schema(description = "用户id", example = "22631")
    private Long userId;

    @Schema(description = "描述内容", requiredMode = Schema.RequiredMode.REQUIRED)
    private String content;

    @Schema(description = "申请状态：0 未通过 1 已通过", example = "1")
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