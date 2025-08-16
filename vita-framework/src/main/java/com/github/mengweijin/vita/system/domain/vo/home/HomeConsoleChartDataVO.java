package com.github.mengweijin.vita.system.domain.vo.home;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeConsoleChartDataVO implements Serializable {

    private LocalDate category;

    private Long categoryValue;

}
