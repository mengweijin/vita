package com.github.mengweijin.quickboot.auth.data.service;

import lombok.extern.slf4j.Slf4j;
import com.github.mengweijin.quickboot.auth.data.entity.LoginLog;
import com.github.mengweijin.quickboot.auth.data.mapper.LoginLogMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录日志表 implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Service
public class LoginLogService extends ServiceImpl<LoginLogMapper, LoginLog> implements IService<LoginLog> {

    /**
     * <p>
     * LoginLogMapper
     * </p>
     */
    @Autowired
    private LoginLogMapper loginLogMapper;
}

