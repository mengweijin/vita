package com.github.mengweijin.vita.framework.propertysource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 监听器实现的两种方式：
 * 1. 方法（参数为 ApplicationEvent 对象）上添加 @EventListener 注解。
 * 2. 实现 ApplicationListener 接口
 *
 * @author mengweijin
 * @since 2025/9/14
 */
@Slf4j
@Component
public class EnvironmentChangeListener implements ApplicationListener<EnvironmentChangeEvent> {

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event) {
        // 获取发生变化的属性键
        Set<String> keys = event.getKeys();
        log.debug("Configuration changed for keys: {}", keys);
        // 这里可以添加你的自定义处理逻辑，例如重新初始化某些资源
    }
}
