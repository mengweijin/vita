package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.constant.UserConst;
import com.github.mengweijin.vita.system.domain.bo.RolePermissionBO;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.domain.entity.RoleMenuDO;
import com.github.mengweijin.vita.system.mapper.RoleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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

    /**
     * Custom paging query
     * @param page page
     * @param role {@link RoleDO}
     * @return IPage
     */
    public IPage<RoleDO> page(IPage<RoleDO> page, RoleDO role){
        LambdaQueryWrapper<RoleDO> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(role.getCode()), RoleDO::getCode, role.getCode())
                .eq(!Objects.isNull(role.getSeq()), RoleDO::getSeq, role.getSeq())
                .eq(StrValidator.isNotBlank(role.getDisabled()), RoleDO::getDisabled, role.getDisabled())
                .like(StrValidator.isNotBlank(role.getName()), RoleDO::getName, role.getName())
                .eq(StrValidator.isNotBlank(role.getRemark()), RoleDO::getRemark, role.getRemark())
                .eq(!Objects.isNull(role.getId()), RoleDO::getId, role.getId())
                .eq(!Objects.isNull(role.getCreateBy()), RoleDO::getCreateBy, role.getCreateBy())
                .eq(!Objects.isNull(role.getCreateTime()), RoleDO::getCreateTime, role.getCreateTime())
                .eq(!Objects.isNull(role.getUpdateBy()), RoleDO::getUpdateBy, role.getUpdateBy())
                .eq(!Objects.isNull(role.getUpdateTime()), RoleDO::getUpdateTime, role.getUpdateTime());

        query.orderByAsc(RoleDO::getSeq);
        return this.page(page, query);
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
