package com.github.mengweijin.vita.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vita.monitor.domain.entity.LogOperationDO;
import com.github.mengweijin.vita.system.domain.vo.home.HomeConsoleChartDataVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  LogOperation Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface LogOperationMapper extends BaseMapper<LogOperationDO> {

    /**
     * 查询时间范围内的日用户操作次数
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return List {@link HomeConsoleChartDataVO}
     */
    List<HomeConsoleChartDataVO> selectDailyUserOperationCountBetweenTime(LocalDateTime startTime, LocalDateTime endTime);
}

