package com.ainutribox.module.agent.dto;

import lombok.Data;

@Data
public class AskResponse {
    private String question;
    private String answer;
    private String source;
    private String sessionId; // 👈 新增：用于多轮对话
}