package com.ainutribox.module.trade.api.push;

/**
 * WxPush
 *
 * @author lucifer
 * @date 2024-11-04 18:10
 */
public interface WxPushApi {
    public void wxSendGoodsPush(String openId, String content,String orderOn);
}
