package com.github.mengweijin.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.framework.domain.R;
import com.github.mengweijin.system.dto.MenuDTO;
import com.github.mengweijin.system.dto.MenuTreeDataDTO;
import com.github.mengweijin.system.entity.MenuDO;
import com.github.mengweijin.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author mengweijin
 * @date 2023/4/1
 */
@RestController
@RequestMapping("/vtl-menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @SaCheckPermission("system:menu:add")
    @PostMapping
    public R add(MenuDO menuDO) {
        boolean bool = menuService.save(menuDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:menu:edit")
    @PutMapping
    public R edit(MenuDO menuDO) {
        boolean bool = menuService.updateById(menuDO);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:menu:delete")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        boolean bool = menuService.removeById(id);
        return R.ajax(bool);
    }

    @SaCheckPermission("system:menu:delete")
    @DeleteMapping
    public R delete(Long[] ids) {
        boolean bool = menuService.removeBatchByIds(Arrays.asList(ids));
        return R.ajax(bool);
    }

    @GetMapping("/{id}")
    public MenuDO getById(@PathVariable("id") Long id) {
        return menuService.getById(id);
    }

    @SaCheckPermission("system:menu:list")
    @GetMapping("/page")
    public IPage<MenuDTO> page(Page<MenuDTO> page, MenuDTO dto) {
        return menuService.page(page, dto);
    }

    @GetMapping("/treeLeftSideData")
    public List<MenuTreeDataDTO> treeLeftSideData() {
        return menuService.treeLeftSideData();
    }

    @SaCheckPermission("system:menu:list")
    @GetMapping("/treeTableDataList")
    public List<MenuDTO> treeTableDataList(MenuDTO dto) {
        return menuService.treeTableDataList(dto);
    }

    @GetMapping("/titleHierarchy/{id}")
    public String titleHierarchyById(@PathVariable("id") Long id) {
        return menuService.titleHierarchyById(id);
    }

    @SaCheckPermission("system:menu:disabled")
    @PostMapping("/setDisabledValue/{id}")
    public R setDisabledValue(@PathVariable("id") Long id, boolean disabled) {
        boolean bool = menuService.setDisabledValue(id, disabled);
        return R.ajax(bool);
    }

    @GetMapping("/byRole/{roleId}")
    public List<Long> byRole(@PathVariable("roleId") Long roleId) {
        return menuService.byRole(roleId);
    }

    @GetMapping("/byDept/{deptId}")
    public List<Long> byDept(@PathVariable("deptId") Long deptId) {
        return menuService.byDept(deptId);
    }

    @GetMapping("/byPost/{postId}")
    public List<Long> byPost(@PathVariable("postId") Long postId) {
        return menuService.byPost(postId);
    }

    @GetMapping("/byUser/{userId}")
    public List<Long> byUser(@PathVariable("userId") Long userId) {
        return menuService.byUser(userId);
    }

}
