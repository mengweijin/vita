package com.github.mengweijin.vita.framework.scheduler;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.util.ObjectMapperUtils;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskLogDO;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskLogService;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskService;
import com.github.mengweijin.vita.system.enums.dict.ESchedulingTaskStatus;
import com.github.mengweijin.vita.system.enums.dict.EYesNo;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 * @since 2025/6/22
 */
public interface ISchedulingTask {

    Logger log = LoggerFactory.getLogger(ISchedulingTask.class);

    /**
     * 运行指定调度任务接口方法，以供子类实现。
     * @param task {@link SchedulingTaskDO}
     * @param args 调度任务执行所需要的执行参数
     * @return 返回自定义消息内容，以便记录到任务执行日志中。
     */
    String run(SchedulingTaskDO task, Map<?, ?> args);

    /**
     * 根据调度任务的 id 来执行任务
     * @param taskId 调度任务 ID
     */
    default void execute(Long taskId) {
        SchedulingTaskService schedulingTaskService = SpringUtil.getBean(SchedulingTaskService.class);
        SchedulingTaskLogService schedulingTaskLogService = SpringUtil.getBean(SchedulingTaskLogService.class);
        SchedulingTaskDO task = schedulingTaskService.getById(taskId);

        SchedulingTaskLogDO taskLog = new SchedulingTaskLogDO();
        taskLog.setSchedulingTaskId(taskId);
        taskLog.setStatus(ESchedulingTaskStatus.RUNNING.getValue());
        taskLog.setArgs(task.getArgs());

        schedulingTaskLogService.save(taskLog);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        try {
            HashMap<?, ?> args = new HashMap<>(16);
            if(StrUtil.isNotBlank(task.getArgs())) {
                args = ObjectMapperUtils.getObjectMapperWrapper().readValue(task.getArgs(), HashMap.class);
            }
            String result = run(task, args);

            taskLog.setSuccess(EYesNo.Y.getValue());
            taskLog.setMessage(result);
        } catch (Throwable e){
            taskLog.setSuccess(EYesNo.N.getValue());
            taskLog.setMessage(e.getMessage());
            log.error(e.getMessage(), e);
        } finally {
            stopWatch.stop();
            taskLog.setStatus(ESchedulingTaskStatus.FINISHED.getValue());
            taskLog.setCostTime(stopWatch.getDuration().toMillis());
            schedulingTaskLogService.updateById(taskLog);
        }
    }
}
