package com.github.mengweijin.vita.generator;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Meng Wei Jin
 **/
@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@SuppressWarnings({"java:S1118"})
public class GeneratorAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public static GeneratorBeanDefinitionRegistryPostProcessor generatorBeanDefinitionRegistryPostProcessor(){
        return new GeneratorBeanDefinitionRegistryPostProcessor();
    }

}
