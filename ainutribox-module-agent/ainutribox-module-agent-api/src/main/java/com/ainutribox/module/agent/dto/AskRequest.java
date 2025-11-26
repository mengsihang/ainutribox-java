package com.ainutribox.module.agent.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AskRequest {
    private String query;
    private String sessionId; // 👈 新增：用于多轮对话
    private List<String> urls = new ArrayList<>();
}