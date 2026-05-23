package com.github.mengweijin.vita.workflow.warmflow;

import cn.hutool.v7.core.math.NumberUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.system.service.RoleService;
import com.github.mengweijin.vita.system.service.UserPostService;
import com.github.mengweijin.vita.system.service.UserRoleService;
import com.github.mengweijin.vita.system.service.UserService;
import com.github.mengweijin.vita.workflow.enums.EWarmFlowHandlerType;
import lombok.AllArgsConstructor;
import org.dromara.warm.flow.core.handler.PermissionHandler;
import org.dromara.warm.flow.core.utils.StreamUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    private final UserService userService;

    private final RoleService roleService;

    private final UserRoleService userRoleService;

    private final UserPostService userPostService;

    /**
     * 获取当前操作用户所有权限
     */
    @Override
    public List<String> permissions() {
        // 办理人权限标识，比如用户，角色，部门，岗位等, 流程设计时未设置办理人或者ignore为true可不传 [按需传输]
        List<String> permissionList = new ArrayList<>();

        // 用户
        Long userId = LoginHelper.getSessionUserId();
        if (userId != null) {
            permissionList.add(EWarmFlowHandlerType.USER.getCode() + userId);
        }

        // 角色
        Set<Long> roleIds = roleService.getRoleIdsByUserId(userId);
        List<String> rolePermissionList = StreamUtils.toList(roleIds, roleId -> EWarmFlowHandlerType.ROLE.getCode() + roleId);
        permissionList.addAll(rolePermissionList);

        // 部门
        Long deptId = LoginHelper.getSessionUserDeptId();
        if (deptId != null) {
            permissionList.add(EWarmFlowHandlerType.DEPT.getCode() + deptId);
        }

        // 岗位
        Set<Long> postIds = userPostService.getPostIdsByUserId(userId);
        List<String> postPermissionList = StreamUtils.toList(postIds, postId -> EWarmFlowHandlerType.POST.getCode() + postId);
        permissionList.addAll(postPermissionList);

        return permissionList;
    }

    /**
     * 获取当前办理人
     *
     * @return 当前办理人
     */
    @Override
    public String getHandler() {
        Long userId = LoginHelper.getSessionUserId();
        return userId == null ? null : userId.toString();
    }

    /**
     * 转换办理人，比如设计器中预设了能办理的人，如果其中包含角色或者部门id等，可以通过此接口进行转换成用户id
     *
     * @return permissions：{user:1,role:1,dept:1,post:1}
     */
    @Override
    public List<String> convertPermissions(List<String> permissions) {
        Set<Long> userIds = new HashSet<>();
        // 把角色，部门，岗位转换成用户
        permissions.forEach(p -> {
            // 只取 : 后面的值，转换为 Long
            long id = NumberUtil.parseLong(p.split(Const.COLON, 2)[1]);

            if (p.startsWith(EWarmFlowHandlerType.ROLE.getCode())) {
                Set<Long> userIdsInRole = userRoleService.getUserIdsByRoleId(id);
                userIds.addAll(userIdsInRole);
            } else if (p.startsWith(EWarmFlowHandlerType.DEPT.getCode())) {
                Set<Long> userIdsInDept = userService.getUserIdsInDeptId(id);
                userIds.addAll(userIdsInDept);
            } else if (p.startsWith(EWarmFlowHandlerType.POST.getCode())) {
                Set<Long> userIdsInPost = userPostService.getUserIdsByPostId(id);
                userIds.addAll(userIdsInPost);
            } else {
                userIds.add(id);
            }
        });
        return userIds.stream().map(Object::toString).toList();
    }
}
