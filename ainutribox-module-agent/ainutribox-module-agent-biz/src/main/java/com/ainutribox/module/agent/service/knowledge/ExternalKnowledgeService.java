// ainutribox-module-agent-biz/src/main/java/.../service/knowledge/ExternalKnowledgeService.java

package com.ainutribox.module.agent.service.knowledge;

import com.ainutribox.framework.common.exception.ServiceException;
import com.ainutribox.module.agent.dto.AskRequest;
import com.ainutribox.module.agent.dto.AskResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class ExternalKnowledgeService {

    @Value("${agent.python.url:https://ainutribox-agent.onrender.com}")
    private String pythonServiceUrl;

    private final RestTemplate restTemplate;

    public ExternalKnowledgeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AskResponse ask(AskRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<AskRequest> entity = new HttpEntity<>(request, headers);
            String url = pythonServiceUrl + "/ask";

            ResponseEntity<AskResponse> response = restTemplate.postForEntity(url, entity, AskResponse.class);
            return response.getBody();
        } catch (Exception e) {
            log.error("调用 Python AI 服务失败", e);
            throw new ServiceException();
        }
    }
}