package com.github.mengweijin.vita.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

/**
 * <p>
 *  Role Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface RoleMapper extends BaseMapper<RoleDO> {

    Set<String> getRoleCodeByUsername(String username);
}

