package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.domain.vo.ConfigVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Config Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface ConfigMapper extends BaseVitaMapper<ConfigDO, ConfigVO> {

}

