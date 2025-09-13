package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.propertysource.DatabasePropertySource;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.mapper.ConfigMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Config Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class ConfigService extends CrudRepository<ConfigMapper, ConfigDO> {

    private ApplicationEventPublisher applicationEventPublisher;

    private ContextRefresher contextRefresher;

    @Override
    public boolean save(ConfigDO entity) {
        boolean saved = super.save(entity);
        if(saved) {
            // 发布配置更新事件以动态刷新配置
            this.publishEnvironmentChangeEvent();
        }
        return saved;
    }

    @Override
    public boolean updateById(ConfigDO config) {
        boolean updated = super.updateById(config);
        if(updated) {
            ConfigDO latestConfig = this.getById(config.getId());
            // 发布配置更新事件以动态刷新配置
            this.publishEnvironmentChangeEvent();
        }
        return updated;
    }

    public LambdaQueryWrapper<ConfigDO> getQueryWrapper(ConfigDO config) {
        LambdaQueryWrapper<ConfigDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(config.getId() != null, ConfigDO::getId, config.getId());
        wrapper.eq(config.getCreateBy() != null, ConfigDO::getCreateBy, config.getCreateBy());
        wrapper.eq(config.getUpdateBy() != null, ConfigDO::getUpdateBy, config.getUpdateBy());
        wrapper.gt(config.getSearchStartTime() != null, ConfigDO::getCreateTime, config.getSearchStartTime());
        wrapper.le(config.getSearchEndTime() != null, ConfigDO::getCreateTime, config.getSearchEndTime());
        if (StrUtil.isNotBlank(config.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(ConfigDO::getConfigKey, config.getKeywords()));
                w.or(w1 -> w1.like(ConfigDO::getConfigValue, config.getKeywords()));
                w.or(w1 -> w1.like(ConfigDO::getRemark, config.getKeywords()));
            });
        }
        return wrapper;
    }

    public ConfigDO getByConfigKey(String key) {
        return this.lambdaQuery().eq(ConfigDO::getConfigKey, key).one();
    }

    public void publishEnvironmentChangeEvent() {
        DatabasePropertySource databasePropertySource = SpringUtil.getBean(DatabasePropertySource.class);
        // 更新数据库配置缓存
        databasePropertySource.refresh();
        // Set<String> keys = databasePropertySource.getKeys();

        // 发布事件
        // applicationEventPublisher.publishEvent(new EnvironmentChangeEvent(this, SetUtil.of(keys)));

        // 触发Spring上下文刷新，更新 @RefreshScope 注解的 bean
        // contextRefresher.refresh();

        ConfigurationPropertiesRebinder rebinder = SpringUtil.getBean(ConfigurationPropertiesRebinder.class);
        // rebinder.rebind(VitaProperties.class);
        rebinder.rebind("vitaProperties");
    }
}
