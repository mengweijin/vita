package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vita.system.domain.entity.LogOperation;
import com.github.mengweijin.vita.system.mapper.LogOperationMapper;
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
public class LogOperationService extends ServiceImpl<LogOperationMapper, LogOperation> {

    @Async
    @EventListener
    public void saveAsync(LogOperation entity) {
        this.save(entity);
    }

    /**
     * Custom paging query
     * @param page page
     * @param logOperation {@link LogOperation}
     * @return IPage
     */
    public IPage<LogOperation> page(IPage<LogOperation> page, LogOperation logOperation){
        LambdaQueryWrapper<LogOperation> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(logOperation.getOperationType()), LogOperation::getOperationType, logOperation.getOperationType())
                .eq(StrValidator.isNotBlank(logOperation.getHttpMethod()), LogOperation::getHttpMethod, logOperation.getHttpMethod())
                .eq(StrValidator.isNotBlank(logOperation.getSuccess()), LogOperation::getSuccess, logOperation.getSuccess())
                .eq(StrValidator.isNotBlank(logOperation.getErrorMsg()), LogOperation::getErrorMsg, logOperation.getErrorMsg())
                .eq(!Objects.isNull(logOperation.getId()), LogOperation::getId, logOperation.getId())
                .eq(!Objects.isNull(logOperation.getCreateBy()), LogOperation::getCreateBy, logOperation.getCreateBy())
                .eq(!Objects.isNull(logOperation.getCreateTime()), LogOperation::getCreateTime, logOperation.getCreateTime())
                .eq(!Objects.isNull(logOperation.getUpdateBy()), LogOperation::getUpdateBy, logOperation.getUpdateBy())
                .eq(!Objects.isNull(logOperation.getUpdateTime()), LogOperation::getUpdateTime, logOperation.getUpdateTime())
                .like(StrValidator.isNotBlank(logOperation.getTitle()), LogOperation::getTitle, logOperation.getTitle())
                .like(StrValidator.isNotBlank(logOperation.getUrl()), LogOperation::getUrl, logOperation.getUrl())
                .like(StrValidator.isNotBlank(logOperation.getMethodName()), LogOperation::getMethodName, logOperation.getMethodName());
        return this.page(page, query);
    }
}
