package com.github.mengweijin.vita.monitor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vita.monitor.domain.LogDO;
import com.github.mengweijin.vita.monitor.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
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
public class LogsService extends ServiceImpl<LogMapper, LogDO> {

    private final ExecutorService executorService = ThreadUtil.newSingleExecutor();

    /**
     * 使用单个线程异步执行，以保证日志的插入顺序
     */
    @EventListener
    public void saveAsync(LogDO entity) {
        CompletableFuture.runAsync(() -> this.save(entity), executorService);
    }

    /**
     * Custom paging query
     * @param page page
     * @param logDO {@link LogDO}
     * @return IPage
     */
    public IPage<LogDO> page(IPage<LogDO> page, LogDO logDO){
        LambdaQueryWrapper<LogDO> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(logDO.getLoggerLevel()), LogDO::getLoggerLevel, logDO.getLoggerLevel())
                .like(StrValidator.isNotBlank(logDO.getThreadName()), LogDO::getThreadName, logDO.getThreadName())
                .like(StrValidator.isNotBlank(logDO.getLoggerName()), LogDO::getLoggerName, logDO.getLoggerName())
                .like(StrValidator.isNotBlank(logDO.getFormattedMessage()), LogDO::getFormattedMessage, logDO.getFormattedMessage())
                .like(StrValidator.isNotBlank(logDO.getStackTrace()), LogDO::getStackTrace, logDO.getStackTrace());
        return this.page(page, query);
    }
}
