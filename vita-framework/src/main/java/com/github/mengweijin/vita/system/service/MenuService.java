package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.constant.UserConst;
import com.github.mengweijin.vita.system.domain.entity.Menu;
import com.github.mengweijin.vita.system.domain.entity.User;
import com.github.mengweijin.vita.system.enums.EMenuType;
import com.github.mengweijin.vita.system.enums.EYesNo;
import com.github.mengweijin.vita.system.mapper.MenuMapper;
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
public class MenuService extends CrudRepository<MenuMapper, Menu> {

    private UserService userService;

    private UserRoleService userRoleService;

    private RoleMenuService roleMenuService;

    /**
     * Custom paging query
     *
     * @param page page
     * @param menu {@link Menu}
     * @return IPage
     */
    public IPage<Menu> page(IPage<Menu> page, Menu menu) {
        LambdaQueryWrapper<Menu> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(menu.getParentId()), Menu::getParentId, menu.getParentId())
                .eq(StrValidator.isNotBlank(menu.getType()), Menu::getType, menu.getType())
                .eq(StrValidator.isNotBlank(menu.getTitle()), Menu::getTitle, menu.getTitle())
                .eq(StrValidator.isNotBlank(menu.getPermission()), Menu::getPermission, menu.getPermission())
                .eq(StrValidator.isNotBlank(menu.getRouteName()), Menu::getRouteName, menu.getRouteName())
                .eq(StrValidator.isNotBlank(menu.getRoutePath()), Menu::getRoutePath, menu.getRoutePath())
                .eq(StrValidator.isNotBlank(menu.getComponent()), Menu::getComponent, menu.getComponent())
                .eq(!Objects.isNull(menu.getSeq()), Menu::getSeq, menu.getSeq())
                .eq(StrValidator.isNotBlank(menu.getIcon()), Menu::getIcon, menu.getIcon())
                .eq(StrValidator.isNotBlank(menu.getDisabled()), Menu::getDisabled, menu.getDisabled())
                .eq(!Objects.isNull(menu.getId()), Menu::getId, menu.getId())
                .eq(!Objects.isNull(menu.getCreateBy()), Menu::getCreateBy, menu.getCreateBy())
                .eq(!Objects.isNull(menu.getCreateTime()), Menu::getCreateTime, menu.getCreateTime())
                .eq(!Objects.isNull(menu.getUpdateBy()), Menu::getUpdateBy, menu.getUpdateBy())
                .eq(!Objects.isNull(menu.getUpdateTime()), Menu::getUpdateTime, menu.getUpdateTime());
        return this.page(page, query);
    }

    public Set<String> getMenuPermissionListByUsername(String username) {
        if (UserConst.ADMIN_USERNAME.equals(username)) {
            return this.lambdaQuery().select(Menu::getPermission).list()
                    .stream().map(Menu::getPermission).collect(Collectors.toSet());
        }

        User user = userService.getByUsername(username);
        Set<Long> roleIds = userRoleService.getRoleIdsByUserId(user.getId());
        Set<Long> menuIds = roleMenuService.getMenuIdsInRoleIds(roleIds);

        return this.lambdaQuery().select(Menu::getPermission).in(Menu::getId, menuIds).list()
                .stream().map(Menu::getPermission).collect(Collectors.toSet());
    }

    public List<Menu> getSideMenuByUserId(Long userId) {
        if (userId.equals(UserConst.ADMIN_USER_ID)) {
            return this.lambdaQuery().eq(Menu::getDisabled, EYesNo.N.getValue()).ne(Menu::getType, EMenuType.BTN.getValue()).list();
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
