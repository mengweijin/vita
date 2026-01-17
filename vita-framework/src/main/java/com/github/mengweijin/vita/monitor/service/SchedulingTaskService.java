package com.github.mengweijin.vita.monitor.service;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.text.StrValidator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.scheduler.ISchedulingTask;
import com.github.mengweijin.vita.framework.scheduler.SchedulingTaskFactory;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.domain.vo.SchedulingTaskVO;
import com.github.mengweijin.vita.monitor.mapper.SchedulingTaskMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

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
public class SchedulingTaskService extends BaseVitaService<SchedulingTaskMapper, SchedulingTaskDO, SchedulingTaskVO> {

    private SchedulingTaskFactory schedulingTaskFactory;

    @Override
    public LambdaQueryWrapper<SchedulingTaskDO> buildQueryWrapper(SchedulingTaskDO schedulingTask) {
        LambdaQueryWrapper<SchedulingTaskDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(schedulingTask.getId() != null, SchedulingTaskDO::getId, schedulingTask.getId());
        wrapper.eq(StrValidator.isNotBlank(schedulingTask.getExecuteAfterStarted()), SchedulingTaskDO::getExecuteAfterStarted, schedulingTask.getExecuteAfterStarted());
        wrapper.eq(StrValidator.isNotBlank(schedulingTask.getDisabled()), SchedulingTaskDO::getDisabled, schedulingTask.getDisabled());
        wrapper.eq(schedulingTask.getCreateBy() != null, SchedulingTaskDO::getCreateBy, schedulingTask.getCreateBy());
        wrapper.eq(schedulingTask.getUpdateBy() != null, SchedulingTaskDO::getUpdateBy, schedulingTask.getUpdateBy());
        wrapper.gt(schedulingTask.getStartCreateTime() != null, SchedulingTaskDO::getCreateTime, schedulingTask.getStartCreateTime());
        wrapper.le(schedulingTask.getEndCreateTime() != null, SchedulingTaskDO::getCreateTime, schedulingTask.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(schedulingTask.getName()), SchedulingTaskDO::getName, schedulingTask.getName());
        wrapper.like(StrUtil.isNotBlank(schedulingTask.getBeanName()), SchedulingTaskDO::getBeanName, schedulingTask.getBeanName());
        return wrapper;
    }

    public void run(Long id) {
        SchedulingTaskDO taskDO = this.getById(id);
        String beanName = taskDO.getBeanName();
        ISchedulingTask schedulingTask = schedulingTaskFactory.getSchedulingTask(beanName);
        schedulingTask.execute(id);
    }

    public Set<String> getTaskBeanNames() {
        return schedulingTaskFactory.getSchedulingTaskMap().keySet();
    }
}
