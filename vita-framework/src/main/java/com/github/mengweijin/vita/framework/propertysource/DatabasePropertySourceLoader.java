package com.github.mengweijin.vita.framework.propertysource;

import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.properties.VitaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 *
 * @author mengweijin
 * @since 2025/9/13
 */
@Component
public class DatabasePropertySourceLoader implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ConfigurationPropertiesRebinder rebinder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        // 创建并添加 DatabasePropertySource
        DatabasePropertySource databasePropertySource = new DatabasePropertySource(jdbcTemplate);
        // 添加到最优先位置
        environment.getPropertySources().addFirst(databasePropertySource);

        // 将数据库配置源添加到环境属性源中，优先级高于 application.yml 但低于命令行参数
        // environment.getPropertySources().addAfter(
        //         StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
        //         databasePropertySource
        // );

        System.out.println(environment.getProperty("vita.role-code-for-admin"));

        // 触发配置刷新，使 VitaProperties 重新绑定到新的属性值
        rebinder.rebind("vitaProperties");

        System.out.println(SpringUtil.getBean(VitaProperties.class));
        System.out.println("VitaProperties refreshed successfully after adding DatabasePropertySource.");
    }

    @Override
    public boolean supportsAsyncExecution() {
        return false;
    }
}
