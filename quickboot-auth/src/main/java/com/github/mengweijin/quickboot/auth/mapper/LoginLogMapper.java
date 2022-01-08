package com.github.mengweijin.quickboot.auth.mapper;

import com.github.mengweijin.quickboot.auth.entity.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 登录日志表 Mapper Interface
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

}

