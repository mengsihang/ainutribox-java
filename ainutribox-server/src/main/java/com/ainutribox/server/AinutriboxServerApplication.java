package com.ainutribox.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 项目的启动类
 *
 * 如果你碰到启动的问题，请认真阅读 https://doc.iocoder.cn/quick-start/ 文章
 *
 * @author 河南小泉山科技111111
 */
@SuppressWarnings("SpringComponentScan") // 忽略 IDEA 无法识别 ${ainutribox.info.base-package}
@SpringBootApplication(scanBasePackages = {"${ainutribox.info.base-package}.server", "${ainutribox.info.base-package}.module"})
@EnableFeignClients(basePackages = "${ainutribox.info.base-package}.module.agent.client")
public class AinutriboxServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(AinutriboxServerApplication.class, args);
//        new SpringApplicationBuilder(AinutriboxServerApplication.class)
//                .applicationStartup(new BufferingApplicationStartup(20480))
//                .run(args);

    }

}
