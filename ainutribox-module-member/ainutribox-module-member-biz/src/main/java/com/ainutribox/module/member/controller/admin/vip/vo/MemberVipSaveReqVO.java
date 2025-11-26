package com.ainutribox.module.member.controller.admin.vip.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 用户vip新增/修改 Request VO")
@Data
public class MemberVipSaveReqVO {

    @Schema(description = "主键编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "14926")
    private Long id;

    @Schema(description = "用户id", example = "18706")
    private Long userId;

    @Schema(description = "vip开始时间")
    private LocalDateTime vipStartTime;

    @Schema(description = "vip结束时间")
    private LocalDateTime vipEndTime;

}