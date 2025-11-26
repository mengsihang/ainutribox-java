package com.ainutribox.module.member.dal.dataobject.questionanswer;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户题库答案 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_question_answer")
@KeySequence("member_question_answer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 图片
     */
    private String picUrl;
    /**
     * 答案
     */
    private String answer;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 题目编号
     */
    private Long questionId;
    /**
     * 操作类型
     *
     * 枚举 {@link TODO member_question_answer_type 对应的类}
     */
    private Integer optionType;
    /**
     * 操作对应题目id
     */
    private String optionId;

}