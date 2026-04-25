package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.UserPostDO;
import com.github.mengweijin.vita.system.domain.vo.user.UserPostVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * UserPost Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface UserPostMapper extends BaseVitaMapper<UserPostDO, UserPostVO> {

}

