package com.ainutribox.module.member.service.questionanswer;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.questionanswer.vo.*;
import com.ainutribox.module.member.dal.dataobject.questionanswer.QuestionAnswerDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 用户题库答案 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface QuestionAnswerService {

    /**
     * 创建用户题库答案
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createQuestionAnswer(@Valid QuestionAnswerSaveReqVO createReqVO);

    /**
     * 更新用户题库答案
     *
     * @param updateReqVO 更新信息
     */
    void updateQuestionAnswer(@Valid QuestionAnswerSaveReqVO updateReqVO);

    /**
     * 删除用户题库答案
     *
     * @param id 编号
     */
    void deleteQuestionAnswer(Long id);

    /**
     * 获得用户题库答案
     *
     * @param id 编号
     * @return 用户题库答案
     */
    QuestionAnswerDO getQuestionAnswer(Long id);

    /**
     * 获得用户题库答案分页
     *
     * @param pageReqVO 分页查询
     * @return 用户题库答案分页
     */
    PageResult<QuestionAnswerDO> getQuestionAnswerPage(QuestionAnswerPageReqVO pageReqVO);

}