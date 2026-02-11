package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.text.StrValidator;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.util.AopUtils;
import com.github.mengweijin.vita.monitor.service.LogDataChangeService;
import com.github.mengweijin.vita.system.constant.VitaConst;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.domain.entity.UserRoleDO;
import com.github.mengweijin.vita.system.domain.vo.user.UserRoleVO;
import com.github.mengweijin.vita.system.mapper.UserRoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@AllArgsConstructor
public class UserRoleService extends BaseVitaService<UserRoleMapper, UserRoleDO, UserRoleVO> {

    private LogDataChangeService logDataChangeService;

    public Set<Long> getRoleIdsByUserId(Long userId) {
        List<UserRoleDO> list = this.lambdaQuery().select(UserRoleDO::getRoleId).eq(UserRoleDO::getUserId, userId).list();
        return list.stream().map(UserRoleDO::getRoleId).collect(Collectors.toSet());
    }

    public Set<Long> getUserIdsByRoleId(Long roleId) {
        List<UserRoleDO> list = this.lambdaQuery().select(UserRoleDO::getUserId).eq(UserRoleDO::getRoleId, roleId).list();
        return list.stream().map(UserRoleDO::getUserId).collect(Collectors.toSet());
    }

    public Set<Long> getUserIdsInRoleIds(List<Long> roleIds) {
        List<UserRoleDO> list = this.lambdaQuery().select(UserRoleDO::getUserId).in(UserRoleDO::getRoleId, roleIds).list();
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
        Set<Long> beforeRoleIds = this.getRoleIdsByUserId(userId);

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

        AopUtils.getAopProxy(this).saveBatch(list, Constants.DEFAULT_BATCH_SIZE);

        // 保存用户角色变动日志
        logDataChangeService.saveWhenListChange(VitaConst.TABLE_VT_USER_ROLE, userId, List.copyOf(beforeRoleIds), roleIds);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean addUsers(Long roleId, List<Long> userIds) {
        List<UserRoleDO> queryList = this.lambdaQuery().eq(UserRoleDO::getRoleId, roleId).in(UserRoleDO::getUserId, userIds).list();
        List<Long> queriedUserIds = queryList.stream().map(UserRoleDO::getUserId).toList();
        // 筛选出来新增加该角色的用户，避免重复添加
        List<Long> addIds = userIds.stream().filter(item -> !queriedUserIds.contains(item)).toList();

        // 剩下的用户均为新增当前角色
        List<UserRoleDO> list = addIds.stream().map(userId -> {
            UserRoleDO userRoleDO = new UserRoleDO();
            userRoleDO.setRoleId(roleId);
            userRoleDO.setUserId(userId);
            return userRoleDO;
        }).toList();
        boolean saved = AopUtils.getAopProxy(this).saveBatch(list, Constants.DEFAULT_BATCH_SIZE);

        if(saved) {
            // 保存用户角色变动日志
            for (Long userId : addIds) {
                logDataChangeService.saveWhenListChange(VitaConst.TABLE_VT_USER_ROLE, userId, List.of(), List.of(roleId));
            }
        }
        return saved;
    }

    public boolean removeByRoleIdInUserIds(Long roleId, List<Long> userIds) {
        boolean removed = this.lambdaUpdate().eq(UserRoleDO::getRoleId, roleId).in(UserRoleDO::getUserId, userIds).remove();

        if(removed) {
            // 保存用户角色变动日志
            for (Long userId : userIds) {
                logDataChangeService.saveWhenListChange(VitaConst.TABLE_VT_USER_ROLE, userId, List.of(roleId), List.of());
            }
        }
        return removed;
    }

    @Override
    public LambdaQueryWrapper<UserRoleDO> buildQueryWrapper(UserRoleDO entity) {
        return defaultQueryWrapper(entity);
    }
}
