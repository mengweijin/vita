package com.github.mengweijin.vita.framework.sse;

import cn.hutool.v7.core.array.ArrayUtil;
import cn.hutool.v7.core.thread.ThreadUtil;
import com.github.mengweijin.vita.framework.cache.CacheFactory;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.ServerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.cache.Cache;
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
public class SseConnector implements InitializingBean {

    private Cache<String, SseEmitter> cache;

    private ExecutorService executorService;

    @Override
    public void afterPropertiesSet() throws Exception {
        cache = CacheFactory.getSseEmitterMessageCache();
        executorService = ThreadUtil.newFixedExecutor(Const.PROCESSORS * 2, "thread-pool-sse-", true);
    }

    /**
     * 注册回调
     * sseEmitter.onCompletion(onCompletion(username, token));
     *
     * @param username username
     * @return SseEmitter
     */
    public SseEmitter connect(String username) {
        // 设置超时时间，0表示不过期。默认30秒
        SseEmitter sseEmitter = new SseEmitter(30_000L);
        sseEmitter.onError(onError(username));
        sseEmitter.onTimeout(onTimeout(username));
        cache.put(username, sseEmitter);
        return sseEmitter;
    }

    private Runnable onTimeout(String username) {
        return () -> cache.remove(username);
    }

    private Consumer<Throwable> onError(String username) {
        return throwable -> {
            cache.remove(username);
            log.error(throwable.getMessage(), throwable);
        };
    }

    public void sendMessage(String message, String... usernames) {
        if (ArrayUtil.isEmpty(usernames)) {
            return;
        }

        for (String username : usernames) {
            CompletableFuture.runAsync(() -> {
                try {
                    SseEmitter sseEmitter = cache.get(username);
                    if (sseEmitter == null) {
                        cache.remove(username);
                    } else {
                        sseEmitter.send(message);
                    }
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                    cache.remove(username);
                    throw new ServerException(e);
                }
            }, executorService);
        }
    }

}
