package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.util.BeanCopyUtils;
import com.github.mengweijin.vita.system.constant.UserConst;
import com.github.mengweijin.vita.system.domain.entity.MenuDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.MenuVO;
import com.github.mengweijin.vita.system.enums.EMenuType;
import com.github.mengweijin.vita.system.enums.EYesNo;
import com.github.mengweijin.vita.system.mapper.MenuMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
public class MenuService extends CrudRepository<MenuMapper, MenuDO> {

    private UserService userService;

    private UserRoleService userRoleService;

    private RoleMenuService roleMenuService;

    @Override
    public boolean removeByIds(Collection<?> ids) {
        return super.removeByIds(ids);
    }

    public LambdaQueryWrapper<MenuDO> getQueryWrapper(MenuDO menu) {
        LambdaQueryWrapper<MenuDO> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(!Objects.isNull(menu.getId()), MenuDO::getId, menu.getId());
        wrapper.eq(!Objects.isNull(menu.getCreateBy()), MenuDO::getCreateBy, menu.getCreateBy());
        wrapper.eq(!Objects.isNull(menu.getUpdateBy()), MenuDO::getUpdateBy, menu.getUpdateBy());

        wrapper.eq(!Objects.isNull(menu.getParentId()), MenuDO::getParentId, menu.getParentId());
        wrapper.eq(StrValidator.isNotBlank(menu.getType()), MenuDO::getType, menu.getType());
        wrapper.eq(StrValidator.isNotBlank(menu.getDisabled()), MenuDO::getDisabled, menu.getDisabled());

        wrapper.gt(!Objects.isNull(menu.getSearchStartTime()), MenuDO::getCreateTime, menu.getSearchStartTime());
        wrapper.le(!Objects.isNull(menu.getSearchEndTime()), MenuDO::getCreateTime, menu.getSearchEndTime());

        if (StrValidator.isNotBlank(menu.getKeywords())) {
            wrapper.or(w -> w.like(MenuDO::getTitle, menu.getKeywords()));
            wrapper.or(w -> w.like(MenuDO::getPermission, menu.getKeywords()));
            wrapper.or(w -> w.like(MenuDO::getComponent, menu.getKeywords()));
        }
        return wrapper;
    }

    public List<MenuVO> listVO(MenuDO menuDO) {
        LambdaQueryWrapper<MenuDO> wrapper = this.getQueryWrapper(menuDO);
        List<MenuDO> menuList = this.list(wrapper.orderByAsc(MenuDO::getSeq));
        List<MenuVO> list = BeanCopyUtils.copyList(menuList, MenuVO.class);

        list.forEach(item -> item.setTitlePath(this.buildTitlePath(list, item.getId())));
        return list;
    }

    public Set<String> getMenuPermissionListByUsername(String username) {
        if (UserConst.ADMIN_USERNAME.equals(username)) {
            return this.lambdaQuery().select(MenuDO::getPermission).isNotNull(MenuDO::getPermission).list()
                    .stream().map(MenuDO::getPermission).collect(Collectors.toSet());
        }

        UserDO user = userService.getByUsername(username);
        Set<Long> roleIds = userRoleService.getRoleIdsByUserId(user.getId());
        Set<Long> menuIds = roleMenuService.getMenuIdsInRoleIds(roleIds);

        return this.lambdaQuery().select(MenuDO::getPermission).isNotNull(MenuDO::getPermission).in(MenuDO::getId, menuIds).list()
                .stream().map(MenuDO::getPermission).collect(Collectors.toSet());
    }

    public List<MenuDO> getSideMenuByUserId(Long userId) {
        if (userId.equals(UserConst.ADMIN_USER_ID)) {
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

    public String buildTitlePath(List<MenuVO> list, Long id) {
        List<String> titleList = new ArrayList<>();
        MenuVO menuVO = CollUtil.getFirst(list, item -> item.getId().equals(id));

        while(menuVO != null) {
            titleList.add(0, menuVO.getTitle());
            Long parentId = menuVO.getParentId();
            menuVO = CollUtil.getFirst(list, item -> item.getId().equals(parentId));
        }
        return String.join(Const.SLASH, titleList);
    }
}
