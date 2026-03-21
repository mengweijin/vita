package com.github.mengweijin.vita.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.text.CharSequenceUtil;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.monitor.domain.vo.CacheVO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@RestController
@RequestMapping("/monitor/cache-local")
public class CacheLocalController {

    private static final String LOG_TITLE = "本地缓存";

    private final CacheManager cacheManager;

    public CacheLocalController(@Qualifier(CacheConst.CACHE_MANAGER_LOCAL) CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @SaCheckPermission("monitor:cacheLocal:view")
    @GetMapping("/query/cacheNames")
    public Collection<String> queryCacheNames() {
        Iterable<String> iterable = cacheManager.getCacheNames();
        return CollUtil.sortByPinyin(CollUtil.toCollection(iterable));
    }

    @SaCheckPermission("monitor:cacheLocal:view")
    @GetMapping("/list/cache/by/name")
    public List<CacheVO> getCacheByName(@RequestParam("cacheName") String cacheName) {
        List<CacheVO> list = new ArrayList<>();

        Map<Object, Object> cacheMap = new HashMap<>(16);
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            // 获取 Caffeine Cache 底层的 Native Cache，并调用 asMap()
            Object object = cache.getNativeCache();
            if(object instanceof com.github.benmanes.caffeine.cache.Cache<?, ?> nativeCache) {
                cacheMap = new HashMap<>(nativeCache.asMap());
            }
        }
        cacheMap.forEach((k, v) -> {
            String key = CharSequenceUtil.toString(k);
            list.add(new CacheVO(cacheName, key, v));
        });
        return list;
    }

    @SaCheckPermission("monitor:cacheLocal:view")
    @GetMapping("/query/cache/by/nameAndKey")
    public CacheVO getCacheByNameAndKey(@RequestParam("cacheName") String cacheName, @RequestParam("cacheKey") String cacheKey) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Object value = cache.get(cacheKey);
            return new CacheVO(cacheName, cacheKey, value);
        }
        return null;
    }
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("monitor:cacheLocal:remove")
    @PostMapping("/remove")
    public R<Void> remove(@RequestParam("cacheName") String cacheName, @RequestParam(name = "cacheKey") Serializable cacheKey) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            boolean removed = cache.evictIfPresent(cacheKey);
            return R.result(removed);
        }
        return R.ok();
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("monitor:cacheLocal:remove")
    @PostMapping("/clear/by/name/{cacheName}")
    public R<Void> clearByName(@PathVariable("cacheName") String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
        return R.ok();
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("monitor:cacheLocal:remove")
    @PostMapping("/clear")
    public R<Void> clear() {
        Collection<String> cacheNames = this.queryCacheNames();
        for (String cacheName : cacheNames) {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache != null) {
                cache.clear();
            }
        }
        return R.ok();
    }
}