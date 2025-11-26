package com.ainutribox.module.member.service.order.handler;

import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;

/**
 * MemberOrderHandler
 *
 * @author lucifer
 * @date 2024-06-28 14:16
 */
public interface MemberOrderHandler {


    /**
     * 支付订单后
     * @param order 订单
     */
    default void afterPayOrder(MemberOrderDO order) {};
}
