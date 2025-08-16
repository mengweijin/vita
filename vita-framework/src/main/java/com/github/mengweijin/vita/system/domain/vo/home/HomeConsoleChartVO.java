package com.github.mengweijin.vita.system.domain.vo.home;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
public class HomeConsoleChartVO implements Serializable {

    @JsonFormat(pattern = "MM-dd")
    private List<LocalDate> category;

    private List<Long> activeUsers;

    private List<Long> userOperations;

}
