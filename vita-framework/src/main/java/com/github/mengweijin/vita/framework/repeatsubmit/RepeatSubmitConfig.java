package com.github.mengweijin.vita.framework.repeatsubmit;

import com.github.mengweijin.vita.framework.repeatsubmit.inteceptor.SameUrlDataInterceptor;
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
     * Redis 暂未适配完成 TODO
     */
    @SuppressWarnings({"all"})
    private final boolean useRedisCache = false;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        if(useRedisCache) {
            registry.addInterceptor(new SameUrlDataInterceptor()).addPathPatterns("/**");
        }
    }

}
