package com.ainutribox.module.trade.api.AppBrokerageUser;

import jakarta.validation.constraints.NotNull;

/**
 * 订单 API 接口
 *
 * @author wuhen
 */
public interface AppBrokerageUserApi {

    /**
     * 【会员】绑定推广员
     *
     * @param userId       用户编号
     * @param bindUserId   推广员编号
     * @return 是否绑定
     */
    boolean bindBrokerageUser(@NotNull Long userId, @NotNull Long bindUserId);

}
