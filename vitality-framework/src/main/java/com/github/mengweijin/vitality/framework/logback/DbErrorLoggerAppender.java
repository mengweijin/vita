package com.github.mengweijin.vitality.framework.logback;

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
import com.github.mengweijin.vitality.system.entity.VtlErrorLog;
import com.github.mengweijin.vitality.system.service.VtlErrorLogService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

/**
 * No need additional configuration.
 * @author mengweijin
 * @date 2023/4/1
 */
@Slf4j
@Component
public class DbErrorLoggerAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    @Autowired
    private VtlErrorLogService vtlErrorLogService;

    /**
     * DbErrorLogAppender初始化
     */
    @PostConstruct
    public void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        ThresholdFilter filter = new ThresholdFilter();
        filter.setLevel(Level.ERROR.levelStr);
        filter.setContext(context);
        filter.start();
        this.addFilter(filter);
        this.setContext(context);

        context.getLogger(Logger.ROOT_LOGGER_NAME).addAppender(DbErrorLoggerAppender.this);
        super.start();
    }

    @Override
    protected void append(ILoggingEvent loggingEvent) {
        CompletableFuture.runAsync(() -> {
            try {
                LocalDateTime createTime = Instant.ofEpochMilli(loggingEvent.getTimeStamp()).atZone(ZoneId.systemDefault()).toLocalDateTime();
                IThrowableProxy throwableProxy = loggingEvent.getThrowableProxy();

                VtlErrorLog vtlErrorLog = new VtlErrorLog();
                if (loggingEvent.getCallerData() != null && loggingEvent.getCallerData().length > 0) {
                    StackTraceElement element = loggingEvent.getCallerData()[0];
                    vtlErrorLog.setClassName(element.getClassName());
                    vtlErrorLog.setMethodName(element.getMethodName());
                }

                if (throwableProxy != null) {
                    vtlErrorLog.setExceptionName(throwableProxy.getClassName());
                    vtlErrorLog.setStackTrace(this.getStackTraceMsg(throwableProxy));
                }
                vtlErrorLog.setErrorMsg(loggingEvent.getMessage());
                vtlErrorLog.setCreateTime(createTime);

                // 错误日志实体类异步写入数据库
                vtlErrorLogService.save(vtlErrorLog);
            } catch (RuntimeException e) {
                this.addError("Record error log to database failed! " + e.getMessage());
            }
        });
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
            stringBuilder.append("<br />&nbsp;&nbsp;&nbsp;&nbsp;");
            stringBuilder.append(Transform.escapeTags(step.toString()));
            stringBuilder.append(CoreConstants.LINE_SEPARATOR);
        }

        if (commonFrames > 0) {
            stringBuilder.append("<br />&nbsp;&nbsp;&nbsp;&nbsp;");
            stringBuilder.append("\t... ").append(commonFrames).append(" common frames omitted").append(CoreConstants.LINE_SEPARATOR);
        }

    }

    /**
     * 拼装堆栈跟踪信息第一行
     */
    public void printFirstLine(StringBuilder sb, IThrowableProxy tp) {
        int commonFrames = tp.getCommonFrames();
        if (commonFrames > 0) {
            sb.append("<br />").append("Caused by: ");
        }

        sb.append(tp.getClassName()).append(": ").append(Transform.escapeTags(tp.getMessage()));
        sb.append(CoreConstants.LINE_SEPARATOR);
    }

}
