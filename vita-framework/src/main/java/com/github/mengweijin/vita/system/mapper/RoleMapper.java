package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.domain.vo.RoleVO;
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
public interface RoleMapper extends BaseVitaMapper<RoleDO, RoleVO> {

    /**
     * get role code by username
     * @param username username
     * @return roles
     */
    Set<String> getRoleCodeByUsername(String username);

    /**
     * get role code by userId
     * @param userId userId
     * @return roles
     */
    Set<String> getRoleCodeByUserId(Long userId);
}

