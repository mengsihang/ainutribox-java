package com.ainutribox.module.crm.job.customer;

import com.ainutribox.framework.quartz.core.handler.JobHandler;
import com.ainutribox.framework.tenant.core.job.TenantJob;
import com.ainutribox.module.crm.service.customer.CrmCustomerService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

/**
 * 客户自动掉入公海 Job
 *
 * @author 河南小泉山科技
 */
@Component
public class CrmCustomerAutoPutPoolJob implements JobHandler {

    @Resource
    private CrmCustomerService customerService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = customerService.autoPutCustomerPool();
        return String.format("掉入公海客户 %s 个", count);
    }

}