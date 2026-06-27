package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckSafe;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.util.EnumUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.constant.VitaConst;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EMessageType;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.enums.dict.ESafeMode;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.properties.VitaProperties;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.framework.util.TotpUtils;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.monitor.domain.vo.SaSessionVO;
import com.github.mengweijin.vita.monitor.domain.vo.SaTerminalInfoVO;
import com.github.mengweijin.vita.system.domain.bo.OpenSafeBO;
import com.github.mengweijin.vita.system.domain.bo.PasswordChangeBO;
import com.github.mengweijin.vita.system.domain.bo.PasswordResetBO;
import com.github.mengweijin.vita.system.domain.bo.UserBO;
import com.github.mengweijin.vita.system.domain.bo.UserBasicInformationBO;
import com.github.mengweijin.vita.system.domain.bo.UserRoleBO;
import com.github.mengweijin.vita.system.domain.entity.UserAvatarDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.TotpVO;
import com.github.mengweijin.vita.system.domain.vo.user.UserProfileVO;
import com.github.mengweijin.vita.system.domain.vo.user.UserSessionVO;
import com.github.mengweijin.vita.system.domain.vo.user.UserStoreVO;
import com.github.mengweijin.vita.system.domain.vo.user.UserVO;
import com.github.mengweijin.vita.system.handler.secondaryauth.ISecondaryAuthHandler;
import com.github.mengweijin.vita.system.handler.secondaryauth.SecondaryAuthHandleFactory;
import com.github.mengweijin.vita.system.service.MessageService;
import com.github.mengweijin.vita.system.service.UserAvatarService;
import com.github.mengweijin.vita.system.service.UserRoleService;
import com.github.mengweijin.vita.system.service.UserService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private VitaProperties vitaProperties;

    private MessageService messageService;

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
    public PageQuery<UserVO> page(PageQuery<UserDO> page, UserDO user) {
        LambdaQueryWrapper<UserDO> wrapper = userService.buildQueryWrapper(user);
        return userService.pageVo(page, wrapper);
    }

    @GetMapping("/page/by/role/{roleId}")
    public PageQuery<UserVO> pageByRole(@PathVariable("roleId") Long roleId, PageQuery<UserDO> pageQuery, UserDO user) {
        IPage<UserDO> userPage = userService.pageByRole(roleId, pageQuery.toPage(), user);
        return userService.toVoPageQuery(userPage);
    }

    @GetMapping("/page/by/post/{postId}")
    public PageQuery<UserVO> pageByPost(@PathVariable("postId") Long postId, PageQuery<UserDO> pageQuery, UserDO user) {
        IPage<UserDO> userPage = userService.pageByPost(postId, pageQuery.toPage(), user);
        return userService.toVoPageQuery(userPage);
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
        return userService.listVo(Wrappers.lambdaQuery(userDO));
    }

    /**
     * <p>
     * Get User by id
     * </p>
     *
     * @param id id
     * @return User
     */
    @GetMapping("/{id}")
    public UserVO getById(@PathVariable("id") Long id) {
        return userService.getVoById(id);
    }

    @GetMapping("/query/userStoreVO")
    public UserStoreVO queryUserStoreVO() {
        return userService.getUserStoreVO();
    }

    @GetMapping("/query/userProfileVO")
    public UserProfileVO queryUserProfileVO() {
        Long userId = LoginHelper.getSessionUserId();
        return userService.getUserProfileVO(userId);
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

    @SaCheckPermission("system:user:update")
    @GetMapping("/query/bo/{id}")
    public UserBO queryUserBO(@PathVariable("id") Long id) {
        return userService.getUserBO(id);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE, saveRequestData = false)
    @PostMapping("/update/basicInformation")
    public R<Void> updateBasicInformation(@RequestBody UserBasicInformationBO bo) {
        boolean bool = userService.updateBasicInformation(bo);
        return R.result(bool);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:user:update")
    @PostMapping("/set/disabled")
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
        boolean isAdmin = list.stream().anyMatch(id -> VitaConst.USER_ADMIN_ID == NumberUtil.parseLong(CharSequenceUtil.toString(id)));
        if (isAdmin) {
            throw new ClientException("Can't delete admin account!");
        }
        return R.result(userService.removeByIds(list));
    }

    /**
     * 和 @SaCheckSafe 的功能作用一样，可替代使用。
     * 检查当前会话是否已通过二级认证，如未通过则抛出异常。前端异常拦截后自动拉起二级认证弹框。
     */
    @PostMapping("/check/safe")
    public R<Void> checkSafe() {
        StpUtil.checkSafe();
        return R.ok();
    }

    @PostMapping("/auth/secondary")
    public R<Void> openSafe(@RequestBody OpenSafeBO bo) {
        UserSessionVO sessionUser = LoginHelper.getSessionUser();
        ESafeMode safeMode = EnumUtil.fromString(ESafeMode.class, bo.getSafeMode());
        ISecondaryAuthHandler handler = SecondaryAuthHandleFactory.getHandler(safeMode);
        boolean validated = handler.validate(sessionUser, bo);
        if (validated) {
            Long secondaryAuthValidityPeriod = vitaProperties.getUser().getSecondaryAuthValidityPeriod();
            // 比对成功，为当前会话打开二级认证，有效期为 secondaryAuthValidityPeriod 秒
            StpUtil.openSafe(secondaryAuthValidityPeriod);
            return R.ok();
        }

        String msg = I18nUtils.msg("system.user.secondary.authentication.failed");
        throw new ClientException(msg);
    }

    /**
     * <p>
     * change password。需要先通过 @SaCheckSafe 来验证是否已通过二级认证。
     * </p>
     *
     * @param bo {@link PasswordChangeBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE, saveRequestData = false)
    @SaCheckSafe
    @PostMapping("/change/password")
    public R<Void> changePassword(@Validated @RequestBody PasswordChangeBO bo) {
        String username = LoginHelper.getSessionUsername();
        boolean bool = userService.updatePassword(username, bo.getPassword());
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
    @PostMapping("/reset/password")
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
    @PostMapping("/set/avatar")
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
    @PostMapping("/set/roles")
    public R<Void> setRoles(@Validated @RequestBody UserRoleBO bo) {
        userRoleService.setUserRoles(bo.getUserId(), bo.getRoleIds());

        String title = "角色变更";
        String nickname = StrUtil.emptyIfNull(LoginHelper.getSessionUserNickname()).toString();
        String content = String.format("您的角色已发生变更。变更人：%s", nickname);
        Long userId = LoginHelper.getSessionUserId();
        messageService.sendMessageToUser(EMessageType.USER, title, content, userId);
        return R.ok();
    }

    @PostMapping("/validate/totp/{code}")
    public boolean validateTotpCode(@PathVariable("code") Integer code) {
        UserDO user = this.getById(LoginHelper.getSessionUserId());
        boolean validated = TotpUtils.validate(user.getTotp(), code);
        if (validated) {
            return true;
        }
        throw new ClientException(I18nUtils.msg("system.user.totp.code.invalid"));
    }

    /**
     * 生成 TOTP 二维码。需要先通过 @SaCheckSafe 来验证是否已通过二级认证。
     *
     * @return TotpVO
     */
    @SaCheckSafe
    @GetMapping("/generate/totp/qrcode")
    public TotpVO generateTotpQrcode() {
        return userService.generateTotpQrcode();
    }

    @GetMapping("/query/terminalInfo")
    public List<SaTerminalInfoVO> queryTerminalInfo() {
        SaSession session = StpUtil.getSession();
        SaSessionVO sessionVO = new SaSessionVO(session);
        return sessionVO.getTerminalInfoList();
    }
}

