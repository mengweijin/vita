package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.UserRoleDO;
import com.github.mengweijin.vita.system.domain.vo.user.UserRoleVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  UserRole Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface UserRoleMapper extends BaseVitaMapper<UserRoleDO, UserRoleVO> {

}

