package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.util.BeanCopyUtils;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.constant.UserConst;
import com.github.mengweijin.vita.system.domain.bo.PasswordChangeBO;
import com.github.mengweijin.vita.system.domain.bo.PasswordResetBO;
import com.github.mengweijin.vita.system.domain.bo.UserBO;
import com.github.mengweijin.vita.system.domain.bo.UserRoleBO;
import com.github.mengweijin.vita.system.domain.entity.UserAvatarDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.UserSensitiveVO;
import com.github.mengweijin.vita.system.domain.vo.UserVO;
import com.github.mengweijin.vita.system.service.UserAvatarService;
import com.github.mengweijin.vita.system.service.UserRoleService;
import com.github.mengweijin.vita.system.service.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.math.NumberUtil;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * User Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/user")
public class UserController {

    private static final String LOG_TITLE = "用户管理";

    private UserService userService;

    private UserAvatarService userAvatarService;

    private UserRoleService userRoleService;

    /**
     * <p>
     * Get User page by User
     * </p>
     *
     * @param page page
     * @param user {@link UserDO}
     * @return Page<User>
     */
    @SaCheckPermission("system:user:select")
    @GetMapping("/page")
    public IPage<UserVO> page(Page<UserDO> page, UserDO user) {
        LambdaQueryWrapper<UserDO> wrapper = userService.getQueryWrapper(user);
        IPage<UserDO> userPage = userService.page(page, wrapper);
        return BeanCopyUtils.copyPage(userPage, UserVO.class);
    }

    /**
     * <p>
     * Get User list by User
     * </p>
     *
     * @param userDO {@link UserDO}
     * @return List<User>
     */
    @SaCheckPermission("system:user:select")
    @GetMapping("/list")
    public List<UserVO> list(UserDO userDO) {
        List<UserDO> userList = userService.list(new LambdaQueryWrapper<>(userDO));
        return BeanCopyUtils.copyList(userList, UserVO.class);
    }

    /**
     * <p>
     * Get User by id
     * </p>
     *
     * @param id id
     * @return User
     */
    @SaCheckPermission("system:user:select")
    @GetMapping("/{id}")
    public UserVO getById(@PathVariable("id") Long id) {
        UserDO user = userService.getById(id);
        return BeanCopyUtils.copyBean(user, UserVO.class);
    }

    /**
     * <p>
     * Get Sensitive User by id
     * </p>
     *
     * @param id id
     * @return User
     */
    @SaCheckPermission("system:user:sensitive")
    @GetMapping("/get-sensitive-info/{id}")
    public UserSensitiveVO getSensitiveUserById(@PathVariable("id") Long id) {
        return userService.getSensitiveUserById(id);
    }

    /**
     * <p>
     * Add User
     * </p>
     *
     * @param userBO {@link UserDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT, saveRequestData = false)
    @SaCheckPermission("system:user:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody UserBO userBO) {
        userService.saveOrUpdate(userBO);
        return R.ok();
    }

    /**
     * <p>
     * Update User
     * </p>
     *
     * @param userBO {@link UserDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE, saveRequestData = false)
    @SaCheckPermission("system:user:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody UserBO userBO) {
        userService.saveOrUpdate(userBO);
        return R.ok();
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:user:update")
    @PostMapping("/set-disabled")
    public R<Void> setDisabled(@NotNull Long id, @NotBlank String disabled) {
        boolean bool = userService.setDisabled(id, disabled);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete User by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:user:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        List<Long> list = Arrays.asList(ids);
        boolean isAdmin = list.stream().anyMatch(id -> UserConst.ADMIN_USER_ID == NumberUtil.parseLong(CharSequenceUtil.toString(id)));
        if (isAdmin) {
            throw new ClientException("Can't delete admin account!");
        }
        return R.result(userService.removeByIds(list));
    }

    /**
     * <p>
     * change password
     * </p>
     *
     * @param bo {@link PasswordChangeBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE, saveRequestData = false)
    @SaCheckPermission("system:user:changePassword")
    @PostMapping("/change-password")
    public R<Void> changePassword(@Validated @RequestBody PasswordChangeBO bo) {
        String username = LoginHelper.getLoginUser().getUsername();
        boolean bool = userService.changePassword(username, bo.getPassword(), bo.getNewPassword());
        return R.result(bool);
    }

    /**
     * <p>
     * reset a user password
     * </p>
     *
     * @param bo {@link PasswordResetBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE, saveRequestData = false)
    @SaCheckPermission("system:user:resetPassword")
    @PostMapping("/reset-password")
    public R<Void> resetPassword(@Validated @RequestBody PasswordResetBO bo) {
        boolean bool = userService.updatePassword(bo.getUsername(), bo.getPassword());
        return R.result(bool);
    }

    /**
     * <p>
     * set user avatar
     * </p>
     *
     * @param userAvatar {@link UserAvatarDO}
     */
    @SaCheckPermission("system:user:setAvatar")
    @PostMapping("/set-avatar")
    public R<Void> setAvatar(@Validated @RequestBody UserAvatarDO userAvatar) {
        boolean bool = userAvatarService.setAvatar(userAvatar);
        return R.result(bool);
    }

    /**
     * <p>
     * set user Roles
     * </p>
     *
     * @param bo {@link UserRoleBO}
     */
    @SaCheckPermission("system:user:setRoles")
    @PostMapping("/set-roles")
    public R<Void> setRoles(@Validated @RequestBody UserRoleBO bo) {
        userRoleService.setUserRoles(bo.getUserId(), bo.getRoleIds());
        return R.ok();
    }
}

