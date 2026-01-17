package com.github.mengweijin.vita.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.service.MenuService;
import com.github.mengweijin.vita.system.service.RoleService;
import com.github.mengweijin.vita.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 自定义权限验证接口扩展
 * @author mengweijin
 * @since 2023/6/22
 */
@Component
@AllArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private UserService userService;

    private MenuService menuService;

    private RoleService roleService;

    /**
     * 返回一个账号所拥有的权限码集合。loginId 为 username
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        UserDO user = userService.getByUsername(loginId.toString());
        Set<String> permissionSet = menuService.getPermissionListByUserId(user.getId());
        return new ArrayList<>(permissionSet);
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        UserDO user = userService.getByUsername(loginId.toString());
        Set<String> roleSet = roleService.getRoleCodeByUserId(user.getId());
        return new ArrayList<>(roleSet);
    }

}

