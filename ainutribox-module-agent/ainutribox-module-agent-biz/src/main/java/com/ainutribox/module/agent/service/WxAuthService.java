package com.ainutribox.module.agent.service;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ainutribox.framework.common.exception.ServiceException;
import com.ainutribox.framework.common.exception.enums.GlobalErrorCodeConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class WxAuthService {

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.secret}")
    private String secret;

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 微信小程序登录：用 code 换取 openid，并生成 token 存入 Redis
     *
     * @param code 小程序 wx.login() 获取的临时登录凭证
     * @return 生成的 token（前端后续用于鉴权）
     */
    public String login(String code) {
        // 1. 调用微信接口
        String url = String.format(
                "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                appId, secret, code
        );

        String response = HttpUtil.get(url);
        log.info("微信 jscode2session 响应: {}", response);

        JSONObject json = JSONUtil.parseObj(response);
        if (json.getStr("errcode") != null && !"0".equals(json.getStr("errcode"))) {
            String errMsg = json.getStr("errmsg", "未知错误");
            log.error("微信登录失败: {} - {}", json.getStr("errcode"), errMsg);
            throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "微信登录失败: " + errMsg);
        }

        String openid = json.getStr("openid");
        if (openid == null || openid.isEmpty()) {
            throw new ServiceException(GlobalErrorCodeConstants.UNAUTHORIZED.getCode(), "未获取到 openid");
        }

        // 2. 生成唯一 token（建议用 UUID）
        String token = "wx_" + java.util.UUID.randomUUID().toString().replace("-", "");

        // 3. 存入 Redis，有效期 7 天（单位：秒）
        stringRedisTemplate.opsForValue().set(
                "wx_token:" + token,
                openid,
                7 * 24 * 60 * 60,
                TimeUnit.SECONDS
        );

        log.info("微信登录成功，openid: {}, token: {}", openid, token);
        return token;
    }
}