package com.ainutribox.module.trade.service.aftersale;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ainutribox.framework.common.exception.ErrorCode;
import com.ainutribox.framework.common.pojo.PageParam;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.ObjectUtils;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.framework.mybatis.core.query.QueryWrapperX;
import com.ainutribox.module.pay.api.refund.PayRefundApi;
import com.ainutribox.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import com.ainutribox.module.trade.controller.admin.aftersale.vo.AfterSaleDisagreeReqVO;
import com.ainutribox.module.trade.controller.admin.aftersale.vo.AfterSalePageReqVO;
import com.ainutribox.module.trade.controller.admin.aftersale.vo.AfterSaleRefuseReqVO;
import com.ainutribox.module.trade.controller.app.aftersale.vo.AppAfterSaleCreateAllReqVO;
import com.ainutribox.module.trade.controller.app.aftersale.vo.AppAfterSaleCreateReqVO;
import com.ainutribox.module.trade.controller.app.aftersale.vo.AppAfterSaleDeliveryReqVO;
import com.ainutribox.module.trade.convert.aftersale.AfterSaleConvert;
import com.ainutribox.module.trade.dal.dataobject.aftersale.AfterSaleDO;
import com.ainutribox.module.trade.dal.dataobject.delivery.DeliveryExpressDO;
import com.ainutribox.module.trade.dal.dataobject.dietitianorderlog.DietitianOrderLogDO;
import com.ainutribox.module.trade.dal.dataobject.order.TradeOrderDO;
import com.ainutribox.module.trade.dal.dataobject.order.TradeOrderItemDO;
import com.ainutribox.module.trade.dal.mysql.aftersale.AfterSaleMapper;
import com.ainutribox.module.trade.dal.mysql.dietitianorderlog.DietitianOrderLogMapper;
import com.ainutribox.module.trade.dal.mysql.order.TradeOrderItemMapper;
import com.ainutribox.module.trade.dal.mysql.order.TradeOrderMapper;
import com.ainutribox.module.trade.dal.redis.no.TradeNoRedisDAO;
import com.ainutribox.module.trade.enums.aftersale.AfterSaleOperateTypeEnum;
import com.ainutribox.module.trade.enums.aftersale.AfterSaleStatusEnum;
import com.ainutribox.module.trade.enums.aftersale.AfterSaleTypeEnum;
import com.ainutribox.module.trade.enums.aftersale.AfterSaleWayEnum;
import com.ainutribox.module.trade.enums.dietitian.TradeDietitianEnum;
import com.ainutribox.module.trade.enums.order.TradeOrderItemAfterSaleStatusEnum;
import com.ainutribox.module.trade.enums.order.TradeOrderStatusEnum;
import com.ainutribox.module.trade.framework.aftersale.core.annotations.AfterSaleLog;
import com.ainutribox.module.trade.framework.aftersale.core.utils.AfterSaleLogUtils;
import com.ainutribox.module.trade.framework.order.config.TradeOrderProperties;
import com.ainutribox.module.trade.service.delivery.DeliveryExpressService;
import com.ainutribox.module.trade.service.order.TradeOrderQueryService;
import com.ainutribox.module.trade.service.order.TradeOrderUpdateService;
import com.ainutribox.module.trade.service.order.bo.TradeDietitianLogBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.annotation.Validated;

import jakarta.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.module.trade.enums.ErrorCodeConstants.*;

/**
 * 售后订单 Service 实现类
 *
 * @author 河南小泉山科技
 */
@Slf4j
@Service
@Validated
public class AfterSaleServiceImpl implements AfterSaleService {

    @Resource
    private TradeOrderUpdateService tradeOrderUpdateService;
    @Resource
    private TradeOrderQueryService tradeOrderQueryService;
    @Resource
    private DeliveryExpressService deliveryExpressService;

    @Resource
    private AfterSaleMapper tradeAfterSaleMapper;
    @Resource
    private TradeNoRedisDAO tradeNoRedisDAO;

    @Resource
    private PayRefundApi payRefundApi;

    @Resource
    private TradeOrderProperties tradeOrderProperties;

    @Resource
    private TradeOrderMapper tradeOrderMapper;

    @Resource
    private TradeOrderItemMapper orderItemMapper;

    @Resource
    private DietitianOrderLogMapper dietitianOrderLogMapper;


