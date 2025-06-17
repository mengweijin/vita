package com.github.mengweijin.vita.monitor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vita.monitor.domain.entity.LogOperationDO;
import com.github.mengweijin.vita.monitor.mapper.LogOperationMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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

    public LambdaQueryWrapper<LogOperationDO> getQueryWrapper(LogOperationDO logOperation) {
        LambdaQueryWrapper<LogOperationDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(logOperation.getId() != null, LogOperationDO::getId, logOperation.getId());

        wrapper.eq(StrValidator.isNotBlank(logOperation.getOperationType()), LogOperationDO::getOperationType, logOperation.getOperationType());
        wrapper.eq(StrValidator.isNotBlank(logOperation.getHttpMethod()), LogOperationDO::getHttpMethod, logOperation.getHttpMethod());
        wrapper.eq(StrValidator.isNotBlank(logOperation.getSuccess()), LogOperationDO::getSuccess, logOperation.getSuccess());
        wrapper.eq(StrValidator.isNotBlank(logOperation.getErrorMsg()), LogOperationDO::getErrorMsg, logOperation.getErrorMsg());

        wrapper.eq(logOperation.getCreateBy() != null, LogOperationDO::getCreateBy, logOperation.getCreateBy());
        wrapper.eq(logOperation.getUpdateBy() != null, LogOperationDO::getUpdateBy, logOperation.getUpdateBy());
        wrapper.gt(logOperation.getSearchStartTime() != null, LogOperationDO::getCreateTime, logOperation.getSearchStartTime());
        wrapper.le(logOperation.getSearchEndTime() != null, LogOperationDO::getCreateTime, logOperation.getSearchEndTime());
        if (StrUtil.isNotBlank(logOperation.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(LogOperationDO::getTitle, logOperation.getKeywords()));
                w.or(w1 -> w1.like(LogOperationDO::getUrl, logOperation.getKeywords()));
                w.or(w1 -> w1.like(LogOperationDO::getMethodName, logOperation.getKeywords()));
            });
        }
        return wrapper;
    }
}
