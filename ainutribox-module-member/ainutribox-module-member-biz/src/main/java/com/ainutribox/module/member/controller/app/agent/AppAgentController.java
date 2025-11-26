package com.ainutribox.module.member.controller.app.agent;


import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.module.member.controller.admin.agent.vo.AgentSaveReqVO;
import com.ainutribox.module.member.dal.dataobject.agent.AgentDO;
import com.ainutribox.module.member.dal.dataobject.user.MemberUserDO;
import com.ainutribox.module.member.enums.ErrorCodeConstants;
import com.ainutribox.module.member.service.agent.AgentService;
import com.ainutribox.module.member.service.user.MemberUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "用户 APP - 代理商申请")
@RestController
@RequestMapping("/member/agent")
@Validated
@Slf4j
public class AppAgentController {

    @Resource
    private AgentService agentService;
    @Resource
    private MemberUserService memberUserService;

    @PostMapping("/create")
    @Operation(summary = "申请成为代理商")
    @PreAuthenticated
    public CommonResult<Long> createAgent(@Valid @RequestBody AgentSaveReqVO createReqVO) {
        //防止篡改状态
        createReqVO.setStatus(0);
        createReqVO.setUserId(getLoginUserId());
        MemberUserDO memberUserDO = memberUserService.getUser(createReqVO.getUserId());
        if (memberUserDO == null) {
            return CommonResult.error(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        AgentDO agentDO = agentService.getAgentByUserId(createReqVO.getUserId());
        if (agentDO != null) {
            if(agentDO.getStatus() == 1){
                return CommonResult.error(ErrorCodeConstants.AGENT_STATUS_ONE);
            }
            agentDO.setStatus(0);
            agentService.updateAgent(BeanUtils.toBean(agentDO,AgentSaveReqVO.class));
            return success(1L);
        }else{
            return success(agentService.createAgent(createReqVO));
        }

    }

}
