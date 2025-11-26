package com.ainutribox.module.member.controller.admin.dietitionclasscomment.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 营养师课程评论新增/修改 Request VO")
@Data
public class DietitionClassCommentSaveReqVO {

    @Schema(description = "评论编号，主键自增", requiredMode = Schema.RequiredMode.REQUIRED, example = "3443")
    private Long id;

    @Schema(description = "评价人的用户编号，关联 MemberUserDO 的 id 编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31863")
    @NotNull(message = "评价人的用户编号，关联 MemberUserDO 的 id 编号不能为空")
    private Long userId;

    @Schema(description = "评价人名称", example = "李四")
    private String userNickname;

    @Schema(description = "评价人头像")
    private String userAvatar;

    @Schema(description = "回复人名称", example = "赵六")
    private String dietitionNickname;

    @Schema(description = "回复人avatar")
    private String dietitionAvatar;

    @Schema(description = "是否匿名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "是否匿名不能为空")
    private Boolean anonymous;

    @Schema(description = "是否可见，true:显示false:隐藏")
    private Boolean visible;

    @Schema(description = "评分星级1-5分", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "评分星级1-5分不能为空")
    private Integer scores;

    @Schema(description = "描述星级 1-5 星", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "描述星级 1-5 星不能为空")
    private Integer descriptionScores;

    @Schema(description = "评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "评论内容不能为空")
    private String content;

    @Schema(description = "评论图片地址数组")
    private String picUrls;

    @Schema(description = "营养师是否回复", example = "1")
    private Boolean replyStatus;

    @Schema(description = "回复图片地址数组")
    private String replyUrls;

    @Schema(description = "营养师编号", example = "19714")
    private Long dietitionId;

    @Schema(description = "营养师回复内容")
    private String replyContent;

    @Schema(description = "营养师回复时间")
    private LocalDateTime replyTime;

    @Schema(description = "课程id", example = "28449")
    private Long classId;

}