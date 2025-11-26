package com.ainutribox.module.member.controller.app.order;

import com.ainutribox.framework.common.pojo.CommonResult;
import com.ainutribox.framework.common.pojo.PageResult;
import com.ainutribox.framework.common.util.object.BeanUtils;
import com.ainutribox.framework.security.core.annotations.PreAuthenticated;
import com.ainutribox.framework.security.core.util.SecurityFrameworkUtils;
import com.ainutribox.module.member.controller.admin.order.vo.MemberOrderPageReqVO;
import com.ainutribox.module.member.controller.admin.order.vo.MemberOrderRespVO;
import com.ainutribox.module.member.controller.admin.order.vo.MemberOrderSaveReqVO;
import com.ainutribox.module.member.controller.app.order.vo.AppVipClassOrderCreateReqVO;
import com.ainutribox.module.member.controller.app.order.vo.AppVipClassOrderCreateRespVO;
import com.ainutribox.module.member.dal.dataobject.order.MemberOrderDO;
import com.ainutribox.module.member.service.order.AppMemberOrderService;
import com.ainutribox.module.member.service.order.MemberOrderService;
import com.ainutribox.module.pay.api.notify.dto.PayOrderNotifyReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.ainutribox.framework.common.pojo.CommonResult.success;
import static com.ainutribox.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "用户 App - 用户购买课程和VIP订单")
@RestController
@RequestMapping("/member/order")
@Validated
public class AppMemberOrderController {

    @Resource
    private AppMemberOrderService appMemberOrderService;

    @Resource
    private MemberOrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "创建用户购买课程和VIP订单")
    @PreAuthenticated
    public CommonResult<AppVipClassOrderCreateRespVO> createOrder(@Valid @RequestBody AppVipClassOrderCreateReqVO vipClassOrderCreateReqVO) {
        MemberOrderDO memberOrderDO = appMemberOrderService.createOrder(getLoginUserId(),vipClassOrderCreateReqVO);
        return success(new AppVipClassOrderCreateRespVO().setId(memberOrderDO.getId()).setPayOrderId(memberOrderDO.getPayOrderId()));
    }


    @PostMapping("/update-paid")
    @Operation(summary = "更新订单为已支付") // 由 pay-module 支付服务，进行回调，可见 PayNotifyJob
    public CommonResult<Boolean> updateOrderPaid(@RequestBody PayOrderNotifyReqDTO notifyReqDTO) {
        appMemberOrderService.updateOrderPaid(Long.valueOf(notifyReqDTO.getMerchantOrderId()),
                notifyReqDTO.getPayOrderId());
        return success(true);
    }


    @DeleteMapping("/cancel")
    @Operation(summary = "取消交易订单")
    @Parameter(name = "id", description = "交易订单编号")
    public CommonResult<Boolean> cancelOrder(@RequestParam("id") Long id) {
        appMemberOrderService.cancelOrderByMember(SecurityFrameworkUtils.getLoginUserId(), id);
        return success(true);
    }



    @GetMapping("/get")
    @Operation(summary = "获得用户购买课程和VIP订单")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<MemberOrderRespVO> getOrder(@RequestParam("id") Long id) {
        MemberOrderDO order = orderService.getOrder(id);
        return success(BeanUtils.toBean(order, MemberOrderRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得用户购买课程和VIP订单分页")
    @PreAuthenticated
    public CommonResult<PageResult<MemberOrderRespVO>> getOrderPage(@Valid MemberOrderPageReqVO pageReqVO) {
        pageReqVO.setUserId(getLoginUserId());
        PageResult<MemberOrderDO> pageResult = orderService.getOrderPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MemberOrderRespVO.class));
    }



}