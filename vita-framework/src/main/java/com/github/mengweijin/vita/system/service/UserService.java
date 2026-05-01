package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.data.PasswdStrength;
import cn.hutool.v7.core.date.TimeUtil;
import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.crypto.digest.BCrypt;
import cn.hutool.v7.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.constant.VitaConst;
import com.github.mengweijin.vita.framework.enums.dict.EMessageCategory;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.properties.ApplicationProperties;
import com.github.mengweijin.vita.framework.properties.VitaProperties;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.util.AopUtils;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.framework.util.TotpUtils;
import com.github.mengweijin.vita.monitor.service.LogDataChangeService;
import com.github.mengweijin.vita.system.domain.bo.UserBO;
import com.github.mengweijin.vita.system.domain.bo.UserBasicInformationBO;
import com.github.mengweijin.vita.system.domain.entity.PostDO;
import com.github.mengweijin.vita.system.domain.entity.RoleDO;
import com.github.mengweijin.vita.system.domain.entity.UserAvatarDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.TotpVO;
import com.github.mengweijin.vita.system.domain.vo.user.UserProfileVO;
import com.github.mengweijin.vita.system.domain.vo.user.UserStoreVO;
import com.github.mengweijin.vita.system.domain.vo.user.UserVO;
import com.github.mengweijin.vita.system.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * <p>
 * User Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService extends BaseVitaService<UserMapper, UserDO, UserVO> {

    private UserAvatarService userAvatarService;

    private DeptService deptService;

    private MessageService messageService;

    private RoleService roleService;

    private PostService postService;

    private UserRoleService userRoleService;

    private UserPostService userPostService;

    private VitaProperties vitaProperties;

    private ApplicationProperties applicationProperties;

    private LogDataChangeService logDataChangeService;

    @Override
    public boolean save(UserDO user) {
        if (StrUtil.isBlank(user.getPassword())) {
            String defaultPassword = vitaProperties.getUser().getDefaultPassword();
            user.setPassword(defaultPassword);
        }
        user.setPasswordLevel(PasswdStrength.getLevel(user.getPassword()).name());
        user.setSalt(BCrypt.gensalt());
        user.setPassword(DigestUtil.bcrypt(this.saltedPassword(user.getPassword(), user.getSalt())));
        user.setPasswordChangeTime(LocalDateTime.now());
        return super.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(UserBO userBO) {
        UserDO userDO = MapstructUtils.getConverter().convert(userBO, UserDO.class);
        // 用户
        if (userBO.getId() == null) {
            // 新增
            this.save(userDO);
        } else {
            // 编辑
            super.updateById(userDO);
        }

        Set<Long> beforeRoleIds = userRoleService.getRoleIdsByUserId(userDO.getId());

        // 保存角色
        userRoleService.setUserRoles(userDO.getId(), userBO.getRoleIds());
        // 保存岗位
        userPostService.setUserPosts(userDO.getId(), userBO.getPostIds());
        // 保存角色变动日志
        logDataChangeService.saveWhenListChange(VitaConst.TABLE_VT_USER_ROLE, userDO.getId(), List.copyOf(beforeRoleIds), userBO.getRoleIds());
    }

    public String saltedPassword(String password, String salt) {
        return String.join(Const.COMMA, password, salt);
    }

    @Override
    public LambdaQueryWrapper<UserDO> buildQueryWrapper(UserDO user) {
        List<Long> deptIds = new ArrayList<>();
        if (!Objects.isNull(user.getDeptId())) {
            deptIds = deptService.getChildrenIds(user.getDeptId());
            deptIds.add(user.getDeptId());
        }

        LambdaQueryWrapper<UserDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(user.getId() != null, UserDO::getId, user.getId());
        wrapper.eq(StrUtil.isNotBlank(user.getCitizenId()), UserDO::getCitizenId, user.getCitizenId());
        wrapper.eq(StrUtil.isNotBlank(user.getMobile()), UserDO::getMobile, user.getMobile());
        wrapper.eq(StrUtil.isNotBlank(user.getEmail()), UserDO::getEmail, user.getEmail());
        wrapper.eq(StrUtil.isNotBlank(user.getPasswordLevel()), UserDO::getPasswordLevel, user.getPasswordLevel());
        wrapper.eq(StrUtil.isNotBlank(user.getGender()), UserDO::getGender, user.getGender());
        wrapper.eq(StrUtil.isNotBlank(user.getDisabled()), UserDO::getDisabled, user.getDisabled());
        wrapper.eq(user.getCreateBy() != null, UserDO::getCreateBy, user.getCreateBy());
        wrapper.eq(user.getUpdateBy() != null, UserDO::getUpdateBy, user.getUpdateBy());
        wrapper.gt(user.getStartCreateTime() != null, UserDO::getCreateTime, user.getStartCreateTime());
        wrapper.le(user.getEndCreateTime() != null, UserDO::getCreateTime, user.getEndCreateTime());
        wrapper.in(user.getDeptId() != null, UserDO::getDeptId, deptIds);
        wrapper.like(StrUtil.isNotBlank(user.getUsername()), UserDO::getUsername, user.getUsername());
        wrapper.like(StrUtil.isNotBlank(user.getNickname()), UserDO::getNickname, user.getNickname());
        return wrapper;
    }

    public UserDO getByUsername(String username) {
        return this.lambdaQuery().eq(UserDO::getUsername, username).one();
    }

    public Set<Long> getUserIdsInDeptId(Long deptId) {
        List<Long> deptIds = deptService.getChildrenIds(deptId);
        deptIds.add(deptId);
        List<UserDO> list = this.lambdaQuery().select(UserDO::getId).in(UserDO::getDeptId, deptIds).list();
        return list.stream().map(UserDO::getId).collect(Collectors.toSet());
    }

    public String getUsernamesByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(Const.COMMA)).map(NumberUtil::parseLong).distinct().toList();
        return idList.stream().map(this::getUsernameById).collect(Collectors.joining());
    }

    public String getUserNicknamesByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(Const.COMMA)).map(NumberUtil::parseLong).distinct().toList();
        return idList.stream().map(this::getNicknameById).collect(Collectors.joining());
    }

    @Cacheable(value = CacheNames.USER_ID_TO_USERNAME, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getUsernameById(Long id) {
        return this.lambdaQuery()
                .select(UserDO::getUsername)
                .eq(UserDO::getId, id)
                .oneOpt()
                .map(UserDO::getUsername)
                .orElse(null);
    }

    @Cacheable(value = CacheNames.USER_ID_TO_NICKNAME, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getNicknameById(Long id) {
        return this.lambdaQuery()
                .select(UserDO::getNickname)
                .eq(UserDO::getId, id)
                .oneOpt()
                .map(UserDO::getNickname)
                .orElse(null);
    }

    @Cacheable(value = CacheNames.USER_ID_TO_AVATAR, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getAvatarById(Long id) {
        return userAvatarService.lambdaQuery().eq(UserAvatarDO::getUserId, id).oneOpt()
                .map(UserAvatarDO::getAvatar).orElse(null);
    }

    public boolean checkPassword(String checkingPwd, String dbPwd, String salt) {
        String saltedPassword = this.saltedPassword(checkingPwd, salt);
        return DigestUtil.bcryptCheck(saltedPassword, dbPwd);
    }

    public boolean updatePassword(String username, String newPassword) {
        String passwordLevel = PasswdStrength.getLevel(newPassword).name();
        String salt = BCrypt.gensalt();
        String hashedPwd = DigestUtil.bcrypt(this.saltedPassword(newPassword, salt));

        return this.lambdaUpdate()
                .set(UserDO::getSalt, salt)
                .set(UserDO::getPassword, hashedPwd)
                .set(UserDO::getPasswordLevel, passwordLevel)
                .set(UserDO::getPasswordChangeTime, LocalDateTime.now())
                .eq(UserDO::getUsername, username)
                .update();
    }

    public boolean setDisabled(Long id, String disabled) {
        return this.lambdaUpdate().set(UserDO::getDisabled, disabled).eq(UserDO::getId, id).update();
    }

    public void checkAndSendPasswordLongTimeNoChangeMessageAsync(String username) {
        CompletableFuture.runAsync(() -> {
                    int userPasswordChangeInterval = vitaProperties.getUser().getPasswordChangeInterval();
                    if (userPasswordChangeInterval <= 0) {
                        return;
                    }

                    UserDO user = this.getByUsername(username);
                    Duration duration = TimeUtil.between(user.getPasswordChangeTime(), LocalDateTime.now());
                    if (duration.toDays() < userPasswordChangeInterval) {
                        return;
                    }

                    String messageTitle = I18nUtils.msg("system.message.USER_PASSWORD_LONG_TIME_NO_CHANGE.title");
                    String messageContent = I18nUtils.msg("system.message.USER_PASSWORD_LONG_TIME_NO_CHANGE.content", duration.toDays());
                    messageService.sendMessageToUser(EMessageCategory.SECURITY, messageTitle, messageContent, user.getId());
                })
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    return null;
                });
    }

    public UserStoreVO getUserStoreVO() {
        UserDO user = this.getById(LoginHelper.getSessionUserId());

        UserStoreVO vo = new UserStoreVO();
        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setNickname(user.getNickname());
        vo.setDeptId(user.getDeptId());
        vo.setRoles(LoginHelper.getRoleList());
        vo.setPermissions(LoginHelper.getPermissionList());
        vo.setToken(LoginHelper.getToken());
        return vo;
    }

    public UserBO getUserBO(Long id) {
        UserDO user = this.getById(id);
        Set<Long> roleIds = userRoleService.getRoleIdsByUserId(id);
        Set<Long> postIds = userPostService.getPostIdsByUserId(id);
        UserBO bo = MapstructUtils.getConverter().convert(user, UserBO.class);
        bo.setRoleIds(new ArrayList<>(roleIds));
        bo.setPostIds(new ArrayList<>(postIds));
        return bo;
    }


    public UserProfileVO getUserProfileVO(Long id) {
        UserDO user = this.getById(id);
        Set<Long> roleIds = userRoleService.getRoleIdsByUserId(id);
        Set<Long> postIds = userPostService.getPostIdsByUserId(id);
        UserProfileVO vo = MapstructUtils.getConverter().convert(user, UserProfileVO.class);
        vo.setRoleIds(roleIds);
        vo.setPostIds(postIds);

        if (CollUtil.isNotEmpty(roleIds)) {
            List<RoleDO> roleList = roleService.listByIds(roleIds);
            vo.setRoleList(roleList);
        }

        if (CollUtil.isNotEmpty(postIds)) {
            List<PostDO> postList = postService.listByIds(postIds);
            vo.setPostList(postList);
        }

        return vo;
    }

    public IPage<UserDO> pageByRole(Long roleId, IPage<UserDO> page, UserDO user) {
        Set<Long> userIds = userRoleService.getUserIdsByRoleId(roleId);
        if (CollUtil.isEmpty(userIds)) {
            return page;
        }
        LambdaQueryWrapper<UserDO> wrapper = this.buildQueryWrapper(user);
        wrapper.in(UserDO::getId, userIds);
        return this.page(page, wrapper);
    }

    public IPage<UserDO> pageByPost(Long postId, IPage<UserDO> page, UserDO user) {
        Set<Long> userIds = userPostService.getUserIdsByPostId(postId);
        if (CollUtil.isEmpty(userIds)) {
            return page;
        }
        LambdaQueryWrapper<UserDO> wrapper = this.buildQueryWrapper(user);
        wrapper.in(UserDO::getId, userIds);
        return this.page(page, wrapper);
    }

    public Set<Long> getUserIdsInUsernames(Set<String> usernameSet) {
        return this.lambdaQuery()
                .select(UserDO::getId)
                .in(UserDO::getUsername, usernameSet)
                .list()
                .stream().map(UserDO::getId).collect(Collectors.toSet());
    }

    @CacheEvict(
            value = {
                    CacheNames.USER_ID_TO_AVATAR,
                    CacheNames.USER_ID_TO_NICKNAME,
                    CacheNames.USER_ID_TO_USERNAME
            },
            key = "#bo.id + ''")
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBasicInformation(UserBasicInformationBO bo) {
        UserDO userDO = MapstructUtils.getConverter().convert(bo, UserDO.class);
        boolean bool = AopUtils.getAopProxy(this).updateById(userDO);

        if (StrUtil.isNotBlank(bo.getAvatar())) {
            UserAvatarDO userAvatar = new UserAvatarDO();
            userAvatar.setUserId(bo.getId());
            userAvatar.setAvatar(bo.getAvatar());
            userAvatarService.setAvatar(userAvatar);
        }
        return bool;
    }

    public TotpVO generateTotpQrcode() {
        UserDO user = this.getById(LoginHelper.getSessionUserId());
        String key = user.getTotp();
        if (StrUtil.isBlank(key)) {
            key = TotpUtils.generateSecretKey();
            // 保存 TOTP key
            this.lambdaUpdate().set(UserDO::getTotp, key).eq(UserDO::getId, user.getId()).update();
        }

        String label = String.format("%s(%s)", user.getNickname(), user.getUsername());
        String qrcode = TotpUtils.generateQrCode(key, label, applicationProperties.getName());
        return new TotpVO(key, qrcode);
    }

    public boolean validateTotp(Integer code) {
        UserDO user = this.getById(LoginHelper.getSessionUserId());
        if (StrUtil.isBlank(user.getTotp())) {
            log.warn("The user has not bound the TOTP!");
            return false;
        }
        return TotpUtils.validate(user.getTotp(), code);
    }

}
