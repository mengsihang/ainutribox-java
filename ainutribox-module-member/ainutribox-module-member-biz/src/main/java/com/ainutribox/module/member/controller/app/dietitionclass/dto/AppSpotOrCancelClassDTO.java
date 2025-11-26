package com.ainutribox.module.member.controller.app.dietitionclass.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AppJoinOrCanceClassDTO
 *
 * @author lucifer
 * @date 2024-07-01 10:26
 */
@Schema(description = "用户 app - 课程点赞或取消点赞")
@Data
public class AppSpotOrCancelClassDTO {

    @Schema(description = "课程编号")
    @NotNull(message = "课程编号不能为空")
    private Long classId;

    @Schema(description = "操作类型 1点赞 2取消")
    @NotNull(message = "操作类型不能为空")
    private Integer type;
}
