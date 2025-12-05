package com.github.mengweijin.vita.framework.propertysource;

import com.github.mengweijin.vita.system.service.ConfigService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 *
 * @author mengweijin
 * @since 2025/9/13
 */
@Component
@AllArgsConstructor
public class DatabasePropertySourceLoader implements ApplicationListener<ApplicationReadyEvent> {

    private ConfigurableEnvironment environment;

    private ConfigService configService;

    DatabasePropertySource databasePropertySource;

    /**
     * 也可以添加 DatabasePropertySource 到最优先位置：
     * environment.getPropertySources().addFirst(databasePropertySource);
     * @param event ApplicationReadyEvent
     */
    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        // 将数据库配置源添加到环境属性源中，优先级高于 application.yml 但低于命令行参数
        // environment.getPropertySources().addAfter(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, databasePropertySource);

        // 最低优先级
        environment.getPropertySources().addLast(databasePropertySource);

        // 初始化后，发布更新事件，动态刷新一次配置值
        configService.publishEnvironmentChangeEvent();
    }
}
