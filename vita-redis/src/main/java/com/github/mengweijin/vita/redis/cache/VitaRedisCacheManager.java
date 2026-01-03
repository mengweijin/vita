package com.github.mengweijin.vita.redis.cache;

import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.cache.CacheSpecification;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.lang.NonNull;

import java.time.Duration;

/**
 * Redis 缓存管理器。
 * @author mengweijin
 * @since 2025/12/14
 */
public class VitaRedisCacheManager extends RedisCacheManager {

    public VitaRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    @Override
    protected @NonNull RedisCache createRedisCache(@NonNull String name, RedisCacheConfiguration cacheConfiguration) {
        CacheSpecification spec = CacheSpecification.parse(name);
        // 设置 TTL
        if (spec.getTtl() > 0) {
            cacheConfiguration.entryTtl(Duration.ofMillis(spec.getTtl()));
        } else {
            CacheProperties cacheProperties = SpringUtil.getBean(CacheProperties.class);
            cacheConfiguration.entryTtl(cacheProperties.getRedis().getTimeToLive());
        }
        return super.createRedisCache(spec.getCacheName(), cacheConfiguration);
    }

    @Override
    public @Nullable Cache getCache(@NonNull String name) {
        CacheSpecification spec = CacheSpecification.parse(name);
        return super.getCache(spec.getCacheName());
    }

}
