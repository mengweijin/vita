package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.RolePermissionBO;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.enums.EYesNo;
import com.github.mengweijin.vita.system.service.RoleService;
import com.github.mengweijin.vita.system.service.UserRoleService;
import jakarta.validation.Valid;
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
 *  Role Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/role")
public class RoleController {

    private RoleService roleService;

    private UserRoleService userRoleService;

    /**
     * <p>
     * Get Role page by Role
     * </p>
     * @param page page
     * @param role {@link RoleDO}
     * @return Page<Role>
     */
    @SaCheckPermission("system:role:select")
    @GetMapping("/page")
    public IPage<RoleDO> page(Page<RoleDO> page, RoleDO role) {
        LambdaQueryWrapper<RoleDO> wrapper = roleService.getQueryWrapper(role);
        return roleService.page(page, wrapper);
    }

    /**
     * <p>
     * Get Role list by Role
     * </p>
     * @param role {@link RoleDO}
     * @return List<Role>
     */
    @SaCheckPermission("system:role:select")
    @GetMapping("/list")
    public List<RoleDO> list(RoleDO role) {
        return roleService.list(new LambdaQueryWrapper<>(role).eq(RoleDO::getDisabled, EYesNo.N.getValue()));
    }

    /**
     * <p>
     * Get Role ids by id
     * </p>
     *
     * @param userId userId
     * @return Role
     */
    @SaCheckPermission("system:role:select")
    @GetMapping("/list-role-ids-by-user-id/{userId}")
    public Set<Long> getRoleIdsByUserId(@PathVariable("userId") Long userId) {
        return userRoleService.getRoleIdsByUserId(userId);
    }

    /**
     * <p>
     * Get Role by id
     * </p>
     * @param id id
     * @return Role
     */
    @SaCheckPermission("system:role:select")
    @GetMapping("/{id}")
    public RoleDO getById(@PathVariable("id") Long id) {
        return roleService.getById(id);
    }

    /**
     * <p>
     * Add Role
     * </p>
     * @param role {@link RoleDO}
     */
    @Log(operationType = EOperationType.INSERT)
    @SaCheckPermission("system:role:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody RoleDO role) {
        boolean bool = roleService.save(role);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Role
     * </p>
     * @param role {@link RoleDO}
     */
    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:role:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody RoleDO role) {
        boolean bool = roleService.updateById(role);
        return R.result(bool);
    }

    @Log(operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:role:update")
    @PostMapping("/set-permission")
    public R<Void> setPermission(@Valid @RequestBody RolePermissionBO rolePermissionBO) {
        boolean bool = roleService.setMenuPermission(rolePermissionBO);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Role by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:role:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(roleService.removeByIds(Arrays.asList(ids)));
    }

}

