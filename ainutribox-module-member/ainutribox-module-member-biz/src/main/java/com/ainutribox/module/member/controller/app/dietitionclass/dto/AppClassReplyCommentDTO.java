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
@Schema(description = "用户 app - 导师回复评论")
@Data
public class AppClassReplyCommentDTO {


    @Schema(description = "评论内容")
    @NotBlank(message = "评论内容不能为null")
    private String replyContent;

    @Schema(description = "评论图片地址数组")
    private String replyUrls;

    @Schema(description = "评论Id")
    @NotNull(message = "评论id不能为空")
    private Long commentId;
}
