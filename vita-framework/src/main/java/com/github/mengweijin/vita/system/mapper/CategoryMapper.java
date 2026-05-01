package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import com.github.mengweijin.vita.system.domain.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * Category Mapper
 *
 * @author mengweijin
 */
@Mapper
public interface CategoryMapper extends BaseVitaMapper<CategoryDO, CategoryVO> {

}


