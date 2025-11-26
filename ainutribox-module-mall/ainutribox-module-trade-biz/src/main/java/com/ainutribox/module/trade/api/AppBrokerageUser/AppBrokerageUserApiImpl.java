package com.ainutribox.module.trade.api.AppBrokerageUser;

import com.ainutribox.module.trade.service.brokerage.BrokerageUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class AppBrokerageUserApiImpl implements AppBrokerageUserApi {

    @Resource
    private BrokerageUserService brokerageUserService;

    @Override
    public boolean bindBrokerageUser(Long userId, Long bindUserId) {
        return brokerageUserService.bindBrokerageUser(userId, bindUserId);
    }
}
