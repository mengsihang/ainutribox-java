package com.ainutribox.module.member.controller.app.dietitionclass.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * AppClassCommentDTO
 *
 * @author lucifer
 * @date 2024-07-02 10:33
 */
@Schema(description = "用户 app - 用户发评论")
@Data
public class AppClassCommentDTO {

    @Schema(description = "是否匿名")
    private Boolean anonymous;

    @Schema(description = "星级评分")
    @NotNull(message = "星级评分不能为null")
    @Min(value = 0, message = "值不能小于0")
    @Max(value = 5, message = "值不能大于5")
    private Integer scores;

    @Schema(description = "评论内容")
    @NotBlank(message = "评论内容不能为null")
    private String content;

    @Schema(description = "评论图片地址数组")
    private String picUrls;

    @Schema(description = "课程Id")
    @NotNull(message = "课程不能为空")
    private Long classId;
}
