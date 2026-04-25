package com.github.mengweijin.vita.framework.scheduler;

import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author mengweijin
 * @since 2025/6/22
 */
@SuppressWarnings({"unused"})
@Slf4j
@Component
@AllArgsConstructor
public class DynamicTaskManager implements DisposableBean {

    private final TaskScheduler taskScheduler;

    private final SchedulingTaskFactory schedulingTaskFactory;

    private final Map<String, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    /**
     * 添加 Cron 表达式任务
     *
     * @param task SchedulingTaskDO
     * @throws ServerException 任务已存在则抛出异常。
     */
    public void scheduleCronTask(SchedulingTaskDO task) throws ServerException {
        Runnable runnable = () -> {
            ISchedulingTask schedulingTask = schedulingTaskFactory.getSchedulingTask(task.getBeanName());
            if (schedulingTask != null) {
                schedulingTask.execute(task.getId());
            }
        };
        this.scheduleCronTask(task.getId().toString(), runnable, task.getCron());
    }

    /**
     * 添加 Cron 表达式任务
     *
     * @param taskId         任务唯一标识
     * @param task           执行逻辑
     * @param cronExpression Cron 表达式
     */
    public void scheduleCronTask(String taskId, Runnable task, String cronExpression) throws ServerException {
        checkTaskExists(taskId);
        Trigger trigger = ctx -> new CronTrigger(cronExpression).nextExecution(ctx);
        ScheduledFuture<?> future = taskScheduler.schedule(task, trigger);
        scheduledTasks.put(taskId, future);
    }

    /**
     * 固定延迟任务（上一次执行结束后延迟指定时间）
     *
     * @param taskId 任务标识
     * @param task   执行逻辑
     * @param delay  完成一次执行和开始下一次执行之间的延迟
     */
    public void scheduleFixedDelay(String taskId, Runnable task, Duration delay) throws ServerException {
        checkTaskExists(taskId);
        ScheduledFuture<?> future = taskScheduler.scheduleWithFixedDelay(task, delay);
        scheduledTasks.put(taskId, future);
    }

    /**
     * 固定频率任务（不考虑上一次执行是否完成）
     *
     * @param taskId 任务标识
     * @param task   执行逻辑
     * @param period 连续执行任务之间的间隔
     */
    public void scheduleFixedRate(String taskId, Runnable task, Duration period) throws ServerException {
        checkTaskExists(taskId);
        ScheduledFuture<?> future = taskScheduler.scheduleAtFixedRate(task, period);
        scheduledTasks.put(taskId, future);
    }

    /**
     * 一次性任务（指定时间执行）
     *
     * @param taskId    任务标识
     * @param task      执行逻辑
     * @param startTime 任务的期望执行时间（如果是过去的时间，任务将立即执行，即尽快执行）
     */
    public void scheduleOnce(String taskId, Runnable task, Instant startTime) throws ServerException {
        checkTaskExists(taskId);
        ScheduledFuture<?> future = taskScheduler.schedule(task, startTime);
        scheduledTasks.put(taskId, future);
    }

    /**
     * 取消任务
     *
     * @param taskId 任务标识
     * @throws ServerException 任务取消失败则抛出异常。
     */
    public void cancelTask(String taskId) throws ServerException {
        ScheduledFuture<?> future = scheduledTasks.get(taskId);
        if (future != null) {
            // 允许中断正在运行的任务
            boolean cancelled = future.cancel(true);
            if (cancelled) {
                scheduledTasks.remove(taskId);
            } else {
                throw new ServerException("Task with id '" + taskId + "' cancel failed. Please try again.");
            }
        }
    }

    /**
     * 获取所有任务标识
     */
    public Set<String> getTaskIds() {
        return scheduledTasks.keySet();
    }

    /**
     * 应用关闭时自动取消所有任务
     */
    @Override
    public void destroy() {
        scheduledTasks.values().forEach(future -> future.cancel(true));
        scheduledTasks.clear();
    }

    /**
     * 检查任务是否已存在
     *
     * @param taskId 任务标识
     * @throws ServerException 任务已存在则抛出异常。
     */
    private void checkTaskExists(String taskId) throws ServerException {
        if (scheduledTasks.containsKey(taskId)) {
            throw new ServerException("Task with id '" + taskId + "' already exists.");
        }
    }
}
