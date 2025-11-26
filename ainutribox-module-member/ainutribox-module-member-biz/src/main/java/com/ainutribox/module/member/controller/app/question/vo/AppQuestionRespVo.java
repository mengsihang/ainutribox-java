package com.ainutribox.module.member.controller.app.question.vo;

import com.ainutribox.framework.mybatis.core.dataobject.BaseDO;
import com.ainutribox.module.member.controller.admin.question.vo.QuestionRespVO;
import com.ainutribox.module.member.dal.dataobject.question.QuestionDO;
import com.ainutribox.module.member.dal.dataobject.questionanswer.QuestionAnswerDO;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;


@Schema(description = "APP - 用户题库 Response VO")
@Data
@ExcelIgnoreUnannotated
public class AppQuestionRespVo extends BaseDO {

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

    private List<QuestionAnswerDO> answerList;

}
