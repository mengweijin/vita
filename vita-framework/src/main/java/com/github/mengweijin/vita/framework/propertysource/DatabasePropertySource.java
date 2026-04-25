package com.github.mengweijin.vita.framework.propertysource;

import com.github.mengweijin.vita.framework.jdbc.template.BeanPropertyNameRowMapper;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import lombok.EqualsAndHashCode;
import org.springframework.core.env.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mengweijin
 * @since 2025/9/13
 */
@Component
@EqualsAndHashCode(callSuper = true)
public class DatabasePropertySource extends PropertySource<Map<String, String>> {

    public static final String PROPERTY_SOURCE_NAME = "databasePropertySource";

    private final JdbcTemplate jdbcTemplate;

    public DatabasePropertySource(JdbcTemplate jdbcTemplate) {
        super(PROPERTY_SOURCE_NAME, new ConcurrentHashMap<>());
        this.jdbcTemplate = jdbcTemplate;
        // 初始化
        refresh();
    }

    @Override
    public Object getProperty(@NonNull String name) {
        return source.get(name);
    }

    public void refresh() {
        String sql = "select CONFIG_KEY, CONFIG_VALUE from VT_CONFIG";
        BeanPropertyNameRowMapper<ConfigDO> rowMapper = new BeanPropertyNameRowMapper<>(ConfigDO.class);
        List<ConfigDO> list = jdbcTemplate.query(sql, rowMapper);
        Map<String, String> map = new HashMap<>(list.size());
        list.forEach(i -> map.put(i.getConfigKey(), i.getConfigValue()));

        source.clear();
        source.putAll(map);
    }

    public Set<String> getAllKeys() {
        return source.keySet();
    }
}
