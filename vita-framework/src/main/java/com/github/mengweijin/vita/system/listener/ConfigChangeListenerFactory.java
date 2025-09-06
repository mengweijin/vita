package com.github.mengweijin.vita.system.listener;

import jakarta.annotation.PostConstruct;
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
public class ConfigChangeListenerFactory {

    private final List<ConfigChangeListener> configChangeListenerList;

    public ConfigChangeListenerFactory(List<ConfigChangeListener> configChangeListenerList) {
        this.configChangeListenerList = configChangeListenerList;
    }

    private static final Map<String, ConfigChangeListener> LISTENER_MAP = new ConcurrentHashMap<>();

    @SuppressWarnings({"unused"})
    @PostConstruct
    public void init() {
        for (ConfigChangeListener listener : configChangeListenerList) {
            if(listener.supported() == null) {
                log.warn("{} : was not set supported!", listener.getClass().getName());
            }
            LISTENER_MAP.put(listener.supported(), listener);
        }
    }

    public static ConfigChangeListener getConfigChangeListenerByCode(String code) {
        return LISTENER_MAP.get(code);
    }
}
