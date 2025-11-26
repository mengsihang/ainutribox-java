package com.ainutribox.module.infra.framework.file.config;

import com.ainutribox.module.infra.framework.file.core.client.FileClientFactory;
import com.ainutribox.module.infra.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置类
 *
 * @author 河南小泉山科技
 */
@Configuration(proxyBeanMethods = false)
public class AinutriboxFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
