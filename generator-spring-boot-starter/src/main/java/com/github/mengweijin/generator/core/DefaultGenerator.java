package com.github.mengweijin.generator.core;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;

import javax.sql.DataSource;

/**
 * @author mengweijin
 * @date 2022/8/16
 */
public class DefaultGenerator extends AutoGenerator {

    public DefaultGenerator(String url, String username, String password) {
        super(new DataSourceConfig.Builder(url, username, password).build());
    }

    public DefaultGenerator(DataSource dataSource) {
        super(new DataSourceConfig.Builder(dataSource).build());
    }
    /**
     * 构造方法
     *
     * @param dataSourceConfig 数据库配置
     * @since 3.5.0
     */
    public DefaultGenerator(DataSourceConfig dataSourceConfig) {
        super(dataSourceConfig);
    }

    @Override
    public ConfigBuilder getConfig() {
        return new ConfigBuilder(this.getPackageInfo(), this.getDataSource(), this.getStrategy(), this.getTemplate(), this.getGlobalConfig(), this.injection);
    }
}
