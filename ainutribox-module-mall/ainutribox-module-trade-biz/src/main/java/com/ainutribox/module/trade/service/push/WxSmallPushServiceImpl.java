package com.ainutribox.module.trade.service.push;

import cn.hutool.core.date.DateTime;
import com.ainutribox.module.member.util.RedisUtils;
import com.ainutribox.module.trade.api.push.WxPushApi;
import com.alibaba.fastjson.JSONObject;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.StandardCharsets;

import static com.ainutribox.module.trade.enums.TryLockConstants.ACCESS_TOKEN_KEY;

/**
 * wxSmallPushServiceImpl
 *
 * @author lucifer
 * @date 2024-11-04 18:12
 */
@Service
@Validated
@Slf4j
public class WxSmallPushServiceImpl implements WxPushApi {


    @Value("${wx.miniapp.appid}")
    private String appId;

    @Value("${wx.miniapp.secret}")
    private String secretId;

    @Value("${wx.miniapp.send-goods-temp-id}")
    private String sendTempId;

    @Value("${wx.miniapp.access-token-url}")
    private String accessTokenUrl;

    @Value("${wx.miniapp.send-msg-url}")
    private String sendMsgUrl;

    @Value("${wx.miniapp.miniprogram-state}")
    private String miniprogramState;

    @Value("${wx.miniapp.lang}")
    private String lang;

    @Value("${wx.miniapp.page}")
    private String page;

    @Resource
    RestTemplate restTemplate = new RestTemplate();

    @Resource
    private RedisUtils redisUtils;

    /**
     * 维护accessToken
     * @return
     */
    public String getAccessToken() {
        // 获取acctoken
        String access_token = "";

        Object token = redisUtils.get(ACCESS_TOKEN_KEY);
        if (null == token || redisUtils.getExpire(ACCESS_TOKEN_KEY) <= 0) {
            String resultMap = restTemplate.postForObject(
                    accessTokenUrl+"?grant_type=client_credential&appid=" + appId+ "&secret=" + secretId,
                    null, String.class);

            log.info("resultMap" + resultMap);
            JSONObject jsonData = JSONObject.parseObject(resultMap);
            if (null != jsonData.getString("access_token")) {
                access_token = jsonData.getString("access_token");
                redisUtils.set(ACCESS_TOKEN_KEY, access_token, 7100);
            }
        } else {
            access_token = token.toString();
        }

        return access_token;
    }


    /**
     * 微信订阅消息(订单发货)
     *
     * @param openId       用户openId
     * @param content      提示内容
     */
    public void wxSendGoodsPush(String openId, String content,String orderOn) {

        String accessToken = getAccessToken();

        JSONObject datas = new JSONObject();
        datas.put("access_token",accessToken);
        datas.put("template_id", sendTempId);
        datas.put("page", page);
        datas.put("touser", openId);
        datas.put("miniprogram_state", miniprogramState);
        datas.put("lang", lang);

        JSONObject data = new JSONObject();
        //订单号
        JSONObject data1 = new JSONObject();
        //内容
        JSONObject data2 = new JSONObject();
        //时间
        JSONObject data3 = new JSONObject();

        data1.put("value", orderOn);
        data2.put("value", content);
        data3.put("value", DateTime.now().toString("yyyy-MM-dd HH:mm"));

        data.put("character_string2", data1);
        data.put("thing5", data2);
        data.put("time6", data3);

        datas.put("data", data);

        String body = JSONObject.toJSONString(datas);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setContentLength(body.getBytes(StandardCharsets.UTF_8).length);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        String resultMaps = restTemplate.postForObject(
                sendMsgUrl + "?access_token=" + accessToken, httpEntity, String.class);
        log.info("发送模板消息微信返回__" + resultMaps);
        /*JSONObject jsonObject = JSONObject.parseObject(resultMaps);
        String errcode = jsonObject.get("errcode").toString();
        if (!"0".equals(errcode) || 0 != Integer.parseInt(errcode)) {
            log.error(jsonObject.get("errmsg").toString());
        }*/

    }

}
