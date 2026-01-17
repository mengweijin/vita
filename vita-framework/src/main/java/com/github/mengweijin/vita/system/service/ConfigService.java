package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.array.ArrayUtil;
import cn.hutool.v7.core.collection.set.SetUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.propertysource.DatabasePropertySource;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.domain.vo.ConfigVO;
import com.github.mengweijin.vita.system.mapper.ConfigMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Set;

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
public class ConfigService extends BaseVitaService<ConfigMapper, ConfigDO, ConfigVO> {

    private ApplicationContext applicationContext;

    @Override
    public boolean save(ConfigDO entity) {
        boolean saved = super.save(entity);
        if(saved) {
            // 发布配置更新事件以动态刷新配置
            this.publishEnvironmentChangeEvent(entity.getConfigKey());
        }
        return saved;
    }

    @Override
    public boolean updateById(ConfigDO config) {
        boolean updated = super.updateById(config);
        if(updated) {
            ConfigDO latestConfig = this.getById(config.getId());
            // 发布配置更新事件以动态刷新配置
            this.publishEnvironmentChangeEvent(latestConfig.getConfigKey());
        }
        return updated;
    }

    @Override
    public LambdaQueryWrapper<ConfigDO> buildQueryWrapper(ConfigDO config) {
        LambdaQueryWrapper<ConfigDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(config.getId() != null, ConfigDO::getId, config.getId());
        wrapper.eq(config.getCreateBy() != null, ConfigDO::getCreateBy, config.getCreateBy());
        wrapper.eq(config.getUpdateBy() != null, ConfigDO::getUpdateBy, config.getUpdateBy());
        wrapper.gt(config.getStartCreateTime() != null, ConfigDO::getCreateTime, config.getStartCreateTime());
        wrapper.le(config.getEndCreateTime() != null, ConfigDO::getCreateTime, config.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(config.getConfigKey()), ConfigDO::getConfigKey, config.getConfigKey());
        wrapper.like(StrUtil.isNotBlank(config.getConfigValue()), ConfigDO::getConfigValue, config.getConfigValue());
        wrapper.like(StrUtil.isNotBlank(config.getRemark()), ConfigDO::getRemark, config.getRemark());
        return wrapper;
    }

    public ConfigDO getByConfigKey(String key) {
        return this.lambdaQuery().eq(ConfigDO::getConfigKey, key).one();
    }

    public void publishEnvironmentChangeEvent(String... keys) {
        DatabasePropertySource databasePropertySource = SpringUtil.getBean(DatabasePropertySource.class);
        // 更新数据库配置缓存
        databasePropertySource.refresh();

        Set<String> changeKeys = SetUtil.of(keys);
        if(ArrayUtil.isEmpty(changeKeys)) {
            changeKeys = databasePropertySource.getAllKeys();
        }

        // 发布事件
        applicationContext.publishEvent(new EnvironmentChangeEvent(applicationContext, changeKeys));
    }
}
