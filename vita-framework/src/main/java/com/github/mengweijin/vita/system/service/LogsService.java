package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vita.system.domain.entity.Logs;
import com.github.mengweijin.vita.system.mapper.LogsMapper;
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
public class LogsService extends ServiceImpl<LogsMapper, Logs> {

    private final ExecutorService executorService = ThreadUtil.newSingleExecutor();

    /**
     * 使用单个线程异步执行，以保证日志的插入顺序
     */
    @EventListener
    public void saveAsync(Logs entity) {
        CompletableFuture.runAsync(() -> this.save(entity), executorService);
    }

    /**
     * Custom paging query
     * @param page page
     * @param logs {@link Logs}
     * @return IPage
     */
    public IPage<Logs> page(IPage<Logs> page, Logs logs){
        LambdaQueryWrapper<Logs> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(logs.getLoggerLevel()), Logs::getLoggerLevel, logs.getLoggerLevel())
                .like(StrValidator.isNotBlank(logs.getThreadName()), Logs::getThreadName, logs.getThreadName())
                .like(StrValidator.isNotBlank(logs.getLoggerName()), Logs::getLoggerName, logs.getLoggerName())
                .like(StrValidator.isNotBlank(logs.getFormattedMessage()), Logs::getFormattedMessage, logs.getFormattedMessage())
                .like(StrValidator.isNotBlank(logs.getStackTrace()), Logs::getStackTrace, logs.getStackTrace());
        return this.page(page, query);
    }
}
