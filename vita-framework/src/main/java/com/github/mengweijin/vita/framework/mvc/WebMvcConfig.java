package com.github.mengweijin.vita.framework.mvc;

import com.github.mengweijin.vita.framework.VitaProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
        registry.addViewController("/").setViewName("forward:/vita/index.html");
    }

}
