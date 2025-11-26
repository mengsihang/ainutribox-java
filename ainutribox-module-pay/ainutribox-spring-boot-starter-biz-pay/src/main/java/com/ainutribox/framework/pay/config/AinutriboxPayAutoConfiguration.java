package com.ainutribox.framework.pay.config;

import com.ainutribox.framework.pay.core.client.PayClientFactory;
import com.ainutribox.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置类
 *
 * @author 河南小泉山科技
 */
@AutoConfiguration
public class AinutriboxPayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
