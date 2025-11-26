package com.ainutribox.module.agent.controller.app;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.module.agent.service.WxAuthService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/wx")
@RequiredArgsConstructor
public class AppWxLoginController {

    private final WxAuthService wxAuthService;

    @PostMapping("/login")
    @PermitAll
    public CommonResult<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String code = request.get("code");
        if (code == null || code.trim().isEmpty()) {
            return CommonResult.error(400, "缺少 code 参数");
        }

        String token = wxAuthService.login(code);

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        return CommonResult.success(data);
    }
}