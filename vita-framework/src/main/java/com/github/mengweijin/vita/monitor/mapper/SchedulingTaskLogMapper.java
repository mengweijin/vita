package com.github.mengweijin.vita.monitor.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskLogDO;
import com.github.mengweijin.vita.monitor.domain.vo.SchedulingTaskLogVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Post Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface SchedulingTaskLogMapper extends BaseVitaMapper<SchedulingTaskLogDO, SchedulingTaskLogVO> {

}

