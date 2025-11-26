package com.ainutribox.module.member.framework.order.config;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

/**
 * 交易订单的配置项
 *
 * @author lucifer
 * @since  2024-06-27 19:39
 */
@Component
@ConfigurationProperties(prefix = "ainutribox.trade.classvip")
@Data
@Validated
public class ClassVipOrderProperties {

    /**
     * 应用编号
     */
    @NotNull(message = "应用编号不能为空")
    private Long appId;

    /**
     * 支付超时时间
     */
    @NotNull(message = "支付超时时间不能为空")
    private Duration payExpireTime;

}
