package com.github.mengweijin.vita.system.service;

import com.github.mengweijin.vita.monitor.service.LogLoginService;
import com.github.mengweijin.vita.monitor.service.LogOperationService;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskLogService;
import com.github.mengweijin.vita.system.domain.vo.HomeConsoleStatisticVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 首页 服务类
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Service
@AllArgsConstructor
public class HomeService {

    private LogLoginService logLoginService;

    private LogOperationService logOperationService;

    private SchedulingTaskLogService schedulingTaskLogService;

    public HomeConsoleStatisticVO getConsoleStatistic() {
        HomeConsoleStatisticVO vo = new HomeConsoleStatisticVO();
        vo.setDailyActiveUserCount(logLoginService.getDailyActiveUserCount());
        vo.setDailyOperationCount(logOperationService.getDailyOperationCount());
        vo.setDailyScheduledTaskExecutedCount(schedulingTaskLogService.getDailyScheduledTaskExecutedCount());
        vo.setTotalScheduledTaskExecutedCount(schedulingTaskLogService.getTotalScheduledTaskExecutedCount());
        return vo;
    }
}
