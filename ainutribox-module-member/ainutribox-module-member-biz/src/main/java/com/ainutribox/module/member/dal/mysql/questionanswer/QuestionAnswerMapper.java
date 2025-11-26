package com.ainutribox.module.member.dal.mysql.questionanswer;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.questionanswer.QuestionAnswerDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.questionanswer.vo.*;

/**
 * 用户题库答案 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface QuestionAnswerMapper extends BaseMapperX<QuestionAnswerDO> {

    default PageResult<QuestionAnswerDO> selectPage(QuestionAnswerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QuestionAnswerDO>()
                .eqIfPresent(QuestionAnswerDO::getPicUrl, reqVO.getPicUrl())
                .eqIfPresent(QuestionAnswerDO::getAnswer, reqVO.getAnswer())
                .eqIfPresent(QuestionAnswerDO::getSort, reqVO.getSort())
                .eqIfPresent(QuestionAnswerDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(QuestionAnswerDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(QuestionAnswerDO::getQuestionId, reqVO.getQuestionId())
                .eqIfPresent(QuestionAnswerDO::getOptionType, reqVO.getOptionType())
                .eqIfPresent(QuestionAnswerDO::getOptionId, reqVO.getOptionId())
                .orderByDesc(QuestionAnswerDO::getId));
    }

}