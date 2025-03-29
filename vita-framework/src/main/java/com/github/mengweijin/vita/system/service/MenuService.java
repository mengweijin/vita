package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.constant.MenuConst;
import com.github.mengweijin.vita.system.constant.UserConst;
import com.github.mengweijin.vita.system.domain.entity.Menu;
import com.github.mengweijin.vita.system.mapper.MenuMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

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
                .eq(StrValidator.isNotBlank(menu.getRedirect()), Menu::getRedirect, menu.getRedirect())
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

    public Set<String> getMenuPermissionListByLoginUsername(String username) {
        if (UserConst.ADMIN_USERNAME.equals(username)) {
            return Collections.singleton(MenuConst.ALL_PERMISSIONS);
        }
        return this.getBaseMapper().selectPermissionListByUsername(username);
    }

}
