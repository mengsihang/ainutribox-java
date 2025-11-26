package com.ainutribox.module.member.service.question;

import com.ainutribox.module.member.controller.app.question.vo.AppQuestionRespVo;
import com.ainutribox.module.member.dal.dataobject.question.QuestionDO;

import java.util.List;

/**
 * App 用户题库 Service 接口
 *
 * @author lucifer
 */
public interface AppQuestionService {

    /**
     * 获得题库
     *
     * @return 用户题库
     */
    List<AppQuestionRespVo> getQuestion();
}
