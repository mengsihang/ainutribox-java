package com.ainutribox.module.trade.service.aftersale;

import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.module.trade.controller.admin.aftersale.vo.AfterSaleDisagreeReqVO;
import com.ainutribox.module.trade.controller.admin.aftersale.vo.AfterSalePageReqVO;
import com.ainutribox.module.trade.controller.admin.aftersale.vo.AfterSaleRefuseReqVO;
import com.ainutribox.module.trade.controller.app.aftersale.vo.AppAfterSaleCreateAllReqVO;
import com.ainutribox.module.trade.controller.app.aftersale.vo.AppAfterSaleCreateReqVO;
import com.ainutribox.module.trade.controller.app.aftersale.vo.AppAfterSaleDeliveryReqVO;
import com.ainutribox.module.trade.dal.dataobject.aftersale.AfterSaleDO;

import java.util.List;

/**
 * 售后订单 Service 接口
 *
 * @author 河南小泉山科技
 */
public interface AfterSaleService {

    /**
     * 【管理员】获得售后订单分页
     *
     * @param pageReqVO 分页查询
     * @return 售后订单分页
     */
    PageResult<AfterSaleDO> getAfterSalePage(AfterSalePageReqVO pageReqVO);

    /**
     * 【会员】获得售后订单分页
     *
     * @param userId    用户编号
     * @param pageParam 分页参数
     * @return 售后订单分页
     */
    PageResult<AfterSaleDO> getAfterSalePage(Long userId, PageParam pageParam);

    /**
     * 【会员】获得售后单
     *
     * @param userId 用户编号
     * @param id     售后编号
     * @return 售后订单
     */
    AfterSaleDO getAfterSale(Long userId, Long id);

    /**
     * 【管理员】获得售后单
     *
     * @param id 售后编号
     * @return 售后订单
     */
    AfterSaleDO getAfterSale(Long id);

    /**
     * 【会员】创建售后订单
     *
     * @param userId      会员用户编号
     * @param createReqVO 创建 Request 信息
     * @return 售后编号
     */
    Long createAfterSale(Long userId, AppAfterSaleCreateReqVO createReqVO);

    /**
     * 【管理员】同意售后订单
     *
     * @param userId 管理员用户编号
     * @param id     售后编号
     */
    void agreeAfterSale(Long userId, Long id);

    /**
     * 【管理员】拒绝售后订单
     *
     * @param userId     管理员用户编号
     * @param auditReqVO 审批 Request 信息
     */
    void disagreeAfterSale(Long userId, AfterSaleDisagreeReqVO auditReqVO);

    /**
     * 【会员】退回货物
     *
     * @param userId        会员用户编号
     * @param deliveryReqVO 退货 Request 信息
     */
    void deliveryAfterSale(Long userId, AppAfterSaleDeliveryReqVO deliveryReqVO);

    /**
     * 【管理员】确认收货
     *
     * @param userId 管理员编号
     * @param id     售后编号
     */
    void receiveAfterSale(Long userId, Long id);

    /**
     * 【管理员】拒绝收货
     *
     * @param userId      管理员用户编号
     * @param refuseReqVO 拒绝收货 Request 信息
     */
    void refuseAfterSale(Long userId, AfterSaleRefuseReqVO refuseReqVO);

    /**
     * 【管理员】确认退款
     *
     * @param userId 管理员用户编号
     * @param userIp 管理员用户 IP
     * @param id     售后编号
     */
    void refundAfterSale(Long userId, String userIp, Long id);

    /**
     * 【会员】取消售后
     *
     * @param userId 会员用户编号
     * @param id     售后编号
     */
    void cancelAfterSale(Long userId, Long id);

    /**
     * 【会员】获得正在进行中的售后订单数量
     *
     * @param userId 用户编号
     * @return 数量
     */
    Long getApplyingAfterSaleCount(Long userId);


    /**
     * 【会员】创建售后订单（订单下全部商品）
     *
     * @param userId      会员用户编号
     * @param createAllReqVO 创建 Request 信息
     * @return 售后编号
     */
    List<Long> createAfterSaleAll(Long userId, AppAfterSaleCreateAllReqVO createAllReqVO);


    /**
     * 【会员】取消售后(订单下所有商品)
     *
     * @param userId 会员用户编号
     * @param orderId  订单编号
     */
     void  cancelAfterSaleAll(Long userId, Long orderId);

}
