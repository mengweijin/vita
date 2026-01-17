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
import com.github.mengweijin.vita.framework.properties.VitaProperties;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.monitor.domain.entity.LogSystemDO;
import com.github.mengweijin.vita.monitor.mapper.LogMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class DbLoggerAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    /**
     * 白名单。这些日志名称的日志不会被记录到数据库。
     */
    private static final String[] LOGGER_NAME_WHITE_LIST = new String[]{
            "p6spy",
            ClassUtil.getClassName(LogMapper.class, false) + ".insert",
    };

    private static final String TAB = StrUtil.fillAfter(Const.EMPTY, ' ', 4);

    private VitaProperties vitaProperties;

    /**
     * DbLoggerAppender 初始化
     */
    @PostConstruct
    @SuppressWarnings({"unused","java:S3252"})
    public void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        Logger logger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        logger.addAppender(DbLoggerAppender.this);
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
        String logRecordLevel = vitaProperties.getLogRecordLevel();
        Level level = Level.toLevel(logRecordLevel, Level.ERROR);
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

        Long loginUserId = LoginHelper.getSessionUserId();
        LocalDateTime createTime = event.getInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        LogSystemDO logSystemDO = new LogSystemDO();
        logSystemDO.setLoggerLevel(event.getLevel().levelStr);
        logSystemDO.setThreadName(event.getThreadName());
        logSystemDO.setLoggerName(loggerName);
        logSystemDO.setFormattedMessage(CharSequenceUtil.subPre(event.getFormattedMessage(), 3000));
        logSystemDO.setStackTrace(getStackTraceMsg(event.getThrowableProxy()));

        logSystemDO.setCreateBy(loginUserId);
        logSystemDO.setUpdateBy(loginUserId);
        logSystemDO.setCreateTime(createTime);
        logSystemDO.setUpdateTime(createTime);

        SpringUtil.publishEvent(logSystemDO);
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
     * 堆栈跟踪信息拼装成 html 字符串
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
