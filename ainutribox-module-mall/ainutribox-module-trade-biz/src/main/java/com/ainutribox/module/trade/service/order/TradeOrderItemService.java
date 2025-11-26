package com.ainutribox.module.trade.service.order;

import java.util.*;

import com.ainutribox.module.trade.controller.admin.order.vo.TradeOrderItemBaseVO;
import com.ainutribox.module.trade.dal.dataobject.order.TradeOrderItemDO;
import jakarta.validation.*;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 交易订单明细 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface TradeOrderItemService {

    /**
     * 创建交易订单明细
     *
     * @param tradeOrderItemDO 创建信息
     * @return 编号
     */
        Long createOrderItem(@Valid TradeOrderItemDO tradeOrderItemDO);

    /**
     * 更新交易订单明细
     *
     * @param tradeOrderItemDO 更新信息
     */
    void updateOrderItem(@Valid TradeOrderItemDO tradeOrderItemDO);

    /**
     * 删除交易订单明细
     *
     * @param id 编号
     */
    void deleteOrderItem(Long id);

    /**
     * 获得交易订单明细
     *
     * @param id 编号
     * @return 交易订单明细
     */
    TradeOrderItemDO getOrderItem(Long id);



}