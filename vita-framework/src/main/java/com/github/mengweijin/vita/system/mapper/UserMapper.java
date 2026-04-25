package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.user.UserVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * User Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface UserMapper extends BaseVitaMapper<UserDO, UserVO> {

}

