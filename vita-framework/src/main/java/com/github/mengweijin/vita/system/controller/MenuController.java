package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.Menu;
import com.github.mengweijin.vita.system.service.MenuService;
import com.github.mengweijin.vita.system.service.RoleMenuService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Menu Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    private MenuService menuService;

    private RoleMenuService roleMenuService;

    /**
     * <p>
     * Get Menu page by Menu
     * </p>
     * @param page page
     * @param menu {@link Menu}
     * @return Page<Menu>
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping("/page")
    public IPage<Menu> page(Page<Menu> page, Menu menu) {
        return menuService.page(page, menu);
    }

    /**
     * <p>
     * Get Menu list by Menu
     * </p>
     * @param menu {@link Menu}
     * @return List<Menu>
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping("/list")
    public List<Menu> list(Menu menu) {
        return menuService.list(new LambdaQueryWrapper<>(menu).orderByAsc(Menu::getSeq));
    }

    @SaCheckPermission("system:menu:query")
    @GetMapping("/list-side")
    public List<Menu> getSideMenuByLoginUsername() {
        return menuService.getSideMenuByLoginUserId();
    }

    /**
     * <p>
     * Get Menu by id
     * </p>
     * @param id id
     * @return Menu
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping("/{id}")
    public Menu getById(@PathVariable("id") Long id) {
        return menuService.getById(id);
    }

    @SaCheckPermission("system:menu:query")
    @GetMapping("/get-menu-id-by-role/{roleId}")
    public Set<Long> getMenuIdsByRoleId(@PathVariable("roleId") Long roleId) {
        return roleMenuService.getMenuIdsByRoleId(roleId);
    }

    /**
     * <p>
     * Add Menu
     * </p>
     * @param menu {@link Menu}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:menu:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody Menu menu) {
        boolean bool = menuService.save(menu);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Menu
     * </p>
     * @param menu {@link Menu}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:menu:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody Menu menu) {
        boolean bool = menuService.updateById(menu);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Menu by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.DELETE)
    @SaCheckPermission("system:menu:delete")
    @PostMapping("/delete/{ids}")
    public R<Void> delete(@PathVariable("ids") Long[] ids) {
        return R.result(menuService.removeByIds(Arrays.asList(ids)));
    }

}

