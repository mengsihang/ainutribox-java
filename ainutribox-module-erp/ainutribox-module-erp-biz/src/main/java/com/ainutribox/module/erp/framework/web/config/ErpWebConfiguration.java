package com.ainutribox.module.erp.framework.web.config;

import com.ainutribox.framework.swagger.config.AinutriboxSwaggerAutoConfiguration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * erp 模块的 web 组件的 Configuration
 *
 * @author 河南小泉山科技
 */
@Configuration(proxyBeanMethods = false)
public class ErpWebConfiguration {

    /**
     * erp 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi erpGroupedOpenApi() {
        return AinutriboxSwaggerAutoConfiguration.buildGroupedOpenApi("erp");
    }

}
