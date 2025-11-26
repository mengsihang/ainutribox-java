package com.ainutribox.module.member.service.order;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import com.ainutribox.module.member.controller.admin.order.vo.*;
import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.util.object.BeanUtils;

import com.ainutribox.module.member.dal.mysql.order.MemberOrderMapper;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;

/**
 * 用户购买课程和VIP订单 Service 实现类
 *
 * @author 小泉山网络科技
 */
@Service
@Validated
public class MemberOrderServiceImpl implements MemberOrderService {

    @Resource
    private MemberOrderMapper orderMapper;

    @Override
    public Long createOrder(MemberOrderSaveReqVO createReqVO) {
        // 插入
        MemberOrderDO order = BeanUtils.toBean(createReqVO, MemberOrderDO.class);
        orderMapper.insert(order);
        // 返回
        return order.getId();
    }

    @Override
    public void updateOrder(MemberOrderSaveReqVO updateReqVO) {
        // 校验存在
        validateOrderExists(updateReqVO.getId());
        // 更新
        MemberOrderDO updateObj = BeanUtils.toBean(updateReqVO, MemberOrderDO.class);
        orderMapper.updateById(updateObj);
    }

    @Override
    public void deleteOrder(Long id) {
        // 校验存在
        validateOrderExists(id);
        // 删除
        orderMapper.deleteById(id);
    }

    private void validateOrderExists(Long id) {
        if (orderMapper.selectById(id) == null) {
            throw exception(ORDER_NOT_EXISTS);
        }
    }

    @Override
    public MemberOrderDO getOrder(Long id) {
        return orderMapper.selectById(id);
    }

    @Override
    public PageResult<MemberOrderDO> getOrderPage(MemberOrderPageReqVO pageReqVO) {
        return orderMapper.selectPage(pageReqVO);
    }

}