package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.exception.impl.ClientException;
import com.github.mengweijin.vita.system.constant.UserConst;
import com.github.mengweijin.vita.system.domain.entity.MenuDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.enums.EMenuType;
import com.github.mengweijin.vita.system.enums.EYesNo;
import com.github.mengweijin.vita.system.mapper.MenuMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * Menu Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class MenuService extends CrudRepository<MenuMapper, MenuDO> {

    private UserService userService;

    private UserRoleService userRoleService;

    private RoleMenuService roleMenuService;

    @Override
    public boolean removeByIds(Collection<?> ids) {
        Long count = this.lambdaQuery().in(MenuDO::getParentId, ids).count();
        if(count > 0) {
            throw new ClientException("Please delete the child node first!");
        }
        return super.removeByIds(ids);
    }

    public LambdaQueryWrapper<MenuDO> getQueryWrapper(MenuDO menu) {
        LambdaQueryWrapper<MenuDO> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(menu.getId() != null, MenuDO::getId, menu.getId());
        wrapper.eq(menu.getParentId() != null, MenuDO::getParentId, menu.getParentId());
        wrapper.eq(StrUtil.isNotBlank(menu.getType()), MenuDO::getType, menu.getType());
        wrapper.eq(StrUtil.isNotBlank(menu.getDisabled()), MenuDO::getDisabled, menu.getDisabled());
        wrapper.eq(menu.getCreateBy() != null, MenuDO::getCreateBy, menu.getCreateBy());
        wrapper.eq(menu.getUpdateBy() != null, MenuDO::getUpdateBy, menu.getUpdateBy());
        wrapper.gt(menu.getSearchStartTime() != null, MenuDO::getCreateTime, menu.getSearchStartTime());
        wrapper.le(menu.getSearchEndTime() != null, MenuDO::getCreateTime, menu.getSearchEndTime());
        if (StrUtil.isNotBlank(menu.getKeywords())) {
            wrapper.or(w -> w.like(MenuDO::getTitle, menu.getKeywords()));
            wrapper.or(w -> w.like(MenuDO::getPermission, menu.getKeywords()));
            wrapper.or(w -> w.like(MenuDO::getComponent, menu.getKeywords()));
        }
        return wrapper;
    }

    public Set<String> getMenuPermissionListByUsername(String username) {
        if (UserConst.ADMIN_USERNAME.equals(username)) {
            return this.lambdaQuery().select(MenuDO::getPermission).isNotNull(MenuDO::getPermission).list()
                    .stream().map(MenuDO::getPermission).collect(Collectors.toSet());
        }

        UserDO user = userService.getByUsername(username);
        Set<Long> roleIds = userRoleService.getRoleIdsByUserId(user.getId());
        Set<Long> menuIds = roleMenuService.getMenuIdsInRoleIds(roleIds);

        return this.lambdaQuery().select(MenuDO::getPermission).isNotNull(MenuDO::getPermission).in(MenuDO::getId, menuIds).list()
                .stream().map(MenuDO::getPermission).collect(Collectors.toSet());
    }

    public List<MenuDO> getSideMenuByUserId(Long userId) {
        if (userId.equals(UserConst.ADMIN_USER_ID)) {
            return this.lambdaQuery().eq(MenuDO::getDisabled, EYesNo.N.getValue()).ne(MenuDO::getType, EMenuType.BTN.getValue()).list();
        }

        Set<Long> roleIds = userRoleService.getRoleIdsByUserId(userId);
        Set<Long> menuIds = roleMenuService.getMenuIdsInRoleIds(roleIds);
        // 这里排除掉按钮类型的和已禁用的菜单
        return this.getBaseMapper().selectByIds(menuIds).stream()
                .filter(m -> EYesNo.N.getValue().equalsIgnoreCase(m.getDisabled()))
                .filter(m -> !EMenuType.BTN.getValue().equalsIgnoreCase(m.getType()))
                .toList();
    }

}
