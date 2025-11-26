package com.ainutribox.module.member.controller.app.dietitionclass.dto;

import com.ainutribox.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * AppUserClassVo
 *
 * @author lucifer
 * @date 2024-06-29 12:25
 */
@Schema(description = "用户 app - 查询课程入参")
@Data
public class AppUserClassDTO extends PageParam {

    @Schema(description = "名称", example = "芋艿")
    private String name;

    @Schema(description = "课程标题")
    private String courseTitle;

    @Schema(description = "营养师ID", example = "31418")
    private Long dietitionId;

    @Schema(description = "排序字段")
    private String orderBy;

    @Schema(description = "课程分类", example = "29230")
    private Long categoryId;
}
