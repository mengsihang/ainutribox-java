package com.ainutribox.module.mp.framework.web.config;

import com.ainutribox.framework.swagger.config.AinutriboxSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mp 模块的 web 组件的 Configuration
 *
 * @author 河南小泉山科技
 */
@Configuration(proxyBeanMethods = false)
public class MpWebConfiguration {

    /**
     * mp 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi mpGroupedOpenApi() {
        return AinutriboxSwaggerAutoConfiguration.buildGroupedOpenApi("mp");
    }

}
