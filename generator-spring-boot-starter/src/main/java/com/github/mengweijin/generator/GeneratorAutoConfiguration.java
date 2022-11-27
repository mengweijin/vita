package com.github.mengweijin.generator;

import com.github.mengweijin.generator.config.GeneratorBeanDefinitionRegistryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Meng Wei Jin
 **/
@EnableCaching
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
public class GeneratorAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public GeneratorBeanDefinitionRegistryPostProcessor generatorBeanDefinitionRegistryPostProcessor(){
        return new GeneratorBeanDefinitionRegistryPostProcessor();
    }

}
