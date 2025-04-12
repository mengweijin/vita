package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.RoleMenu;
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
public class RoleMenuService extends CrudRepository<RoleMenuMapper, RoleMenu> {

    public Set<Long> getMenuIdsByRoleId(Long roleId) {
        List<RoleMenu> roleMenuList = this.lambdaQuery().select(RoleMenu::getMenuId).eq(RoleMenu::getRoleId, roleId).list();
        return roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
    }

    public Set<Long> getMenuIdsInRoleIds(Set<Long> roleIds) {
        List<RoleMenu> roleMenuList = this.lambdaQuery().select(RoleMenu::getMenuId).in(RoleMenu::getRoleId, roleIds).list();
        return roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toSet());
    }

    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, roleId);
        this.getBaseMapper().delete(wrapper);
    }


}
