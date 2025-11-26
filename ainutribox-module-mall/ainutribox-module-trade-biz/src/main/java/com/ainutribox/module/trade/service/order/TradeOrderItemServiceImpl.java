package com.ainutribox.module.trade.service.order;

import com.ainutribox.module.trade.controller.admin.order.vo.TradeOrderItemBaseVO;
import com.ainutribox.module.trade.dal.dataobject.order.TradeOrderItemDO;
import com.ainutribox.module.trade.dal.mysql.order.TradeOrderItemMapper;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;


import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.trade.enums.ErrorCodeConstants.*;

/**
 * 交易订单明细 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class TradeOrderItemServiceImpl implements TradeOrderItemService {

    @Resource
    private TradeOrderItemMapper orderItemMapper;

    @Override
    public Long createOrderItem(TradeOrderItemDO createReqVO) {
        // 插入
        TradeOrderItemDO orderItem = BeanUtils.toBean(createReqVO, TradeOrderItemDO.class);
        orderItemMapper.insert(orderItem);
        // 返回
        return orderItem.getId();
    }

    @Override
    public void updateOrderItem(TradeOrderItemDO updateReqVO) {
        // 校验存在
        validateOrderItemExists(updateReqVO.getId());
        // 更新
        TradeOrderItemDO updateObj = BeanUtils.toBean(updateReqVO, TradeOrderItemDO.class);
        orderItemMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrderItem(Long id) {
        // 校验存在
        validateOrderItemExists(id);
        // 删除
        orderItemMapper.deleteById(id);
    }

    private void validateOrderItemExists(Long id) {
        if (orderItemMapper.selectById(id) == null) {
            throw exception(ORDER_ITEM_NOT_EXISTS);
        }
    }

    @Override
    public TradeOrderItemDO getOrderItem(Long id) {
        return orderItemMapper.selectById(id);
    }


}