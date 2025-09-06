package com.github.mengweijin.vita.system.listener;

import com.github.mengweijin.vita.system.domain.entity.ConfigDO;

/**
 *
 * @author mengweijin
 * @since 2025/9/6
 */
public interface ConfigChangeListener {

    /**
     * 支持的配置
     * @return ConfigConst
     */
    String supported();

    /**
     * 要执行的入库实现方法
     * @param config ConfigDO
     */
    void run(ConfigDO config);

}
