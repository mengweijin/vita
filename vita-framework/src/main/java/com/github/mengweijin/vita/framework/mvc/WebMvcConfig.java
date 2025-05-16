package com.github.mengweijin.vita.framework.mvc;

import com.github.mengweijin.vita.framework.VitaProperties;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.time.Duration;

/**
 * @author Meng Wei Jin
 **/
@Configuration
@AllArgsConstructor
@SuppressWarnings({"unused"})
public class WebMvcConfig implements WebMvcConfigurer {

    private VitaProperties vitaProperties;

    /**
     * 允许跨域。
     * 也可以使用 WebMvcConfigurer 类中的 addCorsMappings(@NonNull CorsRegistry registry) 方法配置，但会存一个问题。
     * 使用 addCorsMappings 实现跨域配置可能会失效：
     * 原因：当项目中存在自定义拦截器时，请求会先进入拦截器，导致跨域响应头未添加。
     * 解决：改用 CorsFilter 过滤器（优先级高于拦截器）。
     * 因此这里使用 CorsFilter 配置。
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 允许的源域名
        config.addAllowedOriginPattern(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);
        config.addAllowedHeader(CorsConfiguration.ALL);
        // 允许携带凭证（如Cookie）
        config.setAllowCredentials(true);
        // 缓存时间
        config.setMaxAge(Duration.ofHours(1));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",  config);
        return new CorsFilter(source);
    }

    /**
     * 重定向：registry.addViewController("/").setViewName("/vita/index.html");
     * 转发：registry.addRedirectViewController("/", "/vita/index.html");
     *
     * @param registry ViewControllerRegistry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 将根路径 "/" 的请求转发到 "/index.html"
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    /**
     * 1. spring boot 默认从 header 的 Accept-Language 获取 Locale。如：Accept-Language：zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6
     * 参考：{@link org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration} 中的 LocaleResolver。
     * 默认 Locale 可通过 spring.web.locale=zh_CN 来设置。
     * 2. 也可以根据 url 中的参数切换 Locale。如：/system/user?lang=zh_CN
     * <p>
     * 使用 {@link I18nUtils}
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        // URL参数名
        interceptor.setParamName("lang");

        registry.addInterceptor(interceptor);
    }

}
