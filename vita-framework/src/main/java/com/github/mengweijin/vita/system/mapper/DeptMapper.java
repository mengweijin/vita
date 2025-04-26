package com.github.mengweijin.vita.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vita.system.domain.entity.DeptDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Dept Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface DeptMapper extends BaseMapper<DeptDO> {

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

