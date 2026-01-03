package com.github.mengweijin.vita.framework.cache;

import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

/**
 * Spring Cache Documents 参考：<a href="https://docs.spring.io/spring-framework/docs/current/reference/html/integration.html#cache">Spring Cache Documents</a>
 * <p>
 * 1、使用注解
 * KEY_EXPRESSION 为 @Cacheable 中的 key 值，默认使用 SPEL 表达式，若要拼接普通文本，需要用单引号包裹起来。
 * <p>
 * Example 1: @Cacheable(cacheNames = CacheNames.USER, key = CacheConst.KEY_CLASS_METHOD, unless = "#result?.size() == 0")
 * Example 2: @Cacheable(cacheNames = CacheNames.USER, key = CacheConst.KEY_CLASS + "+#username + 'zhangsan'", unless = "#result == null")
 * Example 3（指定缓存管理器）: @Cacheable(cacheManager = "redisCacheManager", cacheNames = CacheNames.User, key = CacheConst.KEY_CLASS_METHOD)
 * <p>
 * 2、使用 {@link CacheFactory}
 * 3、指定缓存管理器。不指定默认为 @Primary 注解的默认缓存管理器。
 *     - @Cacheable(cacheManager = "caffeineCacheManager")
 *     - @Cacheable(cacheManager = "redisCacheManager")
 * @author mengweijin
 * @since 2025/12/14
 */
@EnableCaching
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({CacheProperties.class})
public class LocalCacheConfig {

    /**
     * 600s = 10 分钟
     */
    public static final Duration DEFAULT_TIME_TO_LIVE = Duration.ofSeconds(600L);

    private CacheProperties cacheProperties;

    @Primary
    @Bean(name = CacheConst.CACHE_MANAGER_LOCAL)
    public CacheManager localCacheManager() {
        VitaCaffeineCacheManager cacheManager = new VitaCaffeineCacheManager();
        Duration timeToLive = cacheProperties.getRedis().getTimeToLive();
        timeToLive = timeToLive == null ? DEFAULT_TIME_TO_LIVE : timeToLive;
        cacheManager.setDefaultExpireAfterWrite(timeToLive);
        return cacheManager;
    }

}
