package com.ainutribox.module.agent.controller.admin;

import com.ainutribox.module.agent.dto.AskRequest;
import com.ainutribox.module.agent.dto.AskResponse;
import com.ainutribox.module.agent.service.knowledge.ExternalKnowledgeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="Agent - Test")
@RestController
@RequestMapping("/admin/agent")
public class KnowledgeAgentController { // 👈 改名！

    private final ExternalKnowledgeService knowledgeService;

    public KnowledgeAgentController(ExternalKnowledgeService knowledgeService) {
        this.knowledgeService = knowledgeService;
    }

    @PostMapping("/ask")
    public AskResponse ask(@RequestBody AskRequest request) {
        return knowledgeService.ask(request);
    }
}