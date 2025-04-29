package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.MenuDO;
import com.github.mengweijin.vita.system.domain.vo.MenuVO;
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
     * @param menu {@link MenuDO}
     * @return Page<Menu>
     */
    @SaCheckPermission("system:menu:select")
    @GetMapping("/page")
    public IPage<MenuDO> page(Page<MenuDO> page, MenuDO menu) {
        LambdaQueryWrapper<MenuDO> wrapper = menuService.getQueryWrapper(menu);
        return menuService.page(page, wrapper);
    }

    /**
     * <p>
     * Get Menu list by Menu
     * </p>
     * @param menu {@link MenuDO}
     * @return List<Menu>
     */
    @SaCheckPermission("system:menu:select")
    @GetMapping("/list")
    public List<MenuVO> list(MenuDO menu) {
        return menuService.listVO(menu);
    }

    /**
     * <p>
     * Get Menu by id
     * </p>
     * @param id id
     * @return Menu
     */
    @SaCheckPermission("system:menu:select")
    @GetMapping("/{id}")
    public MenuDO getById(@PathVariable("id") Long id) {
        return menuService.getById(id);
    }

    @SaCheckPermission("system:menu:select")
    @GetMapping("/get-menu-id-by-role/{roleId}")
    public Set<Long> getMenuIdsByRoleId(@PathVariable("roleId") Long roleId) {
        return roleMenuService.getMenuIdsByRoleId(roleId);
    }

    /**
     * <p>
     * Add Menu
     * </p>
     * @param menu {@link MenuDO}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:menu:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody MenuDO menu) {
        boolean bool = menuService.save(menu);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Menu
     * </p>
     * @param menu {@link MenuDO}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:menu:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody MenuDO menu) {
        boolean bool = menuService.updateById(menu);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Menu by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:menu:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(menuService.removeByIds(Arrays.asList(ids)));
    }

}

