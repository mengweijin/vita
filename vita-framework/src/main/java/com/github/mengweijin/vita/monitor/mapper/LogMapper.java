package com.github.mengweijin.vita.monitor.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.monitor.domain.entity.LogSystemDO;
import com.github.mengweijin.vita.monitor.domain.vo.LogSystemVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  LogOperation Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface LogMapper extends BaseVitaMapper<LogSystemDO, LogSystemVO> {

}

