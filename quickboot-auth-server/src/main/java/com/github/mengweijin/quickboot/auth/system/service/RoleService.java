package com.github.mengweijin.quickboot.auth.system.service;

import com.github.mengweijin.quickboot.auth.system.entity.Role;
import com.github.mengweijin.quickboot.auth.system.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息表 implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-01-07
 */
@Slf4j
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> implements IService<Role> {

    /**
     * <p>
     * RoleMapper
     * </p>
     */
    @Autowired
    private RoleMapper roleMapper;
}

