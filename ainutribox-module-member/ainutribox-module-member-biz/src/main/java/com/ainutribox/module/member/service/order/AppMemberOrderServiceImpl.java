package com.ainutribox.module.member.service.order;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.ainutribox.framework.common.core.KeyValue;
import com.ainutribox.framework.common.util.json.JsonUtils;
import com.ainutribox.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.ainutribox.module.member.controller.app.order.vo.AppVipClassOrderCreateReqVO;
import com.ainutribox.module.member.controller.app.order.vo.AppVipClassOrderVo;
import com.ainutribox.module.member.convert.order.MemberOrderConvert;
import com.ainutribox.module.member.dal.dataobject.dietitionclass.DietitionClassDO;
import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;
import com.ainutribox.module.member.dal.dataobject.vippackage.VipPackageDO;
import com.ainutribox.module.member.dal.mysql.dietitionclass.DietitionClassMapper;
import com.ainutribox.module.member.dal.mysql.order.MemberOrderMapper;
import com.ainutribox.module.member.enums.memberorder.MemberOrderStatusEnum;
import com.ainutribox.module.member.framework.order.config.ClassVipOrderProperties;
import com.ainutribox.module.member.service.order.handler.MemberOrderHandler;
import com.ainutribox.module.member.service.user.MemberUserService;
import com.ainutribox.module.member.service.vippackage.VipPackageService;
import com.ainutribox.module.pay.api.order.PayOrderApi;
import com.ainutribox.module.pay.api.order.dto.PayOrderCreateReqDTO;
import com.ainutribox.module.pay.api.order.dto.PayOrderRespDTO;
import com.ainutribox.module.pay.enums.order.PayOrderStatusEnum;
import com.ainutribox.module.trade.dal.redis.no.TradeNoRedisDAO;
import com.ainutribox.module.trade.enums.order.TradeOrderStatusEnum;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.ainutribox.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.ainutribox.framework.common.util.date.LocalDateTimeUtils.minusTime;
import static com.ainutribox.framework.common.util.servlet.ServletUtils.getClientIP;
import static com.ainutribox.framework.web.core.util.WebFrameworkUtils.getTerminal;
import static com.ainutribox.module.member.enums.ErrorCodeConstants.*;
import static com.ainutribox.module.trade.enums.ErrorCodeConstants.*;

/**
 * AppOrderServiceImpl
 *
 * @author lucifer
 * @date 2024-06-27 19:39
 */
@Service
@Slf4j
public class AppMemberOrderServiceImpl implements AppMemberOrderService {


    @Resource
    private VipPackageService packageService;

    @Resource
    private MemberUserService memberUserService;


    @Resource
    private DietitionClassMapper classMapper;

    @Resource
    private TradeNoRedisDAO tradeNoRedisDAO;

    @Resource
    private MemberOrderMapper orderMapper;

    @Resource
    private PayOrderApi payOrderApi;

    @Resource
    private ClassVipOrderProperties classVipOrderProperties;

    @Resource
    private List<MemberOrderHandler> memberOrderHandlers;

