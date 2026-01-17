package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.system.domain.entity.RoleMenuDO;
import com.github.mengweijin.vita.system.domain.vo.RoleMenuVO;
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
public class RoleMenuService extends BaseVitaService<RoleMenuMapper, RoleMenuDO, RoleMenuVO> {

    public Set<Long> getMenuIdsByRoleId(Long roleId) {
        List<RoleMenuDO> roleMenuList = this.lambdaQuery().select(RoleMenuDO::getMenuId).eq(RoleMenuDO::getRoleId, roleId).list();
        return roleMenuList.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toSet());
    }

    public Set<Long> getMenuIdsInRoleIds(Set<Long> roleIds) {
        List<RoleMenuDO> roleMenuList = this.lambdaQuery().select(RoleMenuDO::getMenuId).in(RoleMenuDO::getRoleId, roleIds).list();
        return roleMenuList.stream().map(RoleMenuDO::getMenuId).collect(Collectors.toSet());
    }

    public void removeByRoleId(Long roleId) {
        LambdaQueryWrapper<RoleMenuDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RoleMenuDO::getRoleId, roleId);
        this.getBaseMapper().delete(wrapper);
    }


    @Override
    public LambdaQueryWrapper<RoleMenuDO> buildQueryWrapper(RoleMenuDO entity) {
        return defaultQueryWrapper(entity);
    }
}
