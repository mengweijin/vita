package com.github.mengweijin.vitality.framework;

import com.github.mengweijin.vitality.framework.log.DbErrorLoggerAppender;
import com.github.mengweijin.vitality.framework.log.LogAspect;
import com.github.mengweijin.vitality.framework.otp.TOTPService;
import com.github.mengweijin.vitality.system.VitalitySystemBeanDefinitionRegistryPostProcessor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 当任务新增进来时：
 * 1. 当前运行的线程 < corePoolSize 时，新起一个线程执行新增进来的任务；
 * 2. 当前运行的线程 >= corePoolSize 时，新增进来的任务添加到阻塞队列；
 * 3. 阻塞队列已经满了时，但当前运行线程数 < maxPoolSize, 新起一个线程执行新增进来的任务；
 * 4. 阻塞队列已经满了时，并且当前运行线程数 = maxPoolSize，执行任务丢弃策略。
 *
 * @author mengweijin
 */
//@EnableAsync // 不推荐使用
//@EnableScheduling // 不推荐使用
@MapperScan({ "com.github.mengweijin.vitality.**.mapper" })
@Configuration
@EnableConfigurationProperties({VitalityProperties.class})
public class VitalityAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public static VitalitySystemBeanDefinitionRegistryPostProcessor vitalitySystemBeanDefinitionRegistryPostProcessor() {
        return new VitalitySystemBeanDefinitionRegistryPostProcessor();
    }

    @Bean
    public LogAspect logAspect() {
        return new LogAspect(aopLog -> {});
    }

    @Bean
    public DbErrorLoggerAppender dbErrorLoggerAppender() {
        return new DbErrorLoggerAppender();
    }

    @Bean
    public TOTPService totpService() {
        return new TOTPService();
    }
}
