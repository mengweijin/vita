package com.github.mengweijin.vita.framework.propertysource;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;

/**
 *
 * @author mengweijin
 * @since 2025/9/13
 */
@Configuration
@AllArgsConstructor
public class PropertySourceConfig {

    private ConfigurableEnvironment env;

    private DatabasePropertySource databasePropertySource;

    @PostConstruct
    public void init() {
        MutablePropertySources sources = env.getPropertySources();
        sources.addFirst(databasePropertySource);
    }

}
