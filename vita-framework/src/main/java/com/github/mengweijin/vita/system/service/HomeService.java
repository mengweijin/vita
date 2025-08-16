package com.github.mengweijin.vita.system.service;

import com.github.mengweijin.vita.monitor.service.LogLoginService;
import com.github.mengweijin.vita.monitor.service.LogOperationService;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskLogService;
import com.github.mengweijin.vita.system.domain.vo.home.HomeConsoleChartDataVO;
import com.github.mengweijin.vita.system.domain.vo.home.HomeConsoleChartVO;
import com.github.mengweijin.vita.system.domain.vo.home.HomeConsoleStatisticVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        vo.setDailyUserLoginCount(logLoginService.getDailyUserLoginCount());
        vo.setDailyUserOperationCount(logOperationService.getDailyUserOperationCount());
        vo.setDailyScheduledTaskExecutedCount(schedulingTaskLogService.getDailyScheduledTaskExecutedCount());
        vo.setTotalScheduledTaskExecutedCount(schedulingTaskLogService.getTotalScheduledTaskExecutedCount());
        return vo;
    }

    public HomeConsoleChartVO getConsoleChart() {
        long days = 6;
        LocalDate now = LocalDate.now();
        LocalDateTime startTime = now.minusDays(days).atTime(LocalTime.MIN);
        LocalDateTime endTime = now.atTime(LocalTime.MAX);

        List<LocalDate> categories = new ArrayList<>();
        for (int i = 0; i <= days; i++) {
            LocalDate localDate = startTime.toLocalDate().plusDays(i);
            categories.add(localDate);
        }

        List<HomeConsoleChartDataVO> dailyUserLoginCountList = logLoginService.selectDailyUserLoginCountBetweenTime(startTime, endTime);
        List<HomeConsoleChartDataVO> dailyUserLoginCountFixedList = fixedConsoleChartData(categories, dailyUserLoginCountList);
        List<Long> activeUsers = dailyUserLoginCountFixedList.stream().map(HomeConsoleChartDataVO::getCategoryValue).toList();

        List<HomeConsoleChartDataVO> dailyUserOperationCountList = logOperationService.selectDailyUserOperationCountBetweenTime(startTime, endTime);
        List<HomeConsoleChartDataVO> dailyUserOperationCountFixedList = fixedConsoleChartData(categories, dailyUserOperationCountList);
        List<Long> userOperations = dailyUserOperationCountFixedList.stream().map(HomeConsoleChartDataVO::getCategoryValue).toList();

        HomeConsoleChartVO vo = new HomeConsoleChartVO();
        vo.setCategory(categories);
        vo.setActiveUsers(activeUsers);
        vo.setUserOperations(userOperations);

        return vo;
    }

    private List<HomeConsoleChartDataVO> fixedConsoleChartData(List<LocalDate> categories, List<HomeConsoleChartDataVO> list) {
        List<HomeConsoleChartDataVO> fixedList = new ArrayList<>();
        categories.forEach(category -> {
            HomeConsoleChartDataVO vo = list.stream()
                    .filter(item -> category.equals(item.getCategory()))
                    .findFirst()
                    .orElse(new HomeConsoleChartDataVO(category, 0L));
            fixedList.add(vo);
        });
        fixedList.sort(Comparator.comparing(HomeConsoleChartDataVO::getCategory));
        return fixedList;
    }
}
