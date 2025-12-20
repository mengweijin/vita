package com.github.mengweijin.vita.framework.cache;

import cn.hutool.v7.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * @author mengweijin
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheFactory {
    
    private static final CacheManager CACHE_MANAGER = SpringUtil.getBean(CacheManager.class);

    private static final CacheManager LOCAL_CACHE_MANAGER = SpringUtil.getBean(CacheConfig.LOCAL_CACHE_MANAGER, CacheManager.class);

    private static final CacheManager REDIS_CACHE_MANAGER = SpringUtil.getBean(CacheConfig.REDIS_CACHE_MANAGER, CacheManager.class);

    public static Cache getRepeatSubmitCache() {
        return CACHE_MANAGER.getCache(CacheNames.REPEAT_SUBMIT);
    }

    public static Cache getSseEmitterMessageCache() {
        return CACHE_MANAGER.getCache(CacheNames.SSE_EMITTER_MESSAGE);
    }

    public static Cache getRateLimitCache() {
        return CACHE_MANAGER.getCache(CacheNames.RATE_LIMIT);
    }

    public static Cache getCaptchaCache() {
        return LOCAL_CACHE_MANAGER.getCache(CacheNames.CAPTCHA);
    }
}
