package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.util.BeanCopyUtils;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.system.constant.ConfigConst;
import com.github.mengweijin.vita.system.domain.bo.UserBO;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.domain.entity.UserAvatarDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.UserSensitiveVO;
import com.github.mengweijin.vita.system.enums.EMessageCategory;
import com.github.mengweijin.vita.system.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.PasswdStrength;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.math.NumberUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.crypto.digest.BCrypt;
import org.dromara.hutool.crypto.digest.DigestUtil;
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
public class UserService extends CrudRepository<UserMapper, UserDO> {

    private UserAvatarService userAvatarService;

    private DeptService deptService;

    private MessageService messageService;

    private ConfigService configService;

    private RoleService roleService;

    private UserRoleService userRoleService;

    private UserPostService userPostService;

    @Override
    public boolean save(UserDO user) {
        if(StrUtil.isBlank(user.getPassword())) {
            ConfigDO config = configService.getByCode(ConfigConst.USER_PASSWORD_DEFAULT);
            user.setPassword(config.getVal());
        }
        user.setPasswordLevel(PasswdStrength.getLevel(user.getPassword()).name());
        user.setSalt(BCrypt.gensalt());
        user.setPassword(DigestUtil.bcrypt(this.saltedPassword(user.getPassword(), user.getSalt())));
        user.setPasswordChangeTime(LocalDateTime.now());
        return super.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdate(UserBO userBO) {
        UserDO userDO = BeanCopyUtils.copyBean(userBO, new UserDO());
        // 用户
        if(userBO.getId() == null) {
            // 新增
            this.save(userDO);
        } else {
            // 编辑
            super.updateById(userDO);
        }

        // 角色
        userRoleService.setUserRoles(userDO.getId(), userBO.getRoleIds());
        // 岗位
        userPostService.setUserPosts(userDO.getId(), userBO.getPostIds());
    }

    public String saltedPassword(String password, String salt) {
        return String.join(Const.COMMA, password, salt);
    }

    public LambdaQueryWrapper<UserDO> getQueryWrapper(UserDO user) {
        List<Long> deptIds = new ArrayList<>();
        if (!Objects.isNull(user.getDeptId())) {
            deptIds = deptService.selectChildrenIdsWithCurrentIdById(user.getDeptId());
        }

        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(user.getId() != null, UserDO::getId, user.getId());
        wrapper.eq(StrUtil.isNotBlank(user.getCitizenId()), UserDO::getCitizenId, user.getCitizenId());
        wrapper.eq(StrUtil.isNotBlank(user.getMobile()), UserDO::getMobile, user.getMobile());
        wrapper.eq(StrUtil.isNotBlank(user.getEmail()), UserDO::getEmail, user.getEmail());
        wrapper.eq(StrUtil.isNotBlank(user.getPasswordLevel()), UserDO::getPasswordLevel, user.getPasswordLevel());
        wrapper.eq(StrUtil.isNotBlank(user.getGender()), UserDO::getGender, user.getGender());
        wrapper.eq(StrUtil.isNotBlank(user.getDisabled()), UserDO::getDisabled, user.getDisabled());
        wrapper.eq(user.getCreateBy() != null, UserDO::getCreateBy, user.getCreateBy());
        wrapper.eq(user.getUpdateBy() != null, UserDO::getUpdateBy, user.getUpdateBy());
        wrapper.gt(user.getSearchStartTime() != null, UserDO::getCreateTime, user.getSearchStartTime());
        wrapper.le(user.getSearchEndTime() != null, UserDO::getCreateTime, user.getSearchEndTime());
        wrapper.in(user.getDeptId() != null, UserDO::getDeptId, deptIds);
        if (StrUtil.isNotBlank(user.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(UserDO::getUsername, user.getKeywords()));
                w.or(w1 -> w1.like(UserDO::getNickname, user.getKeywords()));
            });
        }
        return wrapper;
    }

    public UserDO getByUsername(String username) {
        return this.lambdaQuery().eq(UserDO::getUsername, username).one();
    }

    public Set<Long> getUserIdsInDeptId(Long deptId) {
        List<Long> deptIds = deptService.selectChildrenIdsWithCurrentIdById(deptId);
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

    public boolean changePassword(String username, String password, String newPassword) {
        UserDO user = this.getByUsername(username);
        boolean checked = this.checkPassword(password, user.getPassword(), user.getSalt());
        if (!checked) {
            throw new ClientException("User or password check failed!");
        }

        return this.updatePassword(username, newPassword);
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
                    ConfigDO config = configService.getByCode(ConfigConst.USER_PASSWORD_CHANGE_INTERVAL);
                    if (config == null) {
                        return;
                    }
                    long daysInterval = NumberUtil.parseLong(config.getVal());
                    if (daysInterval <= 0) {
                        return;
                    }

                    UserDO user = this.getByUsername(username);
                    Duration duration = TimeUtil.between(user.getPasswordChangeTime(), LocalDateTime.now());
                    if (duration.toDays() < daysInterval) {
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

    public UserSensitiveVO getSensitiveUserById(Long id) {
        UserDO user = this.getById(id);
        Set<Long> roleIds = userRoleService.getRoleIdsByUserId(id);
        Set<Long> postIds = userPostService.getPostIdsByUserId(id);
        UserSensitiveVO vo = BeanCopyUtils.copyBean(user, new UserSensitiveVO());
        vo.setRoleIds(roleIds);
        vo.setPostIds(postIds);
        return vo;
    }

    public IPage<UserDO> pageByRole(Long roleId, Page<UserDO> page, UserDO user) {
        Set<Long> userIds = userRoleService.getUserIdsByRoleId(roleId);
        LambdaQueryWrapper<UserDO> wrapper = this.getQueryWrapper(user);
        wrapper.in(UserDO::getId, userIds);
        return this.page(page, wrapper);
    }

    public IPage<UserDO> pageByPost(Long postId, Page<UserDO> page, UserDO user) {
        Set<Long> userIds = userPostService.getUserIdsByPostId(postId);
        LambdaQueryWrapper<UserDO> wrapper = this.getQueryWrapper(user);
        wrapper.in(UserDO::getId, userIds);
        return this.page(page, wrapper);
    }
}
