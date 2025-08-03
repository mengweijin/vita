package com.github.mengweijin.vita.system.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
public class HomeConsoleStatisticVO implements Serializable {

    private Long dailyActiveUserCount;

    private Long dailyOperationCount;

    private Long dailyScheduledTaskExecutedCount;

    private Long totalScheduledTaskExecutedCount;

}
