package com.github.mengweijin.vita.monitor.service;

import cn.hutool.v7.core.text.StrValidator;
import cn.hutool.v7.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.monitor.domain.entity.LogSystemDO;
import com.github.mengweijin.vita.monitor.domain.vo.LogSystemVO;
import com.github.mengweijin.vita.monitor.mapper.LogMapper;
import lombok.extern.slf4j.Slf4j;
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
public class LogSystemService extends BaseVitaService<LogMapper, LogSystemDO, LogSystemVO> {

    private final ExecutorService executorService = ThreadUtil.newSingleExecutor();

    /**
     * 使用单个线程异步执行，以保证日志的插入顺序
     */
    public void saveBySingleExecutorService(LogSystemDO entity) {
        CompletableFuture.runAsync(() -> this.save(entity), executorService);
    }

    @Override
    public LambdaQueryWrapper<LogSystemDO> buildQueryWrapper(LogSystemDO logSystemDO) {
        LambdaQueryWrapper<LogSystemDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(logSystemDO.getId() != null, LogSystemDO::getId, logSystemDO.getId());
        wrapper.eq(StrValidator.isNotBlank(logSystemDO.getLoggerLevel()), LogSystemDO::getLoggerLevel, logSystemDO.getLoggerLevel());
        wrapper.eq(logSystemDO.getCreateBy() != null, LogSystemDO::getCreateBy, logSystemDO.getCreateBy());
        wrapper.eq(logSystemDO.getUpdateBy() != null, LogSystemDO::getUpdateBy, logSystemDO.getUpdateBy());
        wrapper.gt(logSystemDO.getStartCreateTime() != null, LogSystemDO::getCreateTime, logSystemDO.getStartCreateTime());
        wrapper.le(logSystemDO.getEndCreateTime() != null, LogSystemDO::getCreateTime, logSystemDO.getEndCreateTime());
        wrapper.like(StrValidator.isNotBlank(logSystemDO.getThreadName()), LogSystemDO::getThreadName, logSystemDO.getThreadName());
        wrapper.like(StrValidator.isNotBlank(logSystemDO.getLoggerName()), LogSystemDO::getLoggerName, logSystemDO.getLoggerName());
        wrapper.like(StrValidator.isNotBlank(logSystemDO.getFormattedMessage()), LogSystemDO::getFormattedMessage, logSystemDO.getFormattedMessage());
        wrapper.like(StrValidator.isNotBlank(logSystemDO.getStackTrace()), LogSystemDO::getStackTrace, logSystemDO.getStackTrace());
        return wrapper;
    }
}
