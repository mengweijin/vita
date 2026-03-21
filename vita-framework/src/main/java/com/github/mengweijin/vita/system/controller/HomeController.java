package com.github.mengweijin.vita.system.controller;

import com.github.mengweijin.vita.system.domain.vo.home.HomeConsoleChartVO;
import com.github.mengweijin.vita.system.domain.vo.home.HomeConsoleStatisticVO;
import com.github.mengweijin.vita.system.service.HomeService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {

    private HomeService homeService;

    @GetMapping("/query/console/statistic")
    public HomeConsoleStatisticVO queryConsoleStatistic() {
        return homeService.queryConsoleStatistic();
    }

    @GetMapping("/query/console/chart")
    public HomeConsoleChartVO queryConsoleChart() {
        return homeService.queryConsoleChart();
    }
}
