package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.enums.dict.EYesNo;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.properties.VitaProperties;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.RoleBO;
import com.github.mengweijin.vita.system.domain.bo.RolePermissionBO;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.domain.vo.RoleVO;
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
 * Role Controller
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

    private static final String LOG_TITLE = "角色管理";

    private RoleService roleService;

    private UserRoleService userRoleService;

    private VitaProperties vitaProperties;

    /**
     * <p>
     * Get Role page by Role
     * </p>
     *
     * @param page page
     * @param role {@link RoleDO}
     * @return Page<Role>
     */
    @SaCheckPermission("system:role:select")
    @GetMapping("/page")
    public PageQuery<RoleVO> page(PageQuery<RoleDO> page, RoleDO role) {
        LambdaQueryWrapper<RoleDO> wrapper = roleService.buildQueryWrapper(role);
        return roleService.pageVo(page, wrapper.orderByAsc(RoleDO::getSeq));
    }

    /**
     * <p>
     * Get Role list by Role
     * </p>
     *
     * @param role {@link RoleDO}
     * @return List<Role>
     */
    @SaCheckPermission("system:role:select")
    @GetMapping("/list")
    public List<RoleVO> list(RoleDO role) {
        return roleService.listVo(Wrappers.lambdaQuery(role).eq(RoleDO::getDisabled, EYesNo.N.getValue()));
    }

    /**
     * <p>
     * Get Role ids by id
     * </p>
     *
     * @param userId userId
     * @return Role
     */
    @GetMapping("/query/roleIds/by/userId/{userId}")
    public Set<Long> queryRoleIdsByUserId(@PathVariable("userId") Long userId) {
        return userRoleService.getRoleIdsByUserId(userId);
    }

    @GetMapping("/query/defaultRole")
    public RoleVO queryDefaultRole() {
        String defaultRoleCode = vitaProperties.getUser().getDefaultRoleCode();
        RoleDO roleDO = roleService.getByCode(defaultRoleCode);
        return MapstructUtils.getConverter().convert(roleDO, RoleVO.class);
    }

    /**
     * <p>
     * Get Role by id
     * </p>
     *
     * @param id id
     * @return Role
     */
    @GetMapping("/{id}")
    public RoleVO getById(@PathVariable("id") Long id) {
        return roleService.getVoById(id);
    }

    /**
     * <p>
     * Add Role
     * </p>
     *
     * @param role {@link RoleBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:role:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody RoleBO role) {
        boolean bool = roleService.saveByBo(role);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Role
     * </p>
     *
     * @param role {@link RoleBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:role:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody RoleBO role) {
        boolean bool = roleService.updateByBoById(role);
        return R.result(bool);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @SaCheckPermission("system:role:setPermissions")
    @PostMapping("/set/permissions")
    public R<Void> setPermissions(@Valid @RequestBody RolePermissionBO rolePermissionBO) {
        boolean bool = roleService.setMenuPermission(rolePermissionBO);
        if (bool) {
            roleService.sendPermissionChangeMessageToOnlineUsers(rolePermissionBO.getRoleId());
        }
        return R.result(bool);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @SaCheckPermission("system:role:setUsers")
    @PostMapping("/set/users/{roleId}/{userIds}")
    public R<Void> setUsers(@PathVariable("roleId") Long roleId, @PathVariable("userIds") Long[] userIds) {
        return R.result(userRoleService.addUsers(roleId, Arrays.asList(userIds)));
    }

    /**
     * <p>
     * Delete Role by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:role:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(roleService.removeByIds(Arrays.asList(ids)));
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:role:setUsers")
    @PostMapping("/remove/by/roleId/in/userIds/{roleId}/{userIds}")
    public R<Void> removeByRoleIdInUserIds(@PathVariable("roleId") Long roleId, @PathVariable("userIds") Long[] userIds) {
        return R.result(userRoleService.removeByRoleIdInUserIds(roleId, Arrays.asList(userIds)));
    }
}
