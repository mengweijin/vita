package com.github.mengweijin.vita.framework.logback;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.filter.ThresholdFilter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import ch.qos.logback.core.helpers.Transform;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.monitor.domain.LogDO;
import com.github.mengweijin.vita.monitor.mapper.LogMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
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
            "org.springframework.web.servlet.DispatcherServlet"
    };

    private static final String TAB = StrUtil.fillAfter(Const.EMPTY, ' ', 4);

    /**
     * DbErrorLogAppender初始化
     */
    @PostConstruct
    @SuppressWarnings({"unused"})
    public void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        ThresholdFilter filter = new ThresholdFilter();
        filter.setLevel(Level.INFO.levelStr);
        filter.setContext(context);
        filter.start();
        this.addFilter(filter);
        this.setContext(context);

        context.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(DbLoggerAppender.this);
        super.start();
    }

    @Override
    protected void append(ILoggingEvent event) {
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
