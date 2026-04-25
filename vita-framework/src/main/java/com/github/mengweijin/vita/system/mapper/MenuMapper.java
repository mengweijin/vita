package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.MenuDO;
import com.github.mengweijin.vita.system.domain.vo.MenuVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Menu Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface MenuMapper extends BaseVitaMapper<MenuDO, MenuVO> {

}

