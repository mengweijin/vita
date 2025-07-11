package com.github.mengweijin.vita.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.mengweijin.vita.framework.cache.CacheFactory;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.monitor.domain.vo.CacheVO;
import lombok.AllArgsConstructor;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.cache.Cache;
import javax.cache.CacheManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@AllArgsConstructor
@RestController
@RequestMapping("/monitor/cache")
public class CacheController {

    private static final String LOG_TITLE = "缓存监控";

    private CacheManager cacheManager;

    @SaCheckPermission("monitor:cache:view")
    @GetMapping("/names")
    public Collection<String> getCacheNames() {
        return CacheFactory.getCacheNames();
    }

    @SaCheckPermission("monitor:cache:view")
    @GetMapping("/query")
    public List<CacheVO> getCacheByName(@RequestParam("cacheName") String cacheName) {
        List<CacheVO> list = new ArrayList<>();
        Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            for (Cache.Entry<Object, Object> next : cache) {
                if(next != null) {
                    String key = CharSequenceUtil.toString(next.getKey());
                    list.add(new CacheVO(key, key, next.getValue()));
                }
            }
        }
        return list;
    }

    @SaCheckPermission("monitor:cache:view")
    @GetMapping("/queryCacheByNameAndKey")
    public CacheVO getCacheByNameAndKey(@RequestParam("cacheName") String cacheName, @RequestParam("cacheKey") String cacheKey) {
        Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            Object value = cache.get(cacheKey);
            return new CacheVO(cacheKey, cacheKey, value);
        }
        return null;
    }
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("monitor:cache:remove")
    @PostMapping("/remove")
    public R<Void> remove(@RequestParam("cacheName") String cacheName, @RequestParam(name = "cacheKey") Serializable cacheKey) {
        Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        boolean removed = cache.remove(cacheKey);
        return R.result(removed);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("monitor:cache:remove")
    @PostMapping("/clear-by-name/{cacheName}")
    public R<Void> clearByName(@PathVariable("cacheName") String cacheName) {
        Cache<Object, Object> cache = cacheManager.getCache(cacheName);
        cache.clear();
        return R.ok();
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("monitor:cache:remove")
    @PostMapping("/clear")
    public R<Void> clear() {
        Collection<String> cacheNames = this.getCacheNames();
        for (String cacheName : cacheNames) {
            Cache<Object, Object> cache = cacheManager.getCache(cacheName);
            cache.clear();
        }
        return R.ok();
    }
}