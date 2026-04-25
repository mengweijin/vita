package com.github.mengweijin.vita.monitor.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.monitor.domain.entity.LogLoginDO;
import com.github.mengweijin.vita.monitor.domain.vo.LogLoginVO;
import com.github.mengweijin.vita.system.domain.vo.home.HomeConsoleChartDataVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * LogLogin Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface LogLoginMapper extends BaseVitaMapper<LogLoginDO, LogLoginVO> {

    /**
     * 查询时间范围内的日用户登录次数
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return List {@link HomeConsoleChartDataVO}
     */
    List<HomeConsoleChartDataVO> selectDailyUserLoginCountBetweenTime(LocalDateTime startTime, LocalDateTime endTime);
}

