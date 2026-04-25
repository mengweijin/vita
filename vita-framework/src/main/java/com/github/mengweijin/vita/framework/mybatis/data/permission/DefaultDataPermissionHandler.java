package com.github.mengweijin.vita.framework.mybatis.data.permission;

import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.service.DeptService;
import com.github.mengweijin.vita.system.service.UserRoleService;
import com.github.mengweijin.vita.system.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author mengweijin
 * @since 2022/11/20
 */
public class DefaultDataPermissionHandler extends BaseDataPermissionHandler {
    @Override
    protected Long getLoginUserId() {
        return LoginHelper.getSessionUserId();
    }

    @Override
    protected boolean isAdmin() {
        return LoginHelper.isAdmin();
    }

    @Override
    protected List<Long> getLoginUserDeptIdList() {
        UserService userService = SpringUtil.getBean(UserService.class);
        DeptService deptService = SpringUtil.getBean(DeptService.class);
        Long userId = LoginHelper.getSessionUserId();
        UserDO user = userService.lambdaQuery().select(UserDO::getDeptId).eq(UserDO::getId, userId).one();
        return deptService.getParentIds(user.getDeptId());
    }

    @Override
    protected List<Long> getLoginUserRoleIdList() {
        UserRoleService userRoleService = SpringUtil.getBean(UserRoleService.class);
        Set<Long> set = userRoleService.getRoleIdsByUserId(getLoginUserId());
        return new ArrayList<>(set);
    }
}
