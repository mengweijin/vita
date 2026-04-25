package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.MenuBO;
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
 * Menu Controller
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

    private static final String LOG_TITLE = "菜单管理";

    private MenuService menuService;

    private RoleMenuService roleMenuService;

    @GetMapping("/list/sideMenus")
    public List<MenuVO> listSideMenus() {
        List<MenuDO> list = menuService.getSideMenuByUserId(LoginHelper.getSessionUserId());
        return MapstructUtils.getConverter().convert(list, MenuVO.class);
    }

    /**
     * <p>
     * Get Menu list by Menu
     * </p>
     *
     * @param menu {@link MenuDO}
     * @return List<Menu>
     */
    @SaCheckPermission("system:menu:select")
    @GetMapping("/list")
    public List<MenuVO> list(MenuDO menu) {
        LambdaQueryWrapper<MenuDO> wrapper = menuService.buildQueryWrapper(menu);
        return menuService.listVo(wrapper.orderByAsc(MenuDO::getSeq));
    }

    /**
     * <p>
     * Get Menu by id
     * </p>
     *
     * @param id id
     * @return Menu
     */
    @GetMapping("/{id}")
    public MenuVO getById(@PathVariable("id") Long id) {
        return menuService.getVoById(id);
    }

    @GetMapping("/query/menuIds/by/roleId/{roleId}")
    public Set<Long> queryMenuIdsByRoleId(@PathVariable("roleId") Long roleId) {
        return roleMenuService.getMenuIdsByRoleId(roleId);
    }

    /**
     * <p>
     * Add Menu
     * </p>
     *
     * @param menu {@link MenuDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:menu:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody MenuBO menu) {
        boolean bool = menuService.saveByBo(menu);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Menu
     * </p>
     *
     * @param menu {@link MenuDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:menu:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody MenuBO menu) {
        boolean bool = menuService.updateByBoById(menu);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Menu by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:menu:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(menuService.removeByIds(Arrays.asList(ids)));
    }

}

