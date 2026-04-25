package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.RoleMenuDO;
import com.github.mengweijin.vita.system.domain.vo.RoleMenuVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * RoleMenu Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface RoleMenuMapper extends BaseVitaMapper<RoleMenuDO, RoleMenuVO> {

}

