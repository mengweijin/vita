package com.github.mengweijin.vita.framework.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author mengweijin
 */
@Slf4j
@Component
public class SchedulingTaskFactory {

    private final Map<String, ISchedulingTask> schedulingTaskMap;

    public SchedulingTaskFactory(Map<String, ISchedulingTask> schedulingTaskMap) {
        this.schedulingTaskMap = schedulingTaskMap;
    }

    public ISchedulingTask getSchedulingTask(String beanName) {
        return schedulingTaskMap.get(beanName);
    }
}
