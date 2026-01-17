package com.github.mengweijin.vita.monitor.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.monitor.domain.bo.LogSystemBO;
import com.github.mengweijin.vita.monitor.domain.vo.LogSystemVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
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
@AutoMappers({
        @AutoMapper(target = LogSystemBO.class),
        @AutoMapper(target = LogSystemVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_LOG_SYSTEM")
public class LogSystemDO extends BaseEntity {

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
