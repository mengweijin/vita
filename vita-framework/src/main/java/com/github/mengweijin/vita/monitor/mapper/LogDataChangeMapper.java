package com.github.mengweijin.vita.monitor.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.monitor.domain.entity.LogDataChangeDO;
import com.github.mengweijin.vita.monitor.domain.vo.LogDataChangeVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  LogLogin Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface LogDataChangeMapper extends BaseVitaMapper<LogDataChangeDO, LogDataChangeVO> {

}

