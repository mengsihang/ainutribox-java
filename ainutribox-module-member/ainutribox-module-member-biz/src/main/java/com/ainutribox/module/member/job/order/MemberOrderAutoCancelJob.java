package com.ainutribox.module.member.job.order;

import com.ainutribox.framework.quartz.core.handler.JobHandler;
import com.ainutribox.framework.tenant.core.job.TenantJob;
import com.ainutribox.module.member.service.order.AppMemberOrderService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 交易订单的自动过期 Job
 *
 * @author 河南小泉山科技
 */
@Component
public class MemberOrderAutoCancelJob implements JobHandler {

    @Resource
    private AppMemberOrderService appMemberOrderService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = appMemberOrderService.cancelOrderBySystem();
        return String.format("过期订单 %s 个", count);
    }

}
