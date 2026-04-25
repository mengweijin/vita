package com.github.mengweijin.vita.framework.log.datachange.handler;

import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.constant.VitaConst;
import com.github.mengweijin.vita.framework.log.datachange.DiffModel;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.system.domain.entity.MenuDO;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.service.MenuService;
import com.github.mengweijin.vita.system.service.RoleService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author mengweijin
 * @since 2026/1/25
 */
@Component
public class RoleMenuReadableMessageHandler implements IReadableMessageHandler {

    private static final String ROLE_MENU_I18N_KEY_ADDED = "monitor.log.datachange.human.readable.role.menu.added";

    private static final String ROLE_MENU_I18N_KEY_MODIFIED = "monitor.log.datachange.human.readable.role.menu.modified";

    private static final String ROLE_MENU_I18N_KEY_REMOVED = "monitor.log.datachange.human.readable.role.menu.removed";

    @Override
    public boolean supported(String tableName) {
        return VitaConst.TABLE_VT_ROLE_MENU.equalsIgnoreCase(tableName);
    }

    @Override
    public List<String> buildMessages(Long businessId, List<DiffModel> changeData) {
        RoleService roleService = SpringUtil.getBean(RoleService.class);
        MenuService menuService = SpringUtil.getBean(MenuService.class);

        RoleDO role = roleService.getById(businessId);
        String roleName = role.getName();

        Set<Long> menuIdSet = new HashSet<>();
        for (DiffModel model : changeData) {
            if (model.getOldValue() != null) {
                menuIdSet.add(NumberUtil.parseLong(model.getOldValue()));
            }
            if (model.getNewValue() != null) {
                menuIdSet.add(NumberUtil.parseLong(model.getNewValue()));
            }
        }
        List<MenuDO> menuList = menuService.listByIds(menuIdSet);

        return changeData.stream().map(i -> {
            String oldValueMessage = formatMessage(menuList, NumberUtil.parseLong(i.getOldValue()));
            String newValueMessage = formatMessage(menuList, NumberUtil.parseLong(i.getNewValue()));

            return switch (i.getDiffType()) {
                case ADDED -> I18nUtils.msg(ROLE_MENU_I18N_KEY_ADDED, roleName, newValueMessage);
                case REMOVED -> I18nUtils.msg(ROLE_MENU_I18N_KEY_REMOVED, roleName, oldValueMessage);
                default -> I18nUtils.msg(ROLE_MENU_I18N_KEY_MODIFIED, roleName, oldValueMessage, newValueMessage);
            };
        }).toList();
    }

    private String formatMessage(List<MenuDO> menuList, Long menuId) {
        if (menuId == null) {
            return null;
        }
        return menuList.stream()
                .filter(i -> i.getId().equals(menuId))
                .map(menu -> StrUtil.format("{}[{}]", menu.getTitle(), menu.getPermission()))
                .findFirst()
                .orElse(null);
    }
}
