package com.github.mengweijin.vitality.framework.satoken;

import cn.dev33.satoken.stp.StpInterface;
import com.github.mengweijin.vitality.system.dto.VtlRoleDTO;
import com.github.mengweijin.vitality.system.entity.VtlMenu;
import com.github.mengweijin.vitality.system.entity.VtlRole;
import com.github.mengweijin.vitality.system.service.VtlMenuService;
import com.github.mengweijin.vitality.system.service.VtlRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 * @author mengweijin
 * @date 2023/6/22
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private VtlMenuService menuService;
    @Autowired
    private VtlRoleService roleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        List<VtlMenu> menuList = menuService.getMenuByLoginUser((Long) loginId);
        return menuList.stream().map(VtlMenu::getPermission).toList();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<VtlRoleDTO> roleList = roleService.getByUserId((Long) loginId);
        return roleList.stream().map(VtlRole::getCode).toList();
    }

}

