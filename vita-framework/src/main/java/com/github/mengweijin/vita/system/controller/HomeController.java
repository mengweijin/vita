package com.github.mengweijin.vita.system.controller;

import com.github.mengweijin.vita.system.domain.vo.HomeConsoleStatisticVO;
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

    @GetMapping("/get-console-statistic")
    public HomeConsoleStatisticVO getConsoleStatistic() {
        return homeService.getConsoleStatistic();
    }

}
