package com.github.mengweijin.vita.framework.propertysource;

import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.service.ConfigService;
import org.springframework.core.env.PropertySource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 * @author mengweijin
 * @since 2025/9/13
 */
@Component
public class DatabasePropertySource extends PropertySource<Map<String, String>> {

    public static final String PROPERTY_SOURCE_NAME = "databasePropertySource";

    private List<ConfigDO> configList = new ArrayList<>();

    public DatabasePropertySource() {
        super(PROPERTY_SOURCE_NAME, new ConcurrentHashMap<>());
        // 初始化加载配置
        refresh();
    }

    @Override
    public Object getProperty(@NonNull String name) {
        return this.getSource().get(name);
    }

    /**
     * 从数据库加载配置到缓存
     */
    public void refresh() {
        ConfigService configService = SpringUtil.getBean(ConfigService.class);
        configList = configService.list();
        Map<String, String> map = configList.stream()
                .collect(Collectors.toMap(ConfigDO::getConfigKey, ConfigDO::getConfigValue));
        // 从数据库更新缓存
        this.getSource().putAll(map);
    }

    public Set<String> getKeys() {
        return configList.stream().map(ConfigDO::getConfigKey).collect(Collectors.toSet());
    }
}