    @Resource
    private MemberOrderMapper memberOrderMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberOrderDO createOrder(Long userId, AppVipClassOrderCreateReqVO vipClassOrderCreateReqVO) {
        AppVipClassOrderVo appVipClassOrderVo = new AppVipClassOrderVo();
        //获取价格
        if(vipClassOrderCreateReqVO.getType() == 1){
            appVipClassOrderVo = calculateClassPrice(userId,vipClassOrderCreateReqVO,appVipClassOrderVo);
        }else{
            appVipClassOrderVo = calculateVipPrice(userId,vipClassOrderCreateReqVO,appVipClassOrderVo);
        }

        // 1.2 构建订单
        MemberOrderDO memberOrderDO = buildMemberOrder(userId,appVipClassOrderVo,vipClassOrderCreateReqVO);

        //保存订单
        orderMapper.insert(memberOrderDO);

        //生成预支付订单
        createPayOrder(memberOrderDO);

        return memberOrderDO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderPaid(Long id, Long payOrderId) {
        // 校验交易订单满足被支付的条件
        KeyValue<MemberOrderDO, PayOrderRespDTO> keyValue = validateOrderPayable(id, payOrderId);
        MemberOrderDO order = keyValue.getKey();
        PayOrderRespDTO payOrder = keyValue.getValue();

        // 更新交易订单状态
        orderMapper.update(new MemberOrderDO().setStatus(1).setPayStatus(true)
                        .setPayTime(LocalDateTime.now())
                        .setPayChannelCode(payOrder.getChannelCode()),
                                new LambdaUpdateWrapper<MemberOrderDO>().eq(MemberOrderDO::getId, id)
                                        .eq(MemberOrderDO::getStatus, 0));

        // 3. 执行 TradeOrderHandler 的后置处理
        memberOrderHandlers.forEach(handler -> handler.afterPayOrder(order));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void cancelOrderByMember(Long userId, Long id) {

        // 1.1 校验存在
        MemberOrderDO order = orderMapper.selectOne("id", id,"user_id", userId);
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        // 1.2 校验状态
        if (ObjectUtil.notEqual(order.getStatus(), MemberOrderStatusEnum.UNPAID.getStatus())) {
            throw exception(ORDER_CANCEL_FAIL_STATUS_NOT_UNPAID);
        }

        cancelOrder0(order,MemberOrderStatusEnum.MEMBER_CANCEL);

    }

    @Override
    public int cancelOrderBySystem() {
        // 1. 查询过期的待支付订单
        LocalDateTime expireTime = minusTime(classVipOrderProperties.getPayExpireTime());
        List<MemberOrderDO> orders = memberOrderMapper.selectListByStatusAndCreateTimeLt(
                TradeOrderStatusEnum.UNPAID.getStatus(), expireTime);
        if (CollUtil.isEmpty(orders)) {
            return 0;
        }

        // 2. 遍历执行，逐个取消
        int count = 0;
        for (MemberOrderDO order : orders) {
            try {
                getSelf().cancelOrderBySystem(order);
                count++;
            } catch (Throwable e) {
                log.error("[cancelOrderBySystem][order({}) 过期订单异常]", order.getId(), e);
            }
        }
        return count;
    }

    /**
     * 自动取消单个订单
     *
     * @param order 订单
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrderBySystem(MemberOrderDO order) {
        cancelOrder0(order,MemberOrderStatusEnum.PAY_TIMEOUT);
    }


    public AppVipClassOrderVo calculateVipPrice(Long userId, AppVipClassOrderCreateReqVO vipClassOrderCreateReqVO, AppVipClassOrderVo appVipClassOrderVo){
        //查询套餐价格
        VipPackageDO vipPackageDO = packageService.getVipPackage(vipClassOrderCreateReqVO.getBuyId());

        if(ObjectUtil.isEmpty(vipPackageDO)){
            throw exception(VIP_PACKAGE_NOT_EXISTS);
        }
        appVipClassOrderVo.setPayPrice(vipPackageDO.getPrice()-vipPackageDO.getActivityPrice());
        appVipClassOrderVo.setByName(vipPackageDO.getPackageName());
        appVipClassOrderVo.setDurationMonth(vipPackageDO.getDurationMonth());
        return  appVipClassOrderVo;
    }

    public AppVipClassOrderVo calculateClassPrice(Long userId, AppVipClassOrderCreateReqVO vipClassOrderCreateReqVO,AppVipClassOrderVo appVipClassOrderVo){
        //判断是否已拥有课程
        Integer classStatus = memberUserService.isMemberPayClassExpired(userId,vipClassOrderCreateReqVO.getBuyId());
        if(classStatus == 1){
            throw exception(USER_PAY_CLASS);
        }
        //判断课程是否可以购买
        LambdaQueryWrapperX<DietitionClassDO> classWrapperx = new LambdaQueryWrapperX<>();
        classWrapperx.eq(DietitionClassDO::getId,vipClassOrderCreateReqVO.getBuyId())
                .eq(DietitionClassDO::getStatus,0)
                .eq(DietitionClassDO::getDeleted,false);
        DietitionClassDO dietitionClassDO = classMapper.selectOne(classWrapperx);
        if(ObjectUtil.isEmpty(dietitionClassDO)){
            throw exception(PAY_CLASS_STATUS_EXISTS);
        }
        //判断当前用户是否为vip
        Integer vipStatus = memberUserService.isMemberVipExpired(userId);
        appVipClassOrderVo.setByName(dietitionClassDO.getName());
        appVipClassOrderVo.setPayPrice(dietitionClassDO.getPrice());
        appVipClassOrderVo.setDietitonId(dietitionClassDO.getDietitionId());
        if(vipStatus == 1){
            appVipClassOrderVo.setPayPrice(dietitionClassDO.getVipPrice());
        }
        return  appVipClassOrderVo;
    }

    private MemberOrderDO buildMemberOrder(Long userId,AppVipClassOrderVo appVipClassOrderVo,AppVipClassOrderCreateReqVO vipClassOrderCreateReqVO){
        MemberOrderDO memberOrderDO = new MemberOrderDO();
        memberOrderDO.setNo(tradeNoRedisDAO.generate(TradeNoRedisDAO.AFTER_CLASS_NO_PREFIX));
        memberOrderDO.setPayPrice(appVipClassOrderVo.getPayPrice());
        memberOrderDO.setBuyId(vipClassOrderCreateReqVO.getBuyId());
        memberOrderDO.setType(vipClassOrderCreateReqVO.getType());
        memberOrderDO.setStatus(MemberOrderStatusEnum.UNPAID.getStatus());
        memberOrderDO.setUserIp(getClientIP()).setTerminal(getTerminal());
        memberOrderDO.setPayStatus(false);
        memberOrderDO.setUserId(userId);
        memberOrderDO.setDietitionId(appVipClassOrderVo.getDietitonId());
        memberOrderDO.setDurationMonth(appVipClassOrderVo.getDurationMonth());
        memberOrderDO.setBuyName(appVipClassOrderVo.getByName());
        return memberOrderDO;
    }


    private void createPayOrder(MemberOrderDO order) {
        // 创建支付单，用于后续的支付
        PayOrderCreateReqDTO payOrderCreateReqDTO = MemberOrderConvert.INSTANCE.convert(
                order, classVipOrderProperties);
        Long payOrderId = payOrderApi.createOrder(payOrderCreateReqDTO);

        // 更新到交易单上
        orderMapper.updateById(new MemberOrderDO().setId(order.getId()).setPayOrderId(payOrderId));
        order.setPayOrderId(payOrderId);
    }


    /**
     * 校验交易订单满足被支付的条件
     * <p>
     * 1. 交易订单未支付
     * 2. 支付单已支付
     *
     * @param id         交易订单编号
     * @param payOrderId 支付订单编号
     * @return 交易订单
     */
    private KeyValue<MemberOrderDO, PayOrderRespDTO> validateOrderPayable(Long id, Long payOrderId) {
        // 校验订单是否存在
        MemberOrderDO order = validateOrderExists(id);
        // 校验订单未支付
        if (!TradeOrderStatusEnum.isUnpaid(order.getStatus()) || order.getPayStatus()) {
            log.error("[validateOrderPaid][order({}) 不处于待支付状态，请进行处理！order 数据是：{}]",
                    id, JsonUtils.toJsonString(order));
            throw exception(ORDER_UPDATE_PAID_STATUS_NOT_UNPAID);
        }
        // 校验支付订单匹配
        if (ObjectUtil.notEqual(order.getPayOrderId(), payOrderId)) { // 支付单号
            log.error("[validateOrderPaid][order({}) 支付单不匹配({})，请进行处理！order 数据是：{}]",
                    id, payOrderId, JsonUtils.toJsonString(order));
            throw exception(ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }

        // 校验支付单是否存在
        PayOrderRespDTO payOrder = payOrderApi.getOrder(payOrderId);
        if (payOrder == null) {
            log.error("[validateOrderPaid][order({}) payOrder({}) 不存在，请进行处理！]", id, payOrderId);
            throw exception(ORDER_NOT_FOUND);
        }
        // 校验支付单已支付
        if (!PayOrderStatusEnum.isSuccess(payOrder.getStatus())) {
            log.error("[validateOrderPaid][order({}) payOrder({}) 未支付，请进行处理！payOrder 数据是：{}]",
                    id, payOrderId, JsonUtils.toJsonString(payOrder));
            throw exception(ORDER_UPDATE_PAID_FAIL_PAY_ORDER_STATUS_NOT_SUCCESS);
        }
        // 校验支付金额一致
        if (ObjectUtil.notEqual(payOrder.getPrice(), order.getPayPrice())) {
            log.error("[validateOrderPaid][order({}) payOrder({}) 支付金额不匹配，请进行处理！order 数据是：{}，payOrder 数据是：{}]",
                    id, payOrderId, JsonUtils.toJsonString(order), JsonUtils.toJsonString(payOrder));
            throw exception(ORDER_UPDATE_PAID_FAIL_PAY_PRICE_NOT_MATCH);
        }
        // 校验支付订单匹配（二次）
        if (ObjectUtil.notEqual(payOrder.getMerchantOrderId(), id.toString())) {
            log.error("[validateOrderPaid][order({}) 支付单不匹配({})，请进行处理！payOrder 数据是：{}]",
                    id, payOrderId, JsonUtils.toJsonString(payOrder));
            throw exception(ORDER_UPDATE_PAID_FAIL_PAY_ORDER_ID_ERROR);
        }
        return new KeyValue<>(order, payOrder);
    }


    private MemberOrderDO validateOrderExists(Long id) {
        // 校验订单是否存在
        MemberOrderDO order = orderMapper.selectById(id);
        if (order == null) {
            throw exception(ORDER_NOT_FOUND);
        }
        return order;
    }

    /**
     * 取消订单的核心实现
     * @param order      订单
     */
    private void cancelOrder0(MemberOrderDO order, MemberOrderStatusEnum cancelTypeEnum) {
        // 1. 更新 memberOrder 状态为已取消
        LambdaQueryWrapperX<MemberOrderDO> queryWrapper = new LambdaQueryWrapperX<MemberOrderDO>()
                .eq(MemberOrderDO::getId, order.getId())
                .eq(MemberOrderDO::getStatus, order.getStatus());
        MemberOrderDO memberOrderDO =  new MemberOrderDO().setStatus(TradeOrderStatusEnum.CANCELED.getStatus())
                .setCancelTime(LocalDateTime.now()).setCancelType(cancelTypeEnum.getStatus());
        int updateCount = orderMapper.update(memberOrderDO, queryWrapper);

        if (updateCount == 0) {
            throw exception(ORDER_CANCEL_FAIL_STATUS_NOT_UNPAID);
        }
    }


    /**
     * 获得自身的代理对象，解决 AOP 生效问题
     *
     * @return 自己
     */
    private AppMemberOrderServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
