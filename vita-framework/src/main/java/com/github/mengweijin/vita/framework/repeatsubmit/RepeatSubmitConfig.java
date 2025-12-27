package com.github.mengweijin.vita.framework.repeatsubmit;

import com.github.mengweijin.vita.framework.repeatsubmit.inteceptor.SameUrlDataInterceptor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author mengweijin
 * @since 2025/12/21
 */
@Configuration
@ConditionalOnClass({RedisConnectionFactory.class, RedisAutoConfiguration.class})
public class RepeatSubmitConfig implements WebMvcConfigurer {

    /**
     * 考虑到应用的轻量化，防止可重复提交的功能：
     * 默认使用 {@link com.github.mengweijin.vita.framework.repeatsubmit.aop.LocalCacheRepeatSubmitAspect}
     * 而不启用 Redis {@link SameUrlDataInterceptor} 拦截器。
     * 说明：两个使用其中一个即可，否则两个都会生效。此处保留 Redis 的拦截器实现，以方便其它地方作为参考。
     */
    @Setter
    private boolean enabledRedisInterceptor = false;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        if(enabledRedisInterceptor) {
            registry.addInterceptor(new SameUrlDataInterceptor()).addPathPatterns("/**");
        }
    }

}
