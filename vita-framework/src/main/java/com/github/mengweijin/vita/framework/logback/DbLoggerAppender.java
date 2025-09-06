package com.github.mengweijin.vita.framework.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.helpers.Transform;
import cn.hutool.v7.core.reflect.ClassUtil;
import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.monitor.domain.entity.LogDO;
import com.github.mengweijin.vita.monitor.mapper.LogMapper;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.enums.EConfig;
import com.github.mengweijin.vita.system.service.ConfigService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

/**
 * No need additional configuration.
 *
 * @author mengweijin
 * @since 2023/4/1
 */
@Slf4j
@Component
public class DbLoggerAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    /**
     * 白名单。这些日志名称的日志不会被记录到数据库。
     */
    private static final String[] LOGGER_NAME_WHITE_LIST = new String[]{
            "p6spy",
            ClassUtil.getClassName(LogMapper.class, false) + ".insert",
    };

    private static final String TAB = StrUtil.fillAfter(Const.EMPTY, ' ', 4);

    @Getter
    @Setter
    private Level level;

    /**
     * DbErrorLogAppender初始化
     */
    @PostConstruct
    @SuppressWarnings({"unused","java:S3252"})
    public void init() {
        initLevel();

        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(DbLoggerAppender.this);
        super.start();
    }

    public void initLevel() {
        ConfigService configService = SpringUtil.getBean(ConfigService.class);
        ConfigDO config = configService.getByCode(EConfig.LOG_RECORD_LEVEL.getValue());
        String logLevel = config.getVal();
        // 将字符串转换为 Level 对象（如果为 null 默认记录级别为 Level.ERROR 的日志）
        level = Level.toLevel(logLevel, Level.ERROR);
    }

    @Override
    protected void append(ILoggingEvent event) {
        // 只有当事件的级别 >= 当前设置的阈值级别时，才记录日志
        if (event.getLevel().isGreaterOrEqual(level)) {
            recordLog(event);
        }
    }

    private void recordLog(ILoggingEvent event) {
        String loggerName = event.getLoggerName();
        boolean anyMatch = Arrays.stream(LOGGER_NAME_WHITE_LIST).anyMatch(i -> i.equalsIgnoreCase(loggerName));
        if (anyMatch) {
            return;
        }

        Long loginUserId = LoginHelper.getLoginUserIdQuietly();
        LocalDateTime createTime = event.getInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        LogDO logDO = new LogDO();
        logDO.setLoggerLevel(event.getLevel().levelStr);
        logDO.setThreadName(event.getThreadName());
        logDO.setLoggerName(loggerName);
        logDO.setFormattedMessage(CharSequenceUtil.subPre(event.getFormattedMessage(), 3000));
        logDO.setStackTrace(getStackTraceMsg(event.getThrowableProxy()));

        logDO.setCreateBy(loginUserId);
        logDO.setUpdateBy(loginUserId);
        logDO.setCreateTime(createTime);
        logDO.setUpdateTime(createTime);

        SpringUtil.publishEvent(logDO);
    }

    /**
     * 拼装堆栈跟踪信息
     */
    private String getStackTraceMsg(IThrowableProxy tp) {
        StringBuilder buf = new StringBuilder();
        if (tp != null) {
            while (tp != null) {
                this.renderStackTrace(buf, tp);
                tp = tp.getCause();
            }
        }

        return buf.toString();
    }

    /**
     * 堆栈跟踪信息拼装成html字符串
     */
    private void renderStackTrace(StringBuilder stringBuilder, IThrowableProxy throwableProxy) {
        this.printFirstLine(stringBuilder, throwableProxy);
        int commonFrames = throwableProxy.getCommonFrames();
        StackTraceElementProxy[] stepArray = throwableProxy.getStackTraceElementProxyArray();

        for (int i = 0; i < stepArray.length - commonFrames; ++i) {
            StackTraceElementProxy step = stepArray[i];
            stringBuilder.append(TAB);
            stringBuilder.append(Transform.escapeTags(step.toString()));
            stringBuilder.append(CoreConstants.LINE_SEPARATOR);
        }

        if (commonFrames > 0) {
            stringBuilder.append(TAB);
            stringBuilder.append("... ").append(commonFrames).append(" common frames omitted").append(CoreConstants.LINE_SEPARATOR);
        }

    }

    /**
     * 拼装堆栈跟踪信息第一行
     */
    public void printFirstLine(StringBuilder sb, IThrowableProxy tp) {
        int commonFrames = tp.getCommonFrames();
        if (commonFrames > 0) {
            sb.append(CoreConstants.LINE_SEPARATOR).append("Caused by: ");
        }
        sb.append(tp.getClassName()).append(": ").append(Transform.escapeTags(tp.getMessage()));
        sb.append(CoreConstants.LINE_SEPARATOR);
    }

}
