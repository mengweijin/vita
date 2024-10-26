package com.github.mengweijin.vitality.framework.sse;

import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vitality.framework.cache.CacheManagerFactory;
import com.github.mengweijin.vitality.framework.constant.Const;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.array.ArrayUtil;
import org.dromara.hutool.core.thread.ThreadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.cache.Cache;
import java.io.IOException;
import java.util.List;
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
@Service
public class SseConnector {

    private static final Cache<String, SseEmitter> CACHE = CacheManagerFactory.getSseEmitterMessageCache();

    private static final ExecutorService SSE_EXECUTOR_SERVICE = ThreadUtil.newFixedExecutor(Const.PROCESSORS, "thread-pool-sse-", true);

    public void sendMessageToUsersAsync(String content, String... usernames) {
        if (ArrayUtil.isEmpty(usernames)) {
            return;
        }
        CompletableFuture.runAsync(() -> {
            try {
                for (String username : usernames) {
                    List<String> tokenList = StpUtil.getTokenValueListByLoginId(username);
                    for (String token : tokenList) {
                        SseEmitter sseEmitter = CACHE.get(token);
                        if (sseEmitter != null) {
                            sseEmitter.send(content);
                        }
                    }
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }, SSE_EXECUTOR_SERVICE);
    }

    /**
     * 注册回调
     * sseEmitter.onCompletion(onCompletion(username, token));
     *
     * @param token token
     * @return SseEmitter
     */
    public SseEmitter connect(String token) {
        // 设置超时时间，0表示不过期。默认30秒
        SseEmitter sseEmitter = new SseEmitter(0L);
        sseEmitter.onError(onError(token));
        sseEmitter.onTimeout(onTimeout(token));
        CACHE.put(token, sseEmitter);
        return sseEmitter;
    }

    private Runnable onTimeout(String token) {
        return () -> {
            CACHE.remove(token);
            log.warn("SseEmitter timeout!");
        };
    }

    private Consumer<Throwable> onError(String token) {
        return throwable -> {
            CACHE.remove(token);
            log.error(throwable.getMessage(), throwable);
        };
    }
}
