package com.ainutribox.module.member.dal.dataobject.question;

import com.ainutribox.module.member.controller.admin.questionanswer.vo.QuestionAnswerRespVO;
import com.ainutribox.module.member.dal.dataobject.questionanswer.QuestionAnswerDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;

/**
 * 用户题库 DO
 *
 * @author 小泉山网络科技
 */
@TableName("member_question")
@KeySequence("member_question_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 问题类型：0 基础问题 1 专项问题
     */
    private Integer typeNum;
    /**
     * 标题
     */
    private String title;
    /**
     * 图片
     */
    private String picUrl;
    /**
     * 答案  （所有答案，字符串拼接）
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
     * 多选答案数量 默认1
     */
    private Integer limitNum;

}