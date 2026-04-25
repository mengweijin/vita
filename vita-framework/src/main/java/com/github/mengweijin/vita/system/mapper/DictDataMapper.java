package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.DictDataDO;
import com.github.mengweijin.vita.system.domain.vo.DictDataVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * DictData Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface DictDataMapper extends BaseVitaMapper<DictDataDO, DictDataVO> {

}

