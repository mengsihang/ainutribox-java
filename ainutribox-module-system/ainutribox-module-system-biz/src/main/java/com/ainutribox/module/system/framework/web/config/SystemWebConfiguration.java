package com.ainutribox.module.system.framework.web.config;

import com.ainutribox.framework.swagger.config.AinutriboxSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system 模块的 web 组件的 Configuration
 *
 * @author 河南小泉山科技
 */
@Configuration(proxyBeanMethods = false)
public class SystemWebConfiguration {

    /**
     * system 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi systemGroupedOpenApi() {
        return AinutriboxSwaggerAutoConfiguration.buildGroupedOpenApi("system");
    }

}
