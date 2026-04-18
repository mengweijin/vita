package com.github.mengweijin.vita.workflow.warm;

import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.system.service.RoleService;
import com.github.mengweijin.vita.system.service.UserRoleService;
import com.github.mengweijin.vita.system.service.UserService;
import lombok.AllArgsConstructor;
import org.dromara.warm.flow.core.handler.PermissionHandler;
import org.dromara.warm.flow.core.utils.StreamUtils;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mengweijin
 * @since 2026/4/12
 */
@Component
@AllArgsConstructor
public class WarmFlowPermissionHandler implements PermissionHandler {

    private static final String ROLE_PREFIX = "role:";

    private static final String DEPT_PREFIX = "dept:";

    private final UserService userService;

    private final RoleService roleService;

    private final UserRoleService userRoleService;

    /**
     * 获取当前操作用户所有权限
     */
    @Override
    public List<String> permissions() {
        // 办理人权限标识，比如用户，角色，部门等, 流程设计时未设置办理人或者ignore为true可不传 [按需传输]
        Long userId = LoginHelper.getSessionUserId();
        // 角色
        Set<Long> roleIds = roleService.getRoleIdsByUserId(userId);
        List<String> permissionList = StreamUtils.toList(roleIds, roleId -> ROLE_PREFIX + roleId);
        // 当前用户
        if (userId != null) {
            permissionList.add(String.valueOf(userId));
        }
        // 部门
        Long deptId = LoginHelper.getSessionUserDeptId();
        if (deptId != null) {
            permissionList.add(DEPT_PREFIX + deptId);
        }
        return permissionList;
    }

    /**
     * 获取当前办理人
     * @return 当前办理人
     */
    @Override
    public String getHandler() {
        Long userId = LoginHelper.getSessionUserId();
        return userId == null ? null : userId.toString();
    }

    /**
     * 转换办理人，比如设计器中预设了能办理的人，如果其中包含角色或者部门id等，可以通过此接口进行转换成用户id
     * @return permissions：{role:1,dept:1}
     */
    @Override
    public List<String> convertPermissions(List<String> permissions) {
        Set<Long> userIds = new HashSet<>();
        // 把角色，部门转换成用户
        permissions.forEach( p -> {
            if(p.startsWith(ROLE_PREFIX)) {
                Set<Long> userIdsInRole = userRoleService.getUserIdsByRoleId(Long.valueOf(p.substring(ROLE_PREFIX.length())));
                userIds.addAll(userIdsInRole);
            } else if(p.startsWith(DEPT_PREFIX)) {
                Set<Long> userIdsInDept = userService.getUserIdsInDeptId(Long.valueOf(p.substring(DEPT_PREFIX.length())));
                userIds.addAll(userIdsInDept);
            } else {
                userIds.add(Long.valueOf(p));
            }
        });
        return userIds.stream().map(Object::toString).toList();
    }
}
