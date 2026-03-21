package com.github.mengweijin.vita.framework.scheduler;

import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskService;
import com.github.mengweijin.vita.framework.enums.dict.EYesNo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

/**
 * @author mengweijin
 * @since 2025/6/22
 */
@Slf4j
@Configuration
@EnableScheduling
@AllArgsConstructor
public class SchedulerConfig implements InitializingBean {

    private DynamicTaskManager dynamicTaskManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        SchedulingTaskService schedulingTaskService = SpringUtil.getBean(SchedulingTaskService.class);
        List<SchedulingTaskDO> taskList = schedulingTaskService.lambdaQuery()
                .eq(SchedulingTaskDO::getDisabled, EYesNo.N.getValue())
                .list();
        // 启动时加载所有启用任务
        taskList.forEach(task -> {
            dynamicTaskManager.scheduleCronTask(task);
            log.info("Bean [{}] task [{}] has been scheduled.", task.getBeanName(), task.getName());
        });
    }
}
