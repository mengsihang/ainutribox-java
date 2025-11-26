package com.ainutribox.module.member.controller.admin.dietitionclasscomment.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.ainutribox.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.ainutribox.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 营养师课程评论分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DietitionClassCommentPageReqVO extends PageParam {

    @Schema(description = "评价人的用户编号，关联 MemberUserDO 的 id 编号", example = "31863")
    private Long userId;

    @Schema(description = "评价人名称", example = "李四")
    private String userNickname;

    @Schema(description = "评价人头像")
    private String userAvatar;

    @Schema(description = "回复人名称", example = "赵六")
    private String dietitionNickname;

    @Schema(description = "回复人avatar")
    private String dietitionAvatar;

    @Schema(description = "是否匿名")
    private Boolean anonymous;

    @Schema(description = "是否可见，true:显示false:隐藏")
    private Boolean visible;

    @Schema(description = "评分星级1-5分")
    private Integer scores;

    @Schema(description = "描述星级 1-5 星")
    private Integer descriptionScores;

    @Schema(description = "评论内容")
    private String content;

    @Schema(description = "评论图片地址数组")
    private String picUrls;

    @Schema(description = "营养师是否回复", example = "1")
    private Boolean replyStatus;

    @Schema(description = "营养师编号", example = "19714")
    private Long dietitionId;

    @Schema(description = "营养师回复内容")
    private String replyContent;

    @Schema(description = "回复图片地址数组")
    private String replyUrls;

    @Schema(description = "营养师回复时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] replyTime;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "课程id", example = "28449")
    private Long classId;

}