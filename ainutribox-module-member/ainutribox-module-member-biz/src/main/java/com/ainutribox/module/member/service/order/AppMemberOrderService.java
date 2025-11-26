package com.ainutribox.module.member.service.order;

import com.ainutribox.module.member.controller.app.order.vo.AppVipClassOrderCreateReqVO;
import com.ainutribox.module.member.controller.app.order.vo.AppVipClassOrderCreateRespVO;
import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;

/**
 * AppOrderService
 *
 * @author lucifer
 * @date 2024-06-27 19:38
 */
public interface AppMemberOrderService {

    /**
     * 创建订单
     * @param userId
     * @param vipClassOrderCreateReqVO
     * @return
     */
    public MemberOrderDO createOrder(Long userId, AppVipClassOrderCreateReqVO vipClassOrderCreateReqVO);


    /**
     * 更新交易订单已支付
     *
     * @param id         交易订单编号
     * @param payOrderId 支付订单编号
     */
    void updateOrderPaid(Long id, Long payOrderId);


    /**
     * 【会员】取消交易订单
     *
     * @param userId 用户编号
     * @param id     订单编号
     */
    void cancelOrderByMember(Long userId, Long id);


    /**
     * 【系统】自动取消订单
     *
     * @return 取消数量
     */
    int cancelOrderBySystem();

}
