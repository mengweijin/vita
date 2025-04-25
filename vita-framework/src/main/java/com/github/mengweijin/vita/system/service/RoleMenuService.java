package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.RoleMenuDO;
import com.github.mengweijin.vita.system.mapper.RoleMenuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  Role Menu Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class RoleMenuService extends CrudRepository<RoleMenuMapper, RoleMenuDO> {

    public Set<Long> getMenuIdsByRoleId(Long roleId) {
        List<RoleMenuDO> roleMenuList = this.lambdaQuery().select(RoleMenuDO::getMenuId).eq(RoleMenuDO::getRoleId, roleId).list();
        return roleMenuList.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toSet());
    }

    public Set<Long> getMenuIdsInRoleIds(Set<Long> roleIds) {
        List<RoleMenuDO> roleMenuList = this.lambdaQuery().select(RoleMenuDO::getMenuId).in(RoleMenuDO::getRoleId, roleIds).list();
        return roleMenuList.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toSet());
    }

    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenuDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenuDO::getRoleId, roleId);
        this.getBaseMapper().delete(wrapper);
    }


}
