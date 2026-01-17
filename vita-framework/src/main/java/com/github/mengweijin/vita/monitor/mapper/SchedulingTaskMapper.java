package com.github.mengweijin.vita.monitor.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.domain.vo.SchedulingTaskVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Post Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface SchedulingTaskMapper extends BaseVitaMapper<SchedulingTaskDO, SchedulingTaskVO> {

}

