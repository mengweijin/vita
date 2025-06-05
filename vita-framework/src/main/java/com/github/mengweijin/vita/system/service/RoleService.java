package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.constant.UserConst;
import com.github.mengweijin.vita.system.domain.bo.RolePermissionBO;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.domain.entity.RoleMenuDO;
import com.github.mengweijin.vita.system.mapper.RoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  Role Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class RoleService extends CrudRepository<RoleMapper, RoleDO> {

    private RoleMenuService roleMenuService;

    public LambdaQueryWrapper<RoleDO> getQueryWrapper(RoleDO role) {
        LambdaQueryWrapper<RoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(role.getId() != null, RoleDO::getId, role.getId());
        wrapper.eq(StrUtil.isNotBlank(role.getDisabled()), RoleDO::getDisabled, role.getDisabled());
        wrapper.eq(role.getCreateBy() != null, RoleDO::getCreateBy, role.getCreateBy());
        wrapper.eq(role.getUpdateBy() != null, RoleDO::getUpdateBy, role.getUpdateBy());
        wrapper.gt(role.getSearchStartTime() != null, RoleDO::getCreateTime, role.getSearchStartTime());
        wrapper.le(role.getSearchEndTime() != null, RoleDO::getCreateTime, role.getSearchEndTime());
        if (StrUtil.isNotBlank(role.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(RoleDO::getName, role.getKeywords()));
                w.or(w1 -> w1.like(RoleDO::getCode, role.getKeywords()));
            });
        }
        return wrapper;
    }

    public Set<String> getRoleCodeByUsername(String username) {
        if (UserConst.ADMIN_USERNAME.equals(username)) {
            return this.list().stream().map(RoleDO::getCode).collect(Collectors.toSet());
        }
        return this.getBaseMapper().getRoleCodeByUsername(username);
    }

    public boolean setMenuPermission(RolePermissionBO bo) {
        roleMenuService.removeByRoleId(bo.getRoleId());

        List<RoleMenuDO> collect = bo.getMenuIds().stream().map(menuId -> {
            RoleMenuDO roleMenu = new RoleMenuDO();
            roleMenu.setRoleId(bo.getRoleId());
            roleMenu.setMenuId(menuId);
            return roleMenu;
        }).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            return roleMenuService.saveBatch(collect, Constants.DEFAULT_BATCH_SIZE);
        }
        return true;
    }

    public RoleDO getByCode(String code) {
        return this.lambdaQuery().eq(RoleDO::getCode, code).one();
    }
    
}
