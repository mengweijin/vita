package com.github.mengweijin.vita.monitor.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_LOG")
public class LogDO extends BaseEntity {

    /**
     * 日志级别
     */
    private String loggerLevel;

    /**
     * 线程名称
     */
    private String threadName;

    /**
     * 日志名称。java 类名
     */
    private String loggerName;

    /**
     * 格式化后的日志信息
     */
    private String formattedMessage;

    /**
     * stack trace
     */
    private String stackTrace;

}
