package com.ainutribox.module.pay.job.refund;

import cn.hutool.core.util.StrUtil;
import com.ainutribox.framework.quartz.core.handler.JobHandler;
import com.ainutribox.framework.tenant.core.job.TenantJob;
import com.ainutribox.module.pay.service.refund.PayRefundService;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;

/**
 * 退款订单的同步 Job
 *
 * 由于退款订单的状态，是由支付渠道异步通知进行同步，考虑到异步通知可能会失败（小概率），所以需要定时进行同步。
 *
 * @author 河南小泉山科技
 */
@Component
public class PayRefundSyncJob implements JobHandler {

    @Resource
    private PayRefundService refundService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = refundService.syncRefund();
        return StrUtil.format("同步退款订单 {} 个", count);
    }

}