    @Override
    public PageResult<AfterSaleDO> getAfterSalePage(AfterSalePageReqVO pageReqVO) {
        return tradeAfterSaleMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<AfterSaleDO> getAfterSalePage(Long userId, PageParam pageParam) {
        return tradeAfterSaleMapper.selectPage(userId, pageParam);
    }

    @Override
    public AfterSaleDO getAfterSale(Long userId, Long id) {
        return tradeAfterSaleMapper.selectByIdAndUserId(id, userId);
    }

    @Override
    public AfterSaleDO getAfterSale(Long id) {
        return tradeAfterSaleMapper.selectById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.MEMBER_CREATE)
    public Long createAfterSale(Long userId, AppAfterSaleCreateReqVO createReqVO) {
        // 第一步，前置校验
        TradeOrderItemDO tradeOrderItem = validateOrderItemApplicable(userId, createReqVO);

        // 第二步，存储售后订单
        AfterSaleDO afterSale = createAfterSale(createReqVO, tradeOrderItem);
        return afterSale.getId();
    }

    /**
     * 校验交易订单项是否可以申请售后
     *
     * @param userId      用户编号
     * @param createReqVO 售后创建信息
     * @return 交易订单项
     */
    private TradeOrderItemDO validateOrderItemApplicable(Long userId, AppAfterSaleCreateReqVO createReqVO) {
        // 校验订单项存在
        TradeOrderItemDO orderItem = tradeOrderQueryService.getOrderItem(userId, createReqVO.getOrderItemId());
        if (orderItem == null) {
            throw exception(ORDER_ITEM_NOT_FOUND);
        }
        // 已申请售后，不允许再发起售后申请
        if (!TradeOrderItemAfterSaleStatusEnum.isNone(orderItem.getAfterSaleStatus())) {
            throw exception(AFTER_SALE_CREATE_FAIL_ORDER_ITEM_APPLIED);
        }
        // 申请的退款金额，不能超过商品的价格
        if (createReqVO.getRefundPrice() > orderItem.getPayPrice()) {
            throw exception(AFTER_SALE_CREATE_FAIL_REFUND_PRICE_ERROR);
        }

        // 校验订单存在
        TradeOrderDO order = tradeOrderQueryService.getOrder(userId, orderItem.getOrderId());
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        // TODO 芋艿：超过一定时间，不允许售后
        // 已取消，无法发起售后
        if (TradeOrderStatusEnum.isCanceled(order.getStatus())) {
            throw exception(AFTER_SALE_CREATE_FAIL_ORDER_STATUS_CANCELED);
        }
        // 未支付，无法发起售后
        if (!TradeOrderStatusEnum.havePaid(order.getStatus())) {
            throw exception(AFTER_SALE_CREATE_FAIL_ORDER_STATUS_NO_PAID);
        }
        // 如果是【退货退款】的情况，需要额外校验是否发货
        if (createReqVO.getWay().equals(AfterSaleWayEnum.RETURN_AND_REFUND.getWay())
                && !TradeOrderStatusEnum.haveDelivered(order.getStatus())) {
            throw exception(AFTER_SALE_CREATE_FAIL_ORDER_STATUS_NO_DELIVERED);
        }
        return orderItem;
    }

    private AfterSaleDO createAfterSale(AppAfterSaleCreateReqVO createReqVO,
                                        TradeOrderItemDO orderItem) {
        // 创建售后单
        AfterSaleDO afterSale = AfterSaleConvert.INSTANCE.convert(createReqVO, orderItem);
        afterSale.setNo(tradeNoRedisDAO.generate(TradeNoRedisDAO.AFTER_SALE_NO_PREFIX));
        afterSale.setStatus(AfterSaleStatusEnum.APPLY.getStatus());
        // 标记是售中还是售后
        TradeOrderDO order = tradeOrderQueryService.getOrder(orderItem.getUserId(), orderItem.getOrderId());
        afterSale.setOrderNo(order.getNo()); // 记录 orderNo 订单流水，方便后续检索
        afterSale.setType(TradeOrderStatusEnum.isCompleted(order.getStatus())
                ? AfterSaleTypeEnum.AFTER_SALE.getType() : AfterSaleTypeEnum.IN_SALE.getType());
        tradeAfterSaleMapper.insert(afterSale);

        // 更新交易订单项的售后状态
        tradeOrderUpdateService.updateOrderItemWhenAfterSaleCreate(orderItem.getId(), afterSale.getId());

        // 记录售后日志
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), null,
                AfterSaleStatusEnum.APPLY.getStatus());

        // TODO 发送售后消息
        return afterSale;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.ADMIN_AGREE_APPLY)
    public void agreeAfterSale(Long userId, Long id) {
        // 校验售后单存在，并状态未审批
        AfterSaleDO afterSale = validateAfterSaleAuditable(id);

        // 更新售后单的状态
        // 情况一：退款：标记为 WAIT_REFUND 状态。后续等退款发起成功后，在标记为 COMPLETE 状态
        // 情况二：退货退款：需要等用户退货后，才能发起退款
        Integer newStatus = afterSale.getWay().equals(AfterSaleWayEnum.REFUND.getWay()) ?
                AfterSaleStatusEnum.WAIT_REFUND.getStatus() : AfterSaleStatusEnum.SELLER_AGREE.getStatus();
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.APPLY.getStatus(), new AfterSaleDO()
                .setStatus(newStatus).setAuditUserId(userId).setAuditTime(LocalDateTime.now()));

        // 记录售后日志
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(), newStatus);

        // TODO 发送售后消息
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.ADMIN_DISAGREE_APPLY)
    public void disagreeAfterSale(Long userId, AfterSaleDisagreeReqVO auditReqVO) {
        // 校验售后单存在，并状态未审批
        AfterSaleDO afterSale = validateAfterSaleAuditable(auditReqVO.getId());

        // 更新售后单的状态
        Integer newStatus = AfterSaleStatusEnum.SELLER_DISAGREE.getStatus();
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.APPLY.getStatus(), new AfterSaleDO()
                .setStatus(newStatus).setAuditUserId(userId).setAuditTime(LocalDateTime.now())
                .setAuditReason(auditReqVO.getAuditReason()));

        // 记录售后日志
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(), newStatus);

        // TODO 发送售后消息

        // 更新交易订单项的售后状态为【未申请】
        tradeOrderUpdateService.updateOrderItemWhenAfterSaleCancel(afterSale.getOrderItemId());
    }

    /**
     * 校验售后单是否可审批（同意售后、拒绝售后）
     *
     * @param id 售后编号
     * @return 售后单
     */
    private AfterSaleDO validateAfterSaleAuditable(Long id) {
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(afterSale.getStatus(), AfterSaleStatusEnum.APPLY.getStatus())) {
            throw exception(AFTER_SALE_AUDIT_FAIL_STATUS_NOT_APPLY);
        }
        return afterSale;
    }

    private void updateAfterSaleStatus(Long id, Integer status, AfterSaleDO updateObj) {
        int updateCount = tradeAfterSaleMapper.updateByIdAndStatus(id, status, updateObj);
        if (updateCount == 0) {
            throw exception(AFTER_SALE_UPDATE_STATUS_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.MEMBER_DELIVERY)
    public void deliveryAfterSale(Long userId, AppAfterSaleDeliveryReqVO deliveryReqVO) {
        // 校验售后单存在，并状态未退货
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectByIdAndUserId(deliveryReqVO.getId(), userId);
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(afterSale.getStatus(), AfterSaleStatusEnum.SELLER_AGREE.getStatus())) {
            throw exception(AFTER_SALE_DELIVERY_FAIL_STATUS_NOT_SELLER_AGREE);
        }
        DeliveryExpressDO express = deliveryExpressService.validateDeliveryExpress(deliveryReqVO.getLogisticsId());

        // 更新售后单的物流信息
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.SELLER_AGREE.getStatus(), new AfterSaleDO()
                .setStatus(AfterSaleStatusEnum.BUYER_DELIVERY.getStatus())
                .setLogisticsId(deliveryReqVO.getLogisticsId()).setLogisticsNo(deliveryReqVO.getLogisticsNo())
                .setDeliveryTime(LocalDateTime.now()));

        // 记录售后日志
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(),
                AfterSaleStatusEnum.BUYER_DELIVERY.getStatus(),
                MapUtil.<String, Object>builder().put("deliveryName", express.getName())
                        .put("logisticsNo", deliveryReqVO.getLogisticsNo()).build());

        // TODO 发送售后消息
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.ADMIN_AGREE_RECEIVE)
    public void receiveAfterSale(Long userId, Long id) {
        // 校验售后单存在，并状态为已退货
        AfterSaleDO afterSale = validateAfterSaleReceivable(id);

        // 更新售后单的状态
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.BUYER_DELIVERY.getStatus(), new AfterSaleDO()
                .setStatus(AfterSaleStatusEnum.WAIT_REFUND.getStatus()).setReceiveTime(LocalDateTime.now()));

        // 记录售后日志
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(),
                AfterSaleStatusEnum.WAIT_REFUND.getStatus());

        // TODO 发送售后消息
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.ADMIN_DISAGREE_RECEIVE)
    public void refuseAfterSale(Long userId, AfterSaleRefuseReqVO refuseReqVO) {
        // 校验售后单存在，并状态为已退货
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(refuseReqVO.getId());
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(afterSale.getStatus(), AfterSaleStatusEnum.BUYER_DELIVERY.getStatus())) {
            throw exception(AFTER_SALE_CONFIRM_FAIL_STATUS_NOT_BUYER_DELIVERY);
        }

        // 更新售后单的状态
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.BUYER_DELIVERY.getStatus(), new AfterSaleDO()
                .setStatus(AfterSaleStatusEnum.SELLER_REFUSE.getStatus()).setReceiveTime(LocalDateTime.now())
                .setReceiveReason(refuseReqVO.getRefuseMemo()));

        // 记录售后日志
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(),
                AfterSaleStatusEnum.SELLER_REFUSE.getStatus(),
                MapUtil.of("reason", refuseReqVO.getRefuseMemo()));

        // TODO 发送售后消息

        // 更新交易订单项的售后状态为【未申请】
        tradeOrderUpdateService.updateOrderItemWhenAfterSaleCancel(afterSale.getOrderItemId());
    }

    /**
     * 校验售后单是否可收货，即处于买家已发货
     *
     * @param id 售后编号
     * @return 售后单
     */
    private AfterSaleDO validateAfterSaleReceivable(Long id) {
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(afterSale.getStatus(), AfterSaleStatusEnum.BUYER_DELIVERY.getStatus())) {
            throw exception(AFTER_SALE_CONFIRM_FAIL_STATUS_NOT_BUYER_DELIVERY);
        }
        return afterSale;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.ADMIN_REFUND)
    public void refundAfterSale(Long userId, String userIp, Long id) {
        // 校验售后单的状态，并状态待退款
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (ObjectUtil.notEqual(afterSale.getStatus(), AfterSaleStatusEnum.WAIT_REFUND.getStatus())) {
            throw exception(AFTER_SALE_REFUND_FAIL_STATUS_NOT_WAIT_REFUND);
        }

        // 发起退款单。注意，需要在事务提交后，再进行发起，避免重复发起
        createPayRefund(userIp, afterSale);

        // 更新售后单的状态为【已完成】
        updateAfterSaleStatus(afterSale.getId(), AfterSaleStatusEnum.WAIT_REFUND.getStatus(), new AfterSaleDO()
                .setStatus(AfterSaleStatusEnum.COMPLETE.getStatus()).setRefundTime(LocalDateTime.now()));

        // 记录售后日志
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(),
                AfterSaleStatusEnum.COMPLETE.getStatus());

        //如果是老师商品特殊处理
        TradeOrderItemDO orderItemDO = orderItemMapper.selectById(afterSale.getOrderItemId());
        if(Objects.equals(orderItemDO.getItemType(), TradeDietitianEnum.DIETITIAN.getType())){

            LambdaQueryWrapperX<DietitianOrderLogDO> doLambdaQueryWrapperX = new LambdaQueryWrapperX<>();
            doLambdaQueryWrapperX.eq(DietitianOrderLogDO::getDietitianId,orderItemDO.getDietitianId())
                    .eq(DietitianOrderLogDO::getItemNumber,orderItemDO.getItemNumber())
                            .eq(DietitianOrderLogDO::getOrderId,afterSale.getOrderId())
                                .eq(DietitianOrderLogDO::getDietitianSpuId,orderItemDO.getDietitianSpuId());

            DietitianOrderLogDO dietitianOrderLogDO = dietitianOrderLogMapper.selectOne(doLambdaQueryWrapperX);
            dietitianOrderLogDO.setPayPrice(dietitianOrderLogDO.getPayPrice()-afterSale.getRefundPrice());
            dietitianOrderLogMapper.updateById(dietitianOrderLogDO);
        }

        // TODO 发送售后消息

        // 更新交易订单项的售后状态为【已完成】
        tradeOrderUpdateService.updateOrderItemWhenAfterSaleSuccess(afterSale.getOrderItemId(), afterSale.getRefundPrice());
    }

    private void createPayRefund(String userIp, AfterSaleDO afterSale) {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                // 创建退款单
                PayRefundCreateReqDTO createReqDTO = AfterSaleConvert.INSTANCE.convert(userIp, afterSale, tradeOrderProperties)
                        .setReason(StrUtil.format("退款【{}】", afterSale.getSpuName()));
                Long payRefundId = payRefundApi.createRefund(createReqDTO);
                // 更新售后单的退款单号
                tradeAfterSaleMapper.updateById(new AfterSaleDO().setId(afterSale.getId()).setPayRefundId(payRefundId));
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AfterSaleLog(operateType = AfterSaleOperateTypeEnum.MEMBER_CANCEL)
    public void cancelAfterSale(Long userId, Long id) {
        // 校验售后单的状态，并状态待退款
        AfterSaleDO afterSale = tradeAfterSaleMapper.selectById(id);
        if (afterSale == null) {
            throw exception(AFTER_SALE_NOT_FOUND);
        }
        if (!ObjectUtils.equalsAny(afterSale.getStatus(), AfterSaleStatusEnum.APPLY.getStatus(),
                AfterSaleStatusEnum.SELLER_AGREE.getStatus(),
                AfterSaleStatusEnum.BUYER_DELIVERY.getStatus())) {
            throw exception(AFTER_SALE_CANCEL_FAIL_STATUS_NOT_APPLY_OR_AGREE_OR_BUYER_DELIVERY);
        }

        // 更新售后单的状态为【已取消】
        updateAfterSaleStatus(afterSale.getId(), afterSale.getStatus(), new AfterSaleDO()
                .setStatus(AfterSaleStatusEnum.BUYER_CANCEL.getStatus()));

        // 记录售后日志
        AfterSaleLogUtils.setAfterSaleInfo(afterSale.getId(), afterSale.getStatus(),
                AfterSaleStatusEnum.BUYER_CANCEL.getStatus());

        // TODO 发送售后消息

        // 更新交易订单项的售后状态为【未申请】
        tradeOrderUpdateService.updateOrderItemWhenAfterSaleCancel(afterSale.getOrderItemId());
    }

    @Override
    public Long getApplyingAfterSaleCount(Long userId) {
        return tradeAfterSaleMapper.selectCountByUserIdAndStatus(userId, AfterSaleStatusEnum.APPLYING_STATUSES);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public  List<Long> createAfterSaleAll(Long userId, AppAfterSaleCreateAllReqVO createAllReqVO) {

        List<Long>  saleIds = new ArrayList<>();

        //更具订单查询所有的订单项
        List<TradeOrderItemDO> orderItems = tradeOrderQueryService.getOrderItemListByOrderId(createAllReqVO.getOrderId());

        //循环订单项，组装数据把createAllReqVO orderItem数据 组合到AppAfterSaleCreateReqVO
        for (TradeOrderItemDO orderItem : orderItems) {
            AppAfterSaleCreateReqVO createReqVO = new AppAfterSaleCreateReqVO();
            createReqVO.setOrderItemId(orderItem.getId());
            createReqVO.setRefundPrice(orderItem.getPayPrice());
            createReqVO.setWay(createAllReqVO.getWay());
            createReqVO.setApplyDescription(createAllReqVO.getApplyDescription());
            createReqVO.setApplyReason(createAllReqVO.getApplyReason());
            createReqVO.setApplyPicUrls(createAllReqVO.getApplyPicUrls());
            saleIds.add(this.createAfterSale(userId, createReqVO));
        }
        // 标记是售后
        TradeOrderDO order = tradeOrderQueryService.getOrder(userId, createAllReqVO.getOrderId());
        order.setAfterStatus(AfterSaleStatusEnum.APPLY.getStatus());
        tradeOrderMapper.updateById(order);
        return saleIds;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void  cancelAfterSaleAll(Long userId, Long orderId){
        LambdaQueryWrapperX<AfterSaleDO> lambdaQueryWrapper = new LambdaQueryWrapperX<>();
        lambdaQueryWrapper.eq(AfterSaleDO::getOrderId, orderId)
                .and(wrapper -> wrapper.eq(AfterSaleDO::getStatus, 10)
                        .or().eq(AfterSaleDO::getStatus, 20)
                        .or().eq(AfterSaleDO::getStatus, 30));

        List<AfterSaleDO> afterSaleDOS = tradeAfterSaleMapper.selectList(lambdaQueryWrapper);

        if(afterSaleDOS.isEmpty()){
            throw exception(new ErrorCode(300,"订单对应售后不存在"));
        }

        for (AfterSaleDO afterSaleDO : afterSaleDOS) {
            this.cancelAfterSale(userId, afterSaleDO.getId());
        }

        TradeOrderDO order = tradeOrderQueryService.getOrder(userId, orderId);
        order.setAfterStatus(TradeOrderItemAfterSaleStatusEnum.NONE.getStatus());
        tradeOrderMapper.updateById(order);

    }

}
