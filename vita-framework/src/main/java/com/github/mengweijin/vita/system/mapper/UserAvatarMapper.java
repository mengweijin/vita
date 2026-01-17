package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.UserAvatarDO;
import com.github.mengweijin.vita.system.domain.vo.user.UserAvatarVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  User Avatar Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface UserAvatarMapper extends BaseVitaMapper<UserAvatarDO, UserAvatarVO> {

}

