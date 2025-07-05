package com.github.mengweijin.vita.framework.scheduler;

import com.github.mengweijin.vita.framework.domain.P;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskLogDO;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskLogService;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskService;
import com.github.mengweijin.vita.system.enums.ESchedulingTaskStatus;
import com.github.mengweijin.vita.system.enums.EYesNo;
import org.apache.commons.lang3.time.StopWatch;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
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

    String run(SchedulingTaskDO task, Map<?, ?> args);

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
            HashMap<?, ?> args = new HashMap<>();
            if(StrUtil.isNotBlank(task.getArgs())) {
                args = P.objectMapper().readValue(task.getArgs(), HashMap.class);
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
