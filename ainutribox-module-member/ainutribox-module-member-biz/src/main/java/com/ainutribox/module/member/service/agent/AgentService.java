package com.ainutribox.module.member.service.agent;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.agent.vo.*;
import com.ainutribox.module.member.dal.dataobject.agent.AgentDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 代理商记录 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface AgentService {

    /**
     * 创建代理商记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAgent(@Valid AgentSaveReqVO createReqVO);

    /**
     * 更新代理商记录
     *
     * @param updateReqVO 更新信息
     */
    void updateAgent(@Valid AgentSaveReqVO updateReqVO);

    /**
     * 删除代理商记录
     *
     * @param id 编号
     */
    void deleteAgent(Long id);

    /**
     * 获得代理商记录
     *
     * @param id 编号
     * @return 代理商记录
     */
    AgentDO getAgent(Long id);


    /**
     * 获得代理商记录
     *
     * @param userId 用户编号
     * @return 代理商记录
     */
    AgentDO getAgentByUserId(Long userId);

    /**
     * 获得代理商记录分页
     *
     * @param pageReqVO 分页查询
     * @return 代理商记录分页
     */
    PageResult<AgentDO> getAgentPage(AgentPageReqVO pageReqVO);

}