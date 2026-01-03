package com.github.mengweijin.vita.redis.cache;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.util.ObjUtil;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheFactory;
import com.github.mengweijin.vita.framework.cache.LocalCacheConfig;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

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
public class RedisCacheConfig {

    private CacheProperties cacheProperties;

    /**
     * {@link GenericJackson2JsonRedisSerializer}：可以保存序列化对象的包名和类名，反序列化时可以根据这些信息将 JSON 数据转换回指定的 Java 对象。它适合需要保留类型信息的场景，但从 Redis 获取数据时需要将结果转为字符串后再解析为对象。
     * {@link Jackson2JsonRedisSerializer}：则直接将 Java 对象序列化为 JSON 字符串，反序列化时不需要额外的类型信息，使用更为简单。适合不需要保留类型信息的场景。
     */
    @Bean(name = CacheConst.CACHE_MANAGER_REDIS)
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer) {
        CacheProperties.Redis redis = cacheProperties.getRedis();
        Duration timeToLive = ObjUtil.defaultIfNull(redis.getTimeToLive(), LocalCacheConfig.DEFAULT_TIME_TO_LIVE);
        String keyPrefix = StrUtil.defaultIfBlank(redis.getKeyPrefix(), "cache::");

        // 创建默认配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer))
                .entryTtl(timeToLive);
        if(!redis.isCacheNullValues()) {
            config.disableCachingNullValues();
        }
        if(redis.isUseKeyPrefix()) {
            config.computePrefixWith(cacheName -> keyPrefix + cacheName + "::");
        }
        // 创建自定义 Redis 缓存管理器
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        return new VitaRedisCacheManager(redisCacheWriter, config);
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(".");
            sb.append(method.getName());
            for (Object param : params) {
                if (param != null) {
                    sb.append(":");
                    sb.append(param);
                }
            }
            return sb.toString();
        };
    }

}
