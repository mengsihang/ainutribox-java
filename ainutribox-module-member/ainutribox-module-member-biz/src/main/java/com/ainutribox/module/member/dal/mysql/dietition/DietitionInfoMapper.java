package com.ainutribox.module.member.dal.mysql.dietition;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.dietition.DietitionInfoDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.dietition.vo.*;

/**
 * 营养师信息 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface DietitionInfoMapper extends BaseMapperX<DietitionInfoDO> {

    default PageResult<DietitionInfoDO> selectPage(DietitionInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DietitionInfoDO>()
                .eqIfPresent(DietitionInfoDO::getUserId, reqVO.getUserId())
                .eqIfPresent(DietitionInfoDO::getContent, reqVO.getContent())
                .betweenIfPresent(DietitionInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DietitionInfoDO::getScore, reqVO.getScore())
                .likeIfPresent(DietitionInfoDO::getNickName, reqVO.getNickName())
                .eqIfPresent(DietitionInfoDO::getExperience, reqVO.getExperience())
                .eqIfPresent(DietitionInfoDO::getFavorableRate, reqVO.getFavorableRate())
                .eqIfPresent(DietitionInfoDO::getAdviceNum, reqVO.getAdviceNum())
                .eqIfPresent(DietitionInfoDO::getCaseNum, reqVO.getCaseNum())
                .eqIfPresent(DietitionInfoDO::getScore,reqVO.getScore())
                .eqIfPresent(DietitionInfoDO::getLevel, reqVO.getLevel())
                .eqIfPresent(DietitionInfoDO::getStatus,reqVO.getStatus())
                .orderByDesc(DietitionInfoDO::getId));
    }

}