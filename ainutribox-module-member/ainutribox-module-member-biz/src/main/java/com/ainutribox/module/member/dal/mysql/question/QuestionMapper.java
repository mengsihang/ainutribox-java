package com.ainutribox.module.member.dal.mysql.question;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.question.QuestionDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.question.vo.*;

/**
 * 用户题库 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface QuestionMapper extends BaseMapperX<QuestionDO> {

    default PageResult<QuestionDO> selectPage(QuestionPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<QuestionDO>()
                .eqIfPresent(QuestionDO::getTypeNum, reqVO.getTypeNum())
                .eqIfPresent(QuestionDO::getTitle, reqVO.getTitle())
                .eqIfPresent(QuestionDO::getPicUrl, reqVO.getPicUrl())
                .eqIfPresent(QuestionDO::getAnswer, reqVO.getAnswer())
                .eqIfPresent(QuestionDO::getSort, reqVO.getSort())
                .eqIfPresent(QuestionDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(QuestionDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(QuestionDO::getId));
    }

}