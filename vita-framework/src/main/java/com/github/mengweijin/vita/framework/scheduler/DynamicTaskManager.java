package com.github.mengweijin.vita.framework.scheduler;

import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mengweijin
 * @since 2025/6/22
 */
@Slf4j
public class DynamicTaskManager {

    private final Map<Long, ScheduledTask> taskMap = new ConcurrentHashMap<>();

    /**
     * 添加任务
     * @param registrar ScheduledTaskRegistrar
     * @param task SchedulingTaskDO
     */
    public void addTask(ScheduledTaskRegistrar registrar, SchedulingTaskDO task) {
        ISchedulingTask schedulingTask = SpringUtil.getBean(task.getBeanName());
        if(schedulingTask == null) {
            throw new ServerException(StrUtil.format(" Bean【{}】不存在！", task.getBeanName()));
        }

        Runnable runnable = schedulingTask::run;
        Trigger trigger = ctx -> new CronTrigger(task.getCron()).nextExecution(ctx);
        TriggerTask triggerTask = new TriggerTask(runnable, trigger);
        ScheduledTask scheduledTask = registrar.scheduleTriggerTask(triggerTask);
        taskMap.put(task.getId(),  scheduledTask);
    }

    public void updateTask(ScheduledTaskRegistrar registrar, SchedulingTaskDO task) {
        destroyTask(task.getId(), false);
        addTask(registrar, task);
    }

    /**
     * 销毁任务。会等待正在执行的任务执行完成后再销毁。
     * @param taskId taskId
     * @param mayInterruptIfRunning 如果任务仍在运行，是否强制中断（指定 false 以允许任务完成）
     */
    public void destroyTask(Long taskId, boolean mayInterruptIfRunning) {
        ScheduledTask task = taskMap.remove(taskId);
        if (task != null) {
            // 终止任务执行。false 会等待正在执行的任务执行完成后再销毁
            task.cancel(mayInterruptIfRunning);
        }
    }
}
