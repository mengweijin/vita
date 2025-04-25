package com.github.mengweijin.vita.monitor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vita.monitor.domain.LogOperationDO;
import com.github.mengweijin.vita.monitor.mapper.LogOperationMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 *  LogOperation Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class LogOperationService extends ServiceImpl<LogOperationMapper, LogOperationDO> {

    @Async
    @EventListener
    public void saveAsync(LogOperationDO entity) {
        this.save(entity);
    }

    /**
     * Custom paging query
     * @param page page
     * @param logOperation {@link LogOperationDO}
     * @return IPage
     */
    public IPage<LogOperationDO> page(IPage<LogOperationDO> page, LogOperationDO logOperation){
        LambdaQueryWrapper<LogOperationDO> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(logOperation.getOperationType()), LogOperationDO::getOperationType, logOperation.getOperationType())
                .eq(StrValidator.isNotBlank(logOperation.getHttpMethod()), LogOperationDO::getHttpMethod, logOperation.getHttpMethod())
                .eq(StrValidator.isNotBlank(logOperation.getSuccess()), LogOperationDO::getSuccess, logOperation.getSuccess())
                .eq(StrValidator.isNotBlank(logOperation.getErrorMsg()), LogOperationDO::getErrorMsg, logOperation.getErrorMsg())
                .eq(!Objects.isNull(logOperation.getId()), LogOperationDO::getId, logOperation.getId())
                .eq(!Objects.isNull(logOperation.getCreateBy()), LogOperationDO::getCreateBy, logOperation.getCreateBy())
                .eq(!Objects.isNull(logOperation.getCreateTime()), LogOperationDO::getCreateTime, logOperation.getCreateTime())
                .eq(!Objects.isNull(logOperation.getUpdateBy()), LogOperationDO::getUpdateBy, logOperation.getUpdateBy())
                .eq(!Objects.isNull(logOperation.getUpdateTime()), LogOperationDO::getUpdateTime, logOperation.getUpdateTime())
                .like(StrValidator.isNotBlank(logOperation.getTitle()), LogOperationDO::getTitle, logOperation.getTitle())
                .like(StrValidator.isNotBlank(logOperation.getUrl()), LogOperationDO::getUrl, logOperation.getUrl())
                .like(StrValidator.isNotBlank(logOperation.getMethodName()), LogOperationDO::getMethodName, logOperation.getMethodName());
        return this.page(page, query);
    }
}
