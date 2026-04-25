package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.constant.VitaConst;
import com.github.mengweijin.vita.framework.enums.dict.EMenuType;
import com.github.mengweijin.vita.framework.enums.dict.EYesNo;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.system.domain.entity.MenuDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.MenuVO;
import com.github.mengweijin.vita.system.mapper.MenuMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * Menu Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class MenuService extends BaseVitaService<MenuMapper, MenuDO, MenuVO> {

    private UserService userService;

    private UserRoleService userRoleService;

    private RoleMenuService roleMenuService;

    @Override
    public boolean removeByIds(Collection<?> ids) {
        Long count = this.lambdaQuery().in(MenuDO::getParentId, ids).count();
        if (count > 0) {
            throw new ClientException(I18nUtils.msg("system.menu.delete.hasChildren"));
        }
        return super.removeByIds(ids);
    }

    @Override
    public LambdaQueryWrapper<MenuDO> buildQueryWrapper(MenuDO menu) {
        LambdaQueryWrapper<MenuDO> wrapper = Wrappers.lambdaQuery();

        wrapper.eq(menu.getId() != null, MenuDO::getId, menu.getId());
        wrapper.eq(menu.getParentId() != null, MenuDO::getParentId, menu.getParentId());
        wrapper.eq(StrUtil.isNotBlank(menu.getType()), MenuDO::getType, menu.getType());
        wrapper.eq(StrUtil.isNotBlank(menu.getDisabled()), MenuDO::getDisabled, menu.getDisabled());
        wrapper.eq(menu.getCreateBy() != null, MenuDO::getCreateBy, menu.getCreateBy());
        wrapper.eq(menu.getUpdateBy() != null, MenuDO::getUpdateBy, menu.getUpdateBy());
        wrapper.gt(menu.getStartCreateTime() != null, MenuDO::getCreateTime, menu.getStartCreateTime());
        wrapper.le(menu.getEndCreateTime() != null, MenuDO::getCreateTime, menu.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(menu.getTitle()), MenuDO::getTitle, menu.getTitle());
        wrapper.like(StrUtil.isNotBlank(menu.getPermission()), MenuDO::getPermission, menu.getPermission());
        wrapper.like(StrUtil.isNotBlank(menu.getUrl()), MenuDO::getUrl, menu.getUrl());
        return wrapper;
    }

    public Set<String> getPermissionListByUserId(Long userId) {
        UserDO user = userService.getById(userId);
        if (LoginHelper.isAdmin(user.getId(), user.getUsername())) {
            return this.lambdaQuery()
                    .select(MenuDO::getPermission)
                    .isNotNull(MenuDO::getPermission)
                    .list()
                    .stream()
                    .map(MenuDO::getPermission)
                    .collect(Collectors.toSet());
        }

        Set<Long> roleIds = userRoleService.getRoleIdsByUserId(user.getId());
        Set<Long> menuIds = roleMenuService.getMenuIdsInRoleIds(roleIds);

        return this.lambdaQuery().select(MenuDO::getPermission)
                .isNotNull(MenuDO::getPermission)
                .in(MenuDO::getId, menuIds)
                .list()
                .stream()
                .map(MenuDO::getPermission)
                .collect(Collectors.toSet());
    }

    public List<MenuDO> getSideMenuByUserId(Long userId) {
        if (userId.equals(VitaConst.USER_ADMIN_ID)) {
            return this.lambdaQuery().eq(MenuDO::getDisabled, EYesNo.N.getValue()).ne(MenuDO::getType, EMenuType.BTN.getValue()).list();
        }

        Set<Long> roleIds = userRoleService.getRoleIdsByUserId(userId);
        Set<Long> menuIds = roleMenuService.getMenuIdsInRoleIds(roleIds);
        // 这里排除掉按钮类型的和已禁用的菜单
        return this.getBaseMapper().selectByIds(menuIds).stream()
                .filter(m -> EYesNo.N.getValue().equalsIgnoreCase(m.getDisabled()))
                .filter(m -> !EMenuType.BTN.getValue().equalsIgnoreCase(m.getType()))
                .toList();
    }

}
