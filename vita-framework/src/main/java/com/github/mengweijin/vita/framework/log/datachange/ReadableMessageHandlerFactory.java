package com.github.mengweijin.vita.framework.log.datachange;

import com.github.mengweijin.vita.framework.log.datachange.handler.IReadableMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class ReadableMessageHandlerFactory {

    /**
     * 自定义处理器列表
     */
    private final List<IReadableMessageHandler> humanReadableHandlerList;

    /**
     * 缓存
     */
    private static final Map<String, IReadableMessageHandler> CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 默认处理器
     */
    private static final IReadableMessageHandler DEFAULT_HANDLER = tableName -> true;

    /**
     * 构造函数
     * @param humanReadableHandlerList 自定义处理器列表
     */
    public ReadableMessageHandlerFactory(List<IReadableMessageHandler> humanReadableHandlerList) {
        this.humanReadableHandlerList = humanReadableHandlerList;
    }

    /**
     * 获取处理器
     * @param tableName tableName
     * @return IReadableMessageHandler
     */
    public IReadableMessageHandler getHandler(String tableName) {
        return CACHE_MAP.computeIfAbsent(tableName, k -> {
            for (IReadableMessageHandler handler : humanReadableHandlerList) {
                if(handler.supported(tableName)) {
                    return handler;
                }
            }
            return DEFAULT_HANDLER;
        });
    }
}
