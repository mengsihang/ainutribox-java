package com.ainutribox.module.member.dal.mysql.agent;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.mapper.BaseMapperX;
import com.ainutribox.module.member.dal.dataobject.agent.AgentDO;
import org.apache.ibatis.annotations.Mapper;
import com.ainutribox.module.member.controller.admin.agent.vo.*;

/**
 * 代理商记录 Mapper
 *
 * @author 小泉山网络科技
 */
@Mapper
public interface AgentMapper extends BaseMapperX<AgentDO> {

    default PageResult<AgentDO> selectPage(AgentPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AgentDO>()
                .eqIfPresent(AgentDO::getUserId, reqVO.getUserId())
                .eqIfPresent(AgentDO::getContent, reqVO.getContent())
                .betweenIfPresent(AgentDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(AgentDO::getStatus, reqVO.getStatus())
                .eqIfPresent(AgentDO::getTel, reqVO.getTel())
                .eqIfPresent(AgentDO::getIdPicFront, reqVO.getIdPicFront())
                .eqIfPresent(AgentDO::getIdPicBack, reqVO.getIdPicBack())
                .eqIfPresent(AgentDO::getCertificatePic, reqVO.getCertificatePic())
                .orderByDesc(AgentDO::getId));
    }

}