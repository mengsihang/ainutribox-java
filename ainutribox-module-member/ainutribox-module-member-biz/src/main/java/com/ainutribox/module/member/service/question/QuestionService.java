package com.ainutribox.module.member.service.question;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.question.vo.*;
import com.ainutribox.module.member.dal.dataobject.question.QuestionDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 用户题库 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface QuestionService {

    /**
     * 创建用户题库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createQuestion(@Valid QuestionSaveReqVO createReqVO);

    /**
     * 更新用户题库
     *
     * @param updateReqVO 更新信息
     */
    void updateQuestion(@Valid QuestionSaveReqVO updateReqVO);

    /**
     * 删除用户题库
     *
     * @param id 编号
     */
    void deleteQuestion(Long id);

    /**
     * 获得用户题库
     *
     * @param id 编号
     * @return 用户题库
     */
    QuestionDO getQuestion(Long id);

    /**
     * 获得用户题库分页
     *
     * @param pageReqVO 分页查询
     * @return 用户题库分页
     */
    PageResult<QuestionDO> getQuestionPage(QuestionPageReqVO pageReqVO);

}