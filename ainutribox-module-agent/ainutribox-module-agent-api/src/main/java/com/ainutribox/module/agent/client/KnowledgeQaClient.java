package com.ainutribox.module.agent.client;

import com.ainutribox.module.agent.dto.AskRequest;
import com.ainutribox.module.agent.dto.AskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "knowledge-qa-service", url = "${external.knowledge.url}")
public interface KnowledgeQaClient {
    @PostMapping("/ask")
    AskResponse ask(@RequestBody AskRequest request);
}