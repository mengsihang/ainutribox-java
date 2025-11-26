package com.ainutribox.module.member.service.order;

import java.util.*;
import jakarta.validation.*;
import com.ainutribox.module.member.controller.admin.order.vo.*;
import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.pojo.PageParam;

/**
 * 用户购买课程和VIP订单 Service 接口
 *
 * @author 小泉山网络科技
 */
public interface MemberOrderService {

    /**
     * 创建用户购买课程和VIP订单
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createOrder(@Valid MemberOrderSaveReqVO createReqVO);

    /**
     * 更新用户购买课程和VIP订单
     *
     * @param updateReqVO 更新信息
     */
    void updateOrder(@Valid MemberOrderSaveReqVO updateReqVO);

    /**
     * 删除用户购买课程和VIP订单
     *
     * @param id 编号
     */
    void deleteOrder(Long id);

    /**
     * 获得用户购买课程和VIP订单
     *
     * @param id 编号
     * @return 用户购买课程和VIP订单
     */
    MemberOrderDO getOrder(Long id);

    /**
     * 获得用户购买课程和VIP订单分页
     *
     * @param pageReqVO 分页查询
     * @return 用户购买课程和VIP订单分页
     */
    PageResult<MemberOrderDO> getOrderPage(MemberOrderPageReqVO pageReqVO);

}