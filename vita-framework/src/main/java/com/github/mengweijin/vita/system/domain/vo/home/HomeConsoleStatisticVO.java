package com.github.mengweijin.vita.system.domain.vo.home;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
public class HomeConsoleStatisticVO implements Serializable {

    private Long dailyUserLoginCount;

    private Long dailyUserOperationCount;

    private Long totalUserLoginCount;

    private Long totalUserOperationCount;

    private Long dailyScheduledTaskExecutedCount;

    private Long totalScheduledTaskExecutedCount;

}
