package com.github.mengweijin.vita.framework.log.datachange.handler;

import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.log.datachange.DiffModel;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.system.constant.VitaConst;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.service.RoleService;
import com.github.mengweijin.vita.system.service.UserService;
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
public class UserRoleReadableMessageHandler implements IReadableMessageHandler {

    private static final String USER_ROLE_I18N_KEY_ADDED = "monitor.log.datachange.human.readable.user.role.added";

    private static final String USER_ROLE_I18N_KEY_MODIFIED = "monitor.log.datachange.human.readable.user.role.modified";

    private static final String USER_ROLE_I18N_KEY_REMOVED = "monitor.log.datachange.human.readable.user.role.removed";

    @Override
    public boolean supported(String tableName) {
        return VitaConst.TABLE_VT_USER_ROLE.equalsIgnoreCase(tableName);
    }

    @Override
    public List<String> buildMessages(Long businessId, List<DiffModel> changeData) {
        UserService userService = SpringUtil.getBean(UserService.class);
        RoleService roleService = SpringUtil.getBean(RoleService.class);

        UserDO user = userService.getById(businessId);
        String userNickname = user.getNickname();

        Set<Long> roleIdSet = new HashSet<>();
        for (DiffModel model : changeData) {
            if(model.getOldValue() != null) {
                roleIdSet.add(NumberUtil.parseLong(model.getOldValue()));
            }
            if(model.getNewValue() != null) {
                roleIdSet.add(NumberUtil.parseLong(model.getNewValue()));
            }
        }
        List<RoleDO> roleList = roleService.listByIds(roleIdSet);

        return changeData.stream().map(i -> {
            String oldValueMessage = formatMessage(roleList, NumberUtil.parseLong(i.getOldValue()));
            String newValueMessage = formatMessage(roleList, NumberUtil.parseLong(i.getNewValue()));

            return switch (i.getDiffType()) {
                case ADDED -> I18nUtils.msg(USER_ROLE_I18N_KEY_ADDED, userNickname, newValueMessage);
                case REMOVED -> I18nUtils.msg(USER_ROLE_I18N_KEY_REMOVED, userNickname, oldValueMessage);
                default -> I18nUtils.msg(USER_ROLE_I18N_KEY_MODIFIED, userNickname, oldValueMessage, newValueMessage);
            };
        }).toList();
    }

    private String formatMessage(List<RoleDO> roleList, Long roleId) {
        if(roleId == null) {
            return null;
        }
        return roleList.stream()
                .filter(i -> i.getId().equals(roleId))
                .map(role -> StrUtil.format("{}[{}]", role.getName(), role.getCode()))
                .findFirst()
                .orElse(null);
    }

}
