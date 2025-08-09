package com.github.mengweijin.vita.monitor.service;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.text.StrValidator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.scheduler.ISchedulingTask;
import com.github.mengweijin.vita.framework.scheduler.SchedulingTaskFactory;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.mapper.SchedulingTaskMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Scheduling Task Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class SchedulingTaskService extends CrudRepository<SchedulingTaskMapper, SchedulingTaskDO> {

    private SchedulingTaskFactory schedulingTaskFactory;

    public LambdaQueryWrapper<SchedulingTaskDO> getQueryWrapper(SchedulingTaskDO schedulingTask) {
        LambdaQueryWrapper<SchedulingTaskDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(schedulingTask.getId() != null, SchedulingTaskDO::getId, schedulingTask.getId());
        wrapper.eq(StrValidator.isNotBlank(schedulingTask.getDisabled()), SchedulingTaskDO::getDisabled, schedulingTask.getDisabled());
        wrapper.eq(schedulingTask.getCreateBy() != null, SchedulingTaskDO::getCreateBy, schedulingTask.getCreateBy());
        wrapper.eq(schedulingTask.getUpdateBy() != null, SchedulingTaskDO::getUpdateBy, schedulingTask.getUpdateBy());
        wrapper.gt(schedulingTask.getSearchStartTime() != null, SchedulingTaskDO::getCreateTime, schedulingTask.getSearchStartTime());
        wrapper.le(schedulingTask.getSearchEndTime() != null, SchedulingTaskDO::getCreateTime, schedulingTask.getSearchEndTime());
        if (StrUtil.isNotBlank(schedulingTask.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(SchedulingTaskDO::getName, schedulingTask.getKeywords()));
                w.or(w1 -> w1.like(SchedulingTaskDO::getBeanName, schedulingTask.getKeywords()));
            });
        }
        return wrapper;
    }

    public void run(Long id) {
        SchedulingTaskDO taskDO = this.getById(id);
        String beanName = taskDO.getBeanName();
        ISchedulingTask schedulingTask = schedulingTaskFactory.getSchedulingTask(beanName);
        schedulingTask.execute(id);
    }
}
