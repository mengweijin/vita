package com.github.mengweijin.vita.framework.ratelimit.impl;

import cn.hutool.v7.http.server.servlet.ServletUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.ratelimit.ERateLimitStrategy;
import com.github.mengweijin.vita.framework.ratelimit.RateLimit;
import com.github.mengweijin.vita.framework.util.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.List;

/**
 * 限流处理
 * @author mengweijin
 */
@Slf4j
// @Aspect
// @Component
public class RedisCacheRateLimiterAspect {

    private static final String CACHE_NAME_PREFIX = "RATE_LIMIT_";

    private RedisTemplate<Object, Object> redisTemplate;

    private RedisScript<Long> limitScript;

    @Autowired
    public void setRedisTemplate(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setLimitScript(RedisScript<Long> limitScript) {
        this.limitScript = limitScript;
    }

    @Before("@annotation(rateLimit)")
    public void doBefore(JoinPoint point, RateLimit rateLimit) throws Throwable {
        int time = rateLimit.duration();
        int count = rateLimit.max();

        String combineKey = getCacheKey(rateLimit.strategy(), point);
        List<Object> keys = Collections.singletonList(combineKey);
        try {
            Long number = redisTemplate.execute(limitScript, keys, count, time);
            if (number.intValue() > count) {
                throw new ClientException(rateLimit.message());
            }
            log.info("Request Limits '{}', Current Request '{}', Cache Key '{}'", count, number.intValue(), combineKey);
        } catch (ClientException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Traffic limiting on the server is abnormal. Please try again later.");
        }
    }

    public String getCacheKey(ERateLimitStrategy strategy, JoinPoint joinPoint) {
        String cacheKey = CACHE_NAME_PREFIX + strategy.name() + Const.UNDERSCORE;
        HttpServletRequest request = ServletUtils.getRequest();

        if(strategy == ERateLimitStrategy.API) {
            cacheKey += joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()";
        } else if(strategy == ERateLimitStrategy.IP) {
            cacheKey += ServletUtil.getClientIP(request);
        }
        return cacheKey;
    }
}
