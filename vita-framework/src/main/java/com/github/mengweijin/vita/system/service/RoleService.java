package com.github.mengweijin.vita.system.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.monitor.service.LogDataChangeService;
import com.github.mengweijin.vita.system.domain.bo.RolePermissionBO;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.domain.entity.RoleMenuDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.RoleVO;
import com.github.mengweijin.vita.system.enums.dict.EMessageCategory;
import com.github.mengweijin.vita.system.mapper.RoleMapper;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  Role Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class RoleService extends BaseVitaService<RoleMapper, RoleDO, RoleVO> {

    private RoleMenuService roleMenuService;

    private UserRoleService userRoleService;

    private LogDataChangeService logDataChangeService;

    @Override
    public boolean removeByIds(Collection<?> roleIds) {
        long userCount = userRoleService.countUserInRoleIds(roleIds);
        if(userCount > 0) {
            throw new ClientException(I18nUtils.msg("system.role.delete.hasUser"));
        }
        return super.removeByIds(roleIds);
    }

    @Override
    public LambdaQueryWrapper<RoleDO> buildQueryWrapper(RoleDO role) {
        LambdaQueryWrapper<RoleDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(role.getId() != null, RoleDO::getId, role.getId());
        wrapper.eq(StrUtil.isNotBlank(role.getDisabled()), RoleDO::getDisabled, role.getDisabled());
        wrapper.eq(role.getCreateBy() != null, RoleDO::getCreateBy, role.getCreateBy());
        wrapper.eq(role.getUpdateBy() != null, RoleDO::getUpdateBy, role.getUpdateBy());
        wrapper.gt(role.getStartCreateTime() != null, RoleDO::getCreateTime, role.getStartCreateTime());
        wrapper.le(role.getEndCreateTime() != null, RoleDO::getCreateTime, role.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(role.getName()), RoleDO::getName, role.getName());
        wrapper.like(StrUtil.isNotBlank(role.getCode()), RoleDO::getCode, role.getCode());
        return wrapper;
    }

    public Set<String> getRoleCodeByUserId(Long userId) {
        UserService userService = SpringUtil.getBean(UserService.class);
        UserDO user = userService.getById(userId);
        if (LoginHelper.isAdmin(user.getId(), user.getUsername())) {
            return this.list().stream().map(RoleDO::getCode).collect(Collectors.toSet());
        }
        return this.getBaseMapper().getRoleCodeByUserId(userId);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean setMenuPermission(RolePermissionBO bo) {
        Set<Long> beforeMenuIds = roleMenuService.getMenuIdsByRoleId(bo.getRoleId());

        roleMenuService.removeByRoleId(bo.getRoleId());

        List<RoleMenuDO> collect = bo.getMenuIds().stream().map(menuId -> {
            RoleMenuDO roleMenu = new RoleMenuDO();
            roleMenu.setRoleId(bo.getRoleId());
            roleMenu.setMenuId(menuId);
            return roleMenu;
        }).collect(Collectors.toList());
        if(!collect.isEmpty()) {
            roleMenuService.saveBatch(collect, Constants.DEFAULT_BATCH_SIZE);
        }

        // logDataChangeService.saveWhenListChange(VitaConst.TABLE_VT_ROLE_MENU, bo.getRoleId(), List.copyOf(beforeMenuIds), List.copyOf(bo.getMenuIds()));

        return true;
    }

    public RoleDO getByCode(String code) {
        return this.lambdaQuery().eq(RoleDO::getCode, code).one();
    }

    public void sendPermissionChangeMessageToOnlineUsers(@NotNull Long roleId) {
        // 角色下所有用户 id
        Set<Long> userIds = userRoleService.getUserIdsByRoleId(roleId);
        UserService userService = SpringUtil.getBean(UserService.class);

        // 获取所有在线 session
        List<String> sessionIdList = StpUtil.searchSessionId("", 0, -1, false);
        Set<String> usernameSet = new HashSet<>();
        for (String sessionId : sessionIdList) {
            // 根据会话id，查询对应的 SaSession 对象，此处一个 SaSession 对象即代表一个登录的账号
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            String username = session.getLoginId().toString();
            usernameSet.add(username);
        }

        // 在线用户 id
        Set<Long> idSet = userService.getUserIdsInUsernames(usernameSet);

        // 求交集
        userIds.retainAll(idSet);

        MessageService messageService = SpringUtil.getBean(MessageService.class);
        String messageTitle = I18nUtils.msg("system.message.role.permission.change.title");
        String messageContent = I18nUtils.msg("system.message.role.permission.change.content");
        messageService.sendMessageToUsersAsync(EMessageCategory.SYSTEM, messageTitle, messageContent, userIds);
    }
}
