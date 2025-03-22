package com.github.mengweijin.vita.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.github.mengweijin.vita.system.service.MenuService;
import com.github.mengweijin.vita.system.service.RoleService;
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

    private MenuService menuService;

    private RoleService roleService;

    /**
     * 返回一个账号所拥有的权限码集合。这里的 loginId 即为用户登录名 username
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        Set<String> permissionSet = menuService.getMenuPermissionListByLoginUsername((String) loginId);
        return new ArrayList<>(permissionSet);
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Set<String> roleSet = roleService.getRoleCodeByUsername((String) loginId);
        return new ArrayList<>(roleSet);
    }

}

