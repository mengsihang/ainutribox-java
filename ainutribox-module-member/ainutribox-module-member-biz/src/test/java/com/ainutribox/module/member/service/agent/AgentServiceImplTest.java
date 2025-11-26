package com.ainutribox.module.member.service.agent;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import jakarta.annotation.Resource;

import com.ainutribox.framework.test.core.ut.BaseDbUnitTest;

import com.ainutribox.module.member.controller.admin.agent.vo.*;
import com.ainutribox.module.member.dal.dataobject.agent.AgentDO;
import com.ainutribox.module.member.dal.mysql.agent.AgentMapper;
import com.ainutribox.framework.common.pojo.PageResult;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;
import static com.ainutribox.framework.test.core.util.AssertUtils.*;
import static com.ainutribox.framework.test.core.util.RandomUtils.*;
import static com.ainutribox.framework.common.util.date.LocalDateTimeUtils.*;
import static com.ainutribox.framework.common.util.object.ObjectUtils.*;
import static com.ainutribox.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link AgentServiceImpl} 的单元测试类
 *
 * @author 小泉山网络科技
 */
@Import(AgentServiceImpl.class)
public class AgentServiceImplTest extends BaseDbUnitTest {

    @Resource
    private AgentServiceImpl agentService;

    @Resource
    private AgentMapper agentMapper;

    @Test
    public void testCreateAgent_success() {
        // 准备参数
        AgentSaveReqVO createReqVO = randomPojo(AgentSaveReqVO.class).setId(null);

        // 调用
        Long agentId = agentService.createAgent(createReqVO);
        // 断言
        assertNotNull(agentId);
        // 校验记录的属性是否正确
        AgentDO agent = agentMapper.selectById(agentId);
        assertPojoEquals(createReqVO, agent, "id");
    }

    @Test
    public void testUpdateAgent_success() {
        // mock 数据
        AgentDO dbAgent = randomPojo(AgentDO.class);
        agentMapper.insert(dbAgent);// @Sql: 先插入出一条存在的数据
        // 准备参数
        AgentSaveReqVO updateReqVO = randomPojo(AgentSaveReqVO.class, o -> {
            o.setId(dbAgent.getId()); // 设置更新的 ID
        });

        // 调用
        agentService.updateAgent(updateReqVO);
        // 校验是否更新正确
        AgentDO agent = agentMapper.selectById(updateReqVO.getId()); // 获取最新的
        assertPojoEquals(updateReqVO, agent);
    }

    @Test
    public void testUpdateAgent_notExists() {
        // 准备参数
        AgentSaveReqVO updateReqVO = randomPojo(AgentSaveReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> agentService.updateAgent(updateReqVO), AGENT_NOT_EXISTS);
    }

    @Test
    public void testDeleteAgent_success() {
        // mock 数据
        AgentDO dbAgent = randomPojo(AgentDO.class);
        agentMapper.insert(dbAgent);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbAgent.getId();

        // 调用
        agentService.deleteAgent(id);
        // 校验数据不存在了
        assertNull(agentMapper.selectById(id));
    }

    @Test
    public void testDeleteAgent_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> agentService.deleteAgent(id), AGENT_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetAgentPage() {
        // mock 数据
        AgentDO dbAgent = randomPojo(AgentDO.class, o -> { // 等会查询到
            o.setUserId(null);
            o.setContent(null);
            o.setCreateTime(null);
            o.setStatus(null);
            o.setTel(null);
            o.setIdPicFront(null);
            o.setIdPicBack(null);
            o.setCertificatePic(null);
        });
        agentMapper.insert(dbAgent);
        // 测试 userId 不匹配
        agentMapper.insert(cloneIgnoreId(dbAgent, o -> o.setUserId(null)));
        // 测试 content 不匹配
        agentMapper.insert(cloneIgnoreId(dbAgent, o -> o.setContent(null)));
        // 测试 createTime 不匹配
        agentMapper.insert(cloneIgnoreId(dbAgent, o -> o.setCreateTime(null)));
        // 测试 status 不匹配
        agentMapper.insert(cloneIgnoreId(dbAgent, o -> o.setStatus(null)));
        // 测试 tel 不匹配
        agentMapper.insert(cloneIgnoreId(dbAgent, o -> o.setTel(null)));
        // 测试 idPicFront 不匹配
        agentMapper.insert(cloneIgnoreId(dbAgent, o -> o.setIdPicFront(null)));
        // 测试 idPicBack 不匹配
        agentMapper.insert(cloneIgnoreId(dbAgent, o -> o.setIdPicBack(null)));
        // 测试 certificatePic 不匹配
        agentMapper.insert(cloneIgnoreId(dbAgent, o -> o.setCertificatePic(null)));
        // 准备参数
        AgentPageReqVO reqVO = new AgentPageReqVO();
        reqVO.setUserId(null);
        reqVO.setContent(null);
        reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
        reqVO.setStatus(null);
        reqVO.setTel(null);
        reqVO.setIdPicFront(null);
        reqVO.setIdPicBack(null);
        reqVO.setCertificatePic(null);

        // 调用
        PageResult<AgentDO> pageResult = agentService.getAgentPage(reqVO);
        // 断言
        assertEquals(1, pageResult.getTotal());
        assertEquals(1, pageResult.getList().size());
        assertPojoEquals(dbAgent, pageResult.getList().get(0));
    }

}