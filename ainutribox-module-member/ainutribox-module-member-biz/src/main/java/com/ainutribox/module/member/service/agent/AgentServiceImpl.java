package com.ainutribox.module.member.service.agent;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.agent.vo.*;
import com.ainutribox.module.member.dal.dataobject.agent.AgentDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.agent.AgentMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 代理商记录 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class AgentServiceImpl implements AgentService {

    @Resource
    private AgentMapper agentMapper;

    @Override
    public Long createAgent(AgentSaveReqVO createReqVO) {
        // 插入
        AgentDO agent = BeanUtils.toBean(createReqVO, AgentDO.class);
        agentMapper.insert(agent);
        // 返回
        return agent.getId();
    }

    @Override
    public void updateAgent(AgentSaveReqVO updateReqVO) {
        // 校验存在
        validateAgentExists(updateReqVO.getId());
        // 更新
        AgentDO updateObj = BeanUtils.toBean(updateReqVO, AgentDO.class);
        agentMapper.updateById(updateObj);
    }

    @Override
    public void deleteAgent(Long id) {
        // 校验存在
        validateAgentExists(id);
        // 删除
        agentMapper.deleteById(id);
    }

    private void validateAgentExists(Long id) {
        if (agentMapper.selectById(id) == null) {
            throw exception(AGENT_NOT_EXISTS);
        }
    }

    @Override
    public AgentDO getAgent(Long id) {
        return agentMapper.selectById(id);
    }

    @Override
    public AgentDO getAgentByUserId(Long userId) {
        return agentMapper.selectOne("user_id", userId);
    }

    @Override
    public PageResult<AgentDO> getAgentPage(AgentPageReqVO pageReqVO) {
        return agentMapper.selectPage(pageReqVO);
    }

}