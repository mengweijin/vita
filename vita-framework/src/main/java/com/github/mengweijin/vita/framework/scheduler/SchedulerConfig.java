package com.github.mengweijin.vita.framework.scheduler;

import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskService;
import com.github.mengweijin.vita.system.enums.EYesNo;
import lombok.AllArgsConstructor;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.List;

/**
 * @author mengweijin
 * @since 2025/6/22
 */
@Configuration
@EnableScheduling
@AllArgsConstructor
public class SchedulerConfig implements SchedulingConfigurer {

    private SchedulingTaskService scheduledTaskService;

    @Bean
    public DynamicTaskManager dynamicTaskManager() {
        return new DynamicTaskManager();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {
        DynamicTaskManager dynamicTaskManager = SpringUtil.getBean(DynamicTaskManager.class);
        List<SchedulingTaskDO> taskList = scheduledTaskService.lambdaQuery()
                .eq(SchedulingTaskDO::getDisabled, EYesNo.N.getValue())
                .list();
        // 启动时加载所有启用任务
        taskList.forEach(task  -> dynamicTaskManager.addTask(registrar,  task));
    }
}
