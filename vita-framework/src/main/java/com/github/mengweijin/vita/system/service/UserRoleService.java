package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.domain.entity.UserRoleDO;
import com.github.mengweijin.vita.system.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrValidator;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * User Role Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class UserRoleService extends CrudRepository<UserRoleMapper, UserRoleDO> {

    public Set<Long> getRoleIdsByUserId(Long userId) {
        List<UserRoleDO> list = this.lambdaQuery().select(UserRoleDO::getRoleId).eq(UserRoleDO::getUserId, userId).list();
        return list.stream().map(UserRoleDO::getRoleId).collect(Collectors.toSet());
    }

    public Set<Long> getUserIdsByRoleId(Long roleId) {
        List<UserRoleDO> list = this.lambdaQuery().select(UserRoleDO::getUserId).eq(UserRoleDO::getRoleId, roleId).list();
        return list.stream().map(UserRoleDO::getUserId).collect(Collectors.toSet());
    }

    public Set<Long> getUserIdsByRoleCode(String roleCode) {
        Set<Long> set = new HashSet<>();
        if (StrValidator.isBlank(roleCode)) {
            return set;
        }
        RoleService roleService = SpringUtil.getBean(RoleService.class);
        RoleDO role = roleService.getByCode(roleCode);
        if (role == null) {
            return set;
        }
        return this.getUserIdsByRoleId(role.getId());
    }

    public Long countUserInRoleIds(Collection<?> roleIds) {
        return this.lambdaQuery().in(UserRoleDO::getRoleId, roleIds).count();
    }

    @Transactional(rollbackFor = Exception.class)
    public void setUserRoles(Long userId, List<Long> roleIds) {
        this.lambdaUpdate().eq(UserRoleDO::getUserId, userId).remove();

        if(CollUtil.isEmpty(roleIds)) {
            return;
        }

        List<UserRoleDO> list = roleIds.stream().map(roleId -> {
            UserRoleDO userRole = new UserRoleDO();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            return userRole;
        }).toList();

        this.saveBatch(list, Constants.DEFAULT_BATCH_SIZE);
    }
}
