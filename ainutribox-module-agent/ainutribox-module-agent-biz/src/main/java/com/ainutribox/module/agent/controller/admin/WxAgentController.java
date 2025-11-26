// ainutribox-module-agent-biz/src/main/java/.../controller/AgentController.java
package com.ainutribox.module.agent.controller.admin;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.module.agent.dto.AskRequest;
import com.ainutribox.module.agent.dto.AskResponse;
import com.ainutribox.module.agent.service.knowledge.ExternalKnowledgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "智能体 - 知识问答")
@RestController
@RequestMapping("/agent/knowledge")
@RequiredArgsConstructor
@PermitAll
public class WxAgentController {

    private final ExternalKnowledgeService externalKnowledgeService;

    @Operation(summary = "向智能体提问（知识库问答）")
    @PostMapping("/ask")
    public CommonResult<AskResponse> ask(@Valid @RequestBody AskRequest request) {
        AskResponse response = externalKnowledgeService.ask(request);
        return CommonResult.success(response);
    }
}