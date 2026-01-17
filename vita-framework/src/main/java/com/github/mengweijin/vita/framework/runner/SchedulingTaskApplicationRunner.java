package com.github.mengweijin.vita.framework.runner;

import com.github.mengweijin.vita.framework.scheduler.ISchedulingTask;
import com.github.mengweijin.vita.framework.scheduler.SchedulingTaskFactory;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskService;
import com.github.mengweijin.vita.system.enums.dict.EYesNo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author mengweijin
 * @since 2023/6/6
 */
@Slf4j
@Order(Integer.MAX_VALUE - 1)
@Component
@AllArgsConstructor
public class SchedulingTaskApplicationRunner implements ApplicationRunner {

    private SchedulingTaskService schedulingTaskService;

    private SchedulingTaskFactory schedulingTaskFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SchedulingTaskDO> list = schedulingTaskService.lambdaQuery()
                .eq(SchedulingTaskDO::getDisabled, EYesNo.N.getValue())
                .eq(SchedulingTaskDO::getExecuteAfterStarted, EYesNo.Y.getValue())
                .list();

        list.forEach(task -> {
            ISchedulingTask schedulingTask = schedulingTaskFactory.getSchedulingTask(task.getBeanName());
            // 异步执行
            CompletableFuture.runAsync(() -> {
                log.info("Run scheduling task [{}] on application started.", task.getName());
                schedulingTask.execute(task.getId());
            });
        });
    }
}
