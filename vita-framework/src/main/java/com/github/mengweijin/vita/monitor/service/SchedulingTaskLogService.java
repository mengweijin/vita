package com.github.mengweijin.vita.monitor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskLogDO;
import com.github.mengweijin.vita.monitor.mapper.SchedulingTaskLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Scheduling Task Log Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class SchedulingTaskLogService extends CrudRepository<SchedulingTaskLogMapper, SchedulingTaskLogDO> {

    public LambdaQueryWrapper<SchedulingTaskLogDO> getQueryWrapper(SchedulingTaskLogDO taskLog) {
        LambdaQueryWrapper<SchedulingTaskLogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(taskLog.getId() != null, SchedulingTaskLogDO::getId, taskLog.getId());
        wrapper.eq(taskLog.getSchedulingTaskId() != null, SchedulingTaskLogDO::getSchedulingTaskId, taskLog.getSchedulingTaskId());
        wrapper.eq(StrUtil.isNotBlank(taskLog.getSuccess()), SchedulingTaskLogDO::getSuccess, taskLog.getSuccess());
        wrapper.eq(taskLog.getCreateBy() != null, SchedulingTaskLogDO::getCreateBy, taskLog.getCreateBy());
        wrapper.eq(taskLog.getUpdateBy() != null, SchedulingTaskLogDO::getUpdateBy, taskLog.getUpdateBy());
        wrapper.gt(taskLog.getSearchStartTime() != null, SchedulingTaskLogDO::getCreateTime, taskLog.getSearchStartTime());
        wrapper.le(taskLog.getSearchEndTime() != null, SchedulingTaskLogDO::getCreateTime, taskLog.getSearchEndTime());
        return wrapper;
    }
}
