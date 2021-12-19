package com.github.mengweijin.xss;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * XssFilter 配置, 集成Spring Security后，不需要，security自带xss和csrf验证
 *
 * @author Meng Wei Jin
 *
 * havingValue: 可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
 * matchIfMissing: 缺少该property时是否可以加载。如果为true，没有该property也会正常加载；反之报错
 *
 * ConfigurationProperties 注解主要用来把properties配置文件转化为bean来使用。
 * EnableConfigurationProperties 注解的作用是使@ConfigurationProperties注解生效。
 * 如果只配置@ConfigurationProperties注解，在IOC容器中是获取不到properties配置文件转化的bean的。
 **/
@Configuration
@EnableConfigurationProperties(XssProperties.class)
@AllArgsConstructor
public class XssFilterAutoConfiguration {

    private final XssProperties xssProperties;

    /**
     * 对所有请求都进行xss过滤
     */
    private static final String[] URL_PATTERNS = {"/*"};


    public static final String COMMA = ",";

    public static final String CARET = "^";

    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean xssFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns(URL_PATTERNS);
        registration.setName("xssFilter");
        registration.setOrder(Ordered.LOWEST_PRECEDENCE);
        Map<String, String> initParameters = new HashMap<>(2);
        initParameters.put(XssFilter.EXCLUDES, String.join(COMMA, xssProperties.getExcludes()));
        initParameters.put(XssFilter.ENABLED, String.valueOf(this.xssProperties.getEnabled()));
        registration.setInitParameters(initParameters);
        return registration;
    }
}
