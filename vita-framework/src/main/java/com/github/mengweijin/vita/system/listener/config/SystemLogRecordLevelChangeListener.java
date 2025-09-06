package com.github.mengweijin.vita.system.listener.config;

import ch.qos.logback.classic.Level;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.logback.DbLoggerAppender;
import com.github.mengweijin.vita.system.constant.ConfigConst;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.listener.ConfigChangeListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author mengweijin
 * @since 2025/9/6
 */
@Component
public class SystemLogRecordLevelChangeListener implements ConfigChangeListener {
    @Override
    public String supported() {
        return ConfigConst.LOG_RECORD_LEVEL;
    }

    @Override
    public void run(ConfigDO config) {
        DbLoggerAppender dbLoggerAppender = SpringUtil.getBean(DbLoggerAppender.class);
        String logLevel = config.getVal();
        // 将字符串转换为 Level 对象（如果为 null 默认记录级别为 Level.ERROR 的日志）
        Level level = Level.toLevel(logLevel, Level.ERROR);
        dbLoggerAppender.setLevel(level);
    }
}
