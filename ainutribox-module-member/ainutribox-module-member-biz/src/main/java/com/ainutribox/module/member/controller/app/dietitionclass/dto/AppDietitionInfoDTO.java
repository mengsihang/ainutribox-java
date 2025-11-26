package com.ainutribox.module.member.controller.app.dietitionclass.dto;

import com.ainutribox.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AppDietitionInfoDTO
 *
 * @author lucifer
 * @date 2024-06-29 12:25
 */
@Schema(description = "用户 app - 导师查询")
@Data
public class AppDietitionInfoDTO {

    @Schema(description = "营养师ID", example = "31418")
    private Long dietitionId;

    @Schema(description = "排序字段")
    private String orderBy;

}
