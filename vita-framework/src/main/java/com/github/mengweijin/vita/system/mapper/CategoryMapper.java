package com.github.mengweijin.vita.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vita.system.domain.CategoryDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Category Mapper
 *
 * @author mengweijin
 */
@Mapper
public interface CategoryMapper extends BaseMapper<CategoryDO> {

    /**
     * select children ids
     *
     * @param id current id
     * @return children ids
     */
    List<Long> selectChildrenIdsById(Long id);

    /**
     * select children ids with current id
     *
     * @param id current id
     * @return children ids with current id
     */
    List<Long> selectChildrenIdsWithCurrentIdById(Long id);

    /**
     * select parent ids with current id
     *
     * @param id current id
     * @return parent ids with current id
     */
    List<Long> selectParentIdsWithCurrentIdById(Long id);
}


