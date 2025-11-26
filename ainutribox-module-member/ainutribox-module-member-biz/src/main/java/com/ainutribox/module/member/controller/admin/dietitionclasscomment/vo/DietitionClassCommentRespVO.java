package com.ainutribox.module.member.controller.admin.dietitionclasscomment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 营养师课程评论 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DietitionClassCommentRespVO {

    @Schema(description = "评论编号，主键自增", requiredMode = Schema.RequiredMode.REQUIRED, example = "3443")
    @ExcelProperty("评论编号，主键自增")
    private Long id;

    @Schema(description = "评价人的用户编号，关联 MemberUserDO 的 id 编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31863")
    @ExcelProperty("评价人的用户编号，关联 MemberUserDO 的 id 编号")
    private Long userId;

    @Schema(description = "评价人名称", example = "李四")
    @ExcelProperty("评价人名称")
    private String userNickname;

    @Schema(description = "评价人头像")
    @ExcelProperty("评价人头像")
    private String userAvatar;

    @Schema(description = "回复人名称", example = "赵六")
    @ExcelProperty("回复人名称")
    private String dietitionNickname;

    @Schema(description = "回复人avatar")
    @ExcelProperty("回复人avatar")
    private String dietitionAvatar;

    @Schema(description = "是否匿名", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("是否匿名")
    private Boolean anonymous;

    @Schema(description = "是否可见，true:显示false:隐藏")
    @ExcelProperty("是否可见，true:显示false:隐藏")
    private Boolean visible;

    @Schema(description = "评分星级1-5分", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("评分星级1-5分")
    private Integer scores;

    @Schema(description = "描述星级 1-5 星", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("描述星级 1-5 星")
    private Integer descriptionScores;

    @Schema(description = "评论内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("评论内容")
    private String content;

    @Schema(description = "评论图片地址数组")
    @ExcelProperty("评论图片地址数组")
    private String picUrls;

    @Schema(description = "营养师是否回复", example = "1")
    @ExcelProperty("营养师是否回复")
    private Boolean replyStatus;

    @Schema(description = "营养师编号", example = "19714")
    @ExcelProperty("营养师编号")
    private Long dietitionId;

    @Schema(description = "营养师回复内容")
    @ExcelProperty("营养师回复内容")
    private String replyContent;

    @Schema(description = "回复图片地址数组")
    @ExcelProperty("回复图片地址数组")
    private String replyUrls;

    @Schema(description = "营养师回复时间")
    @ExcelProperty("营养师回复时间")
    private LocalDateTime replyTime;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "课程id", example = "28449")
    @ExcelProperty("课程id")
    private Long classId;

}