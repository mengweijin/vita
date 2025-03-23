package com.github.mengweijin.vita.framework.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.swing.captcha.AbstractCaptcha;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * @author mengweijin
 */
@SuppressWarnings({"unused"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheFactory {

    private static final CacheManager CACHE_MANAGER = SpringUtil.getBean(CacheManager.class);

    public static Collection<String> getCacheNames() {
        Iterable<String> iterable = CACHE_MANAGER.getCacheNames();
        return CollUtil.sortByPinyin(CollUtil.toCollection(iterable));
    }

    public static <K, V> Cache<K, V> createCache(String cacheName, DynamicCache<K, V> dynamicCache) {
        return CACHE_MANAGER.createCache(cacheName, CacheConfig.config(dynamicCache));
    }

    public static <K, V> Cache<K, V> getCache(String cacheName) {
        return CACHE_MANAGER.getCache(cacheName);
    }

    public static Cache<String, String> getDictDataLabelCache() {
        return CACHE_MANAGER.getCache(CacheNames.DICT_VAL_TO_LABEL);
    }

    public static Cache<String, String> getDeptIdNameCache() {
        return CACHE_MANAGER.getCache(CacheNames.DEPT_ID_TO_NAME);
    }

    public static Cache<String, String> getUserIdUsernameCache() {
        return CACHE_MANAGER.getCache(CacheNames.USER_ID_TO_USERNAME);
    }

    public static Cache<String, String> getUserIdNicknameCache() {
        return CACHE_MANAGER.getCache(CacheNames.USER_ID_TO_NICKNAME);
    }

    public static Cache<String, Long> getRepeatSubmitCache() {
        return CACHE_MANAGER.getCache(CacheNames.REPEAT_SUBMIT);
    }

    public static Cache<String, SseEmitter> getSseEmitterMessageCache() {
        return CACHE_MANAGER.getCache(CacheNames.SSE_EMITTER_MESSAGE);
    }

    public static Cache<String, List<LocalDateTime>> getRateLimitCache() {
        return CACHE_MANAGER.getCache(CacheNames.RATE_LIMIT);
    }

    public static Cache<String, AbstractCaptcha> getCaptchaCache() {
        return CACHE_MANAGER.getCache(CacheNames.CAPTCHA);
    }
}
