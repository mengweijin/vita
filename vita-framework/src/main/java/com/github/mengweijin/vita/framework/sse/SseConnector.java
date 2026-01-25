package com.github.mengweijin.vita.framework.sse;

import cn.hutool.v7.core.array.ArrayUtil;
import cn.hutool.v7.core.thread.ThreadUtil;
import com.github.mengweijin.vita.framework.cache.CacheFactory;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * <p>
 * SSE Connector
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Component
public class SseConnector {

    private final ExecutorService executorService = ThreadUtil.newFixedExecutor(Const.PROCESSORS * 2, "thread-pool-sse-", true);

    /**
     * 注册回调
     * sseEmitter.onCompletion(onCompletion(username, token));
     *
     * @param userId userId
     * @return SseEmitter
     */
    public SseEmitter connect(Long userId) {
        Cache cache = CacheFactory.getSseEmitterMessageCache();
        SseEmitter sseEmitter = cache.get(userId, SseEmitter.class);
        if(sseEmitter != null) {
            // 如果存在旧的连接，重新 put 以刷新过期时间
            cache.put(userId, sseEmitter);
            return sseEmitter;
        }

        // 设置超时时间，0表示用不过期。
        sseEmitter = new SseEmitter(0L);
        sseEmitter.onCompletion(() -> cache.evictIfPresent(userId));
        sseEmitter.onTimeout(() -> cache.evictIfPresent(userId));
        sseEmitter.onError(onError(userId));

        // 保存新连接
        cache.put(userId, sseEmitter);
        return sseEmitter;
    }

    public void disconnect(Long userId) {
        Cache cache = CacheFactory.getSseEmitterMessageCache();
        SseEmitter sseEmitter = cache.get(userId, SseEmitter.class);
        if (sseEmitter != null) {
            sseEmitter.complete();
            cache.evictIfPresent(userId);
        }
    }

    public void sendMessage(String message, Long... userIds) {
        Cache cache = CacheFactory.getSseEmitterMessageCache();
        if (ArrayUtil.isEmpty(userIds)) {
            return;
        }

        for (Long userId : userIds) {
            CompletableFuture.runAsync(() -> {
                try {
                    SseEmitter sseEmitter = cache.get(userId, SseEmitter.class);
                    if (sseEmitter == null) {
                        cache.evictIfPresent(userId);
                    } else {
                        sseEmitter.send(message);
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    cache.evictIfPresent(userId);
                    throw new ServerException(e);
                }
            }, executorService);
        }
    }

    private Consumer<Throwable> onError(Long userId) {
        Cache cache = CacheFactory.getSseEmitterMessageCache();
        return throwable -> {
            cache.evictIfPresent(userId);
            log.error(throwable.getMessage(), throwable);
        };
    }

}
