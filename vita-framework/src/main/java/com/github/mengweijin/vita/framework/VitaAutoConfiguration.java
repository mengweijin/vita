package com.github.mengweijin.vita.framework;

import org.dromara.hutool.swing.captcha.generator.MathGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 * EnableAspectJAutoProxy：
 * UserService userService = (UserService) AopContext.currentProxy();
 * <p>
 * 当任务新增进来时：
 * 1. 当前运行的线程 < corePoolSize 时，新起一个线程执行新增进来的任务；
 * 2. 当前运行的线程 >= corePoolSize 时，新增进来的任务添加到阻塞队列；
 * 3. 阻塞队列已经满了时，但当前运行线程数 < maxPoolSize, 新起一个线程执行新增进来的任务；
 * 4. 阻塞队列已经满了时，并且当前运行线程数 = maxPoolSize，执行任务丢弃策略。
 *
 * @author mengweijin
 */
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableAsync(proxyTargetClass = true)
@Configuration
@EnableConfigurationProperties({VitaProperties.class})
public class VitaAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    /**
     * 四则运算验证码生成器
     * @return MathGenerator
     */
    @Bean
    public MathGenerator mathGenerator() {
        return new MathGenerator(1);
    }
}
