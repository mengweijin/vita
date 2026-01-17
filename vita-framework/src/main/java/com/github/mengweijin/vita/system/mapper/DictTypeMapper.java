package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.DictTypeDO;
import com.github.mengweijin.vita.system.domain.vo.DictTypeVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  DictType Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface DictTypeMapper extends BaseVitaMapper<DictTypeDO, DictTypeVO> {

}

