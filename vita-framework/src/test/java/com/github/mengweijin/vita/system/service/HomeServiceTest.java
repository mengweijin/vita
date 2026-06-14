package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.date.TimeUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author mengweijin
 * @since 2025/8/16
 */
@Slf4j
class HomeServiceTest {

    @Test
    void calcDate() {
        LocalDateTime startDate = LocalDate.now(Const.ZONE).minusDays(8).atTime(LocalTime.MIN);
        LocalDateTime endDate = LocalDate.now(Const.ZONE).atTime(LocalTime.MAX);

        log.info("startDate={}", startDate);
        log.info("endDate={}", endDate);

        long between = TimeUtil.between(startDate, endDate, ChronoUnit.DAYS);

        Assertions.assertEquals(8, between);
    }
}