package com.ainutribox.framework.mq.redis.config;

import com.ainutribox.framework.mq.redis.core.RedisMQTemplate;
import com.ainutribox.framework.mq.redis.core.interceptor.RedisMessageInterceptor;
import com.ainutribox.framework.redis.config.AinutriboxRedisAutoConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * Redis 消息队列 Producer 配置类
 *
 * @author 河南小泉山科技
 */
@Slf4j
@AutoConfiguration(after = AinutriboxRedisAutoConfiguration.class)
public class AinutriboxRedisMQProducerAutoConfiguration {

    @Bean
    public RedisMQTemplate redisMQTemplate(StringRedisTemplate redisTemplate,
                                           List<RedisMessageInterceptor> interceptors) {
        RedisMQTemplate redisMQTemplate = new RedisMQTemplate(redisTemplate);
        // 添加拦截器
        interceptors.forEach(redisMQTemplate::addInterceptor);
        return redisMQTemplate;
    }

}
