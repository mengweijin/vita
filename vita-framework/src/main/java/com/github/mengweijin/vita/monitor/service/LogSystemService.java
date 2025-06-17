package com.github.mengweijin.vita.monitor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vita.monitor.domain.entity.LogDO;
import com.github.mengweijin.vita.monitor.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.text.StrValidator;
import org.dromara.hutool.core.thread.ThreadUtil;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

/**
 * <p>
 *  Logs Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class LogSystemService extends ServiceImpl<LogMapper, LogDO> {

    private final ExecutorService executorService = ThreadUtil.newSingleExecutor();

    /**
     * 使用单个线程异步执行，以保证日志的插入顺序
     */
    @EventListener
    public void saveAsync(LogDO entity) {
        CompletableFuture.runAsync(() -> this.save(entity), executorService);
    }

    public LambdaQueryWrapper<LogDO> getQueryWrapper(LogDO logDO) {
        LambdaQueryWrapper<LogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(logDO.getId() != null, LogDO::getId, logDO.getId());
        wrapper.eq(StrValidator.isNotBlank(logDO.getLoggerLevel()), LogDO::getLoggerLevel, logDO.getLoggerLevel());
        wrapper.eq(logDO.getCreateBy() != null, LogDO::getCreateBy, logDO.getCreateBy());
        wrapper.eq(logDO.getUpdateBy() != null, LogDO::getUpdateBy, logDO.getUpdateBy());
        wrapper.gt(logDO.getSearchStartTime() != null, LogDO::getCreateTime, logDO.getSearchStartTime());
        wrapper.le(logDO.getSearchEndTime() != null, LogDO::getCreateTime, logDO.getSearchEndTime());
        wrapper.like(StrValidator.isNotBlank(logDO.getStackTrace()), LogDO::getStackTrace, logDO.getStackTrace());
        if (StrUtil.isNotBlank(logDO.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(LogDO::getThreadName, logDO.getKeywords()));
                w.or(w1 -> w1.like(LogDO::getLoggerName, logDO.getKeywords()));
                w.or(w1 -> w1.like(LogDO::getFormattedMessage, logDO.getKeywords()));
            });
        }
        return wrapper;
    }
}
