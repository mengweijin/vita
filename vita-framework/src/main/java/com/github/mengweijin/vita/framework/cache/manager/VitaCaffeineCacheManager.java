package com.github.mengweijin.vita.framework.cache.manager;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.mengweijin.vita.framework.cache.CacheSpecification;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.lang.NonNull;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存实现。基于 Caffeine
 * @author mengweijin
 * @since 2025/12/14
 */
public class VitaCaffeineCacheManager extends CaffeineCacheManager {

    public static final int DEFAULT_CAPACITY = 100;

    public static final long DEFAULT_MAXIMUM_SIZE = 1000;

    private final Map<String, Cache> cacheMap = new ConcurrentHashMap<>(16);

    /**
     * 默认过期时间（单位：毫秒）
     */
    @Getter
    @Setter
    private Duration defaultExpireAfterWrite;

    public VitaCaffeineCacheManager() {
        super();
    }

    @Override
    protected @NonNull Cache createCaffeineCache(@NonNull String name) {
        CacheSpecification spec = CacheSpecification.parse(name);
        // 创建 Caffeine 缓存构建器
        Caffeine<Object, Object> builder = Caffeine.newBuilder();
        // 设置 TTL
        if (spec.getTtl() > 0) {
            builder.expireAfterWrite(spec.getTtl(), TimeUnit.MILLISECONDS);
        } else {
            builder.expireAfterWrite(defaultExpireAfterWrite.toSeconds(), TimeUnit.SECONDS);
        }
        // 其他可选配置
        builder.initialCapacity(DEFAULT_CAPACITY)
                .maximumSize(DEFAULT_MAXIMUM_SIZE)
                .recordStats();
        // 创建 CaffeineCache
        return new CaffeineCache(spec.getCacheName(), builder.build());
    }

    @Override
    public @NonNull Collection<String> getCacheNames() {
        return Collections.unmodifiableSet(this.cacheMap.keySet());
    }

    @Override
    public Cache getCache(@NonNull String name) {
        CacheSpecification spec = CacheSpecification.parse(name);
        Cache cache = this.cacheMap.get(spec.getCacheName());
        if (cache == null) {
            // computeIfAbsent：当指定键不存在（或关联值为null）时，通过提供的函数计算新值并存入Map；若键已存在，则直接返回当前值。整个过程是原子性的，特别适合并发环境下的惰性计算场景。
            cache = this.cacheMap.computeIfAbsent(spec.getCacheName(), s -> createCaffeineCache(name));
        }
        return cache;
    }

}
