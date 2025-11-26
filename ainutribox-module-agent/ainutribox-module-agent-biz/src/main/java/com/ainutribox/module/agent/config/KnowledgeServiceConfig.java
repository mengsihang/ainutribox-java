package com.ainutribox.module.agent.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "external.knowledge")
public class KnowledgeServiceConfig {
    private String url = "http://localhost:5002"; // 默认本地地址
}