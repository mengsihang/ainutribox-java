package com.ainutribox.module.member.dal.dataobject.dietitionclasscomment;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 营养师课程评论 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_dietition_class_comment")
@KeySequence("member_dietition_class_comment_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietitionClassCommentDO extends BaseDO {

    /**
     * 评论编号，主键自增
     */
    @TableId
    private Long id;
    /**
     * 评价人的用户编号，关联 MemberUserDO 的 id 编号
     */
    private Long userId;
    /**
     * 评价人名称
     */
    private String userNickname;
    /**
     * 评价人头像
     */
    private String userAvatar;
    /**
     * 回复人名称
     */
    private String dietitionNickname;
    /**
     * 回复人avatar
     */
    private String dietitionAvatar;
    /**
     * 是否匿名
     */
    private Boolean anonymous;
    /**
     * 是否可见，true:显示false:隐藏
     */
    private Boolean visible;
    /**
     * 评分星级1-5分
     */
    private Integer scores;
    /**
     * 描述星级 1-5 星
     */
    private Integer descriptionScores;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论图片地址数组
     */
    private String picUrls;
    /**
     * 营养师是否回复
     */
    private Boolean replyStatus;
    /**
     * 回复图片地址数组
     */
    private String replyUrls;
    /**
     * 营养师编号
     */
    private Long dietitionId;
    /**
     * 营养师回复内容
     */
    private String replyContent;
    /**
     * 营养师回复时间
     */
    private LocalDateTime replyTime;
    /**
     * 课程id
     */
    private Long classId;

}