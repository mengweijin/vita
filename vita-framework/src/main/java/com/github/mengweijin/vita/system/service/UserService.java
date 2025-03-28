package com.github.mengweijin.vita.system.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.constant.ConfigConst;
import com.github.mengweijin.vita.system.domain.bo.ChangePasswordBO;
import com.github.mengweijin.vita.system.domain.entity.Config;
import com.github.mengweijin.vita.system.domain.entity.User;
import com.github.mengweijin.vita.system.domain.entity.UserAvatar;
import com.github.mengweijin.vita.system.enums.EMessageCategory;
import com.github.mengweijin.vita.system.enums.EMessageTemplate;
import com.github.mengweijin.vita.system.mapper.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.PasswdStrength;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.math.NumberUtil;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
public class UserService extends CrudRepository<UserMapper, User> {

    private UserAvatarService userAvatarService;

    private DeptService deptService;

    private MessageService messageService;

    private ConfigService configService;

    @Override
    public boolean save(User user) {
        String salt = this.generateSalt();
        user.setPasswordLevel(PasswdStrength.getLevel(user.getPassword()).name());
        user.setSalt(salt);
        user.setPassword(this.hashPassword(user.getUsername(), user.getPassword(), user.getSalt()));
        user.setPasswordChangeTime(LocalDateTime.now());
        return super.save(user);
    }

    public String hashPassword(String username, String password, String salt) {
        return SaSecureUtil.sha256(String.join(username, password, salt));
    }

    public String generateSalt() {
        return IdUtil.simpleUUID().toUpperCase();
    }

    /**
     * Custom paging query
     *
     * @param page page
     * @param user {@link User}
     * @return IPage
     */
    public IPage<User> page(IPage<User> page, User user) {
        List<Long> deptIds = new ArrayList<>();
        if (!Objects.isNull(user.getDeptId())) {
            deptIds = deptService.selectChildrenIdsWithCurrentIdById(user.getDeptId());
        }
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(user.getPasswordLevel()), User::getPassword, user.getPasswordLevel())
                .eq(StrValidator.isNotBlank(user.getIdCard()), User::getIdCard, user.getIdCard())
                .eq(StrValidator.isNotBlank(user.getGender()), User::getGender, user.getGender())
                .eq(StrValidator.isNotBlank(user.getDisabled()), User::getDisabled, user.getDisabled())
                .eq(StrValidator.isNotBlank(user.getRemark()), User::getRemark, user.getRemark())
                .eq(!Objects.isNull(user.getId()), User::getId, user.getId())
                .eq(!Objects.isNull(user.getCreateBy()), User::getCreateBy, user.getCreateBy())
                .eq(!Objects.isNull(user.getCreateTime()), User::getCreateTime, user.getCreateTime())
                .eq(!Objects.isNull(user.getUpdateBy()), User::getUpdateBy, user.getUpdateBy())
                .eq(!Objects.isNull(user.getUpdateTime()), User::getUpdateTime, user.getUpdateTime())
                .in(!Objects.isNull(user.getDeptId()), User::getDeptId, deptIds)
                .like(StrValidator.isNotBlank(user.getUsername()), User::getUsername, user.getUsername())
                .like(StrValidator.isNotBlank(user.getNickname()), User::getNickname, user.getNickname())
                .like(StrValidator.isNotBlank(user.getMobile()), User::getMobile, user.getMobile())
                .like(StrValidator.isNotBlank(user.getEmail()), User::getEmail, user.getEmail());
        return this.page(page, query);
    }

    public User getByUsername(String username) {
        return this.lambdaQuery().eq(User::getUsername, username).one();
    }

    public Set<Long> getUserIdsInDeptId(Long deptId) {
        List<Long> deptIds = deptService.selectChildrenIdsWithCurrentIdById(deptId);
        List<User> list = this.lambdaQuery().select(User::getId).in(User::getDeptId, deptIds).list();
        return list.stream().map(User::getId).collect(Collectors.toSet());
    }

    public String getUsernameByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(Const.COMMA)).map(NumberUtil::parseLong).distinct().toList();
        return idList.stream().map(this::getUsernameById).collect(Collectors.joining());
    }

    public String getUserNicknameByIds(String ids) {
        List<Long> idList = Arrays.stream(ids.split(Const.COMMA)).map(NumberUtil::parseLong).distinct().toList();
        return idList.stream().map(this::getNicknameById).collect(Collectors.joining());
    }

    @Cacheable(value = CacheNames.USER_ID_TO_USERNAME, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getUsernameById(Long id) {
        return this.lambdaQuery()
                .select(User::getUsername)
                .eq(User::getId, id)
                .oneOpt()
                .map(User::getUsername)
                .orElse(null);
    }

    @Cacheable(value = CacheNames.USER_ID_TO_NICKNAME, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getNicknameById(Long id) {
        return this.lambdaQuery()
                .select(User::getNickname)
                .eq(User::getId, id)
                .oneOpt()
                .map(User::getNickname)
                .orElse(null);
    }

    @Cacheable(value = CacheNames.USER_ID_TO_AVATAR, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getAvatarById(Long id) {
        return userAvatarService.lambdaQuery().eq(UserAvatar::getUserId, id).oneOpt()
                .map(UserAvatar::getAvatar).orElse(null);
    }

    public boolean checkPassword(User user, String password) {
        String passwordInDb = user.getPassword();
        String hashedPassword = this.hashPassword(user.getUsername(), password, user.getSalt());
        return passwordInDb.equals(hashedPassword);
    }

    public boolean changePassword(ChangePasswordBO bo) {
        User user = this.getByUsername(bo.getUsername());
        boolean checked = this.checkPassword(user, bo.getPassword());
        if (!checked) {
            throw new ClientException("User or password check failed!");
        }

        return this.updatePassword(bo.getUsername(), bo.getNewPassword());
    }

    public boolean updatePassword(String username, String newPassword) {
        String passwordLevel = PasswdStrength.getLevel(newPassword).name();
        String salt = this.generateSalt();
        String hashedPwd = this.hashPassword(username, newPassword, salt);

        return this.lambdaUpdate()
                .set(User::getSalt, salt)
                .set(User::getPassword, hashedPwd)
                .set(User::getPasswordLevel, passwordLevel)
                .set(User::getPasswordChangeTime, LocalDateTime.now())
                .eq(User::getUsername, username)
                .update();
    }

    public boolean setDisabled(Long id, String disabled) {
        return this.lambdaUpdate().set(User::getDisabled, disabled).eq(User::getId, id).update();
    }

    public void checkAndSendPasswordLongTimeNoChangeMessageAsync(String username) {
        CompletableFuture.runAsync(() -> {
                    Config config = configService.getByCode(ConfigConst.USER_PASSWORD_CHANGE_INTERVAL);
                    if (config == null) {
                        return;
                    }
                    long daysInterval = NumberUtil.parseLong(config.getVal());
                    if (daysInterval <= 0) {
                        return;
                    }

                    User user = this.getByUsername(username);
                    Duration duration = TimeUtil.between(user.getPasswordChangeTime(), LocalDateTime.now());
                    if (duration.toDays() < daysInterval) {
                        return;
                    }

                    messageService.sendMessageToUser(user.getId(), EMessageCategory.SECURITY, EMessageTemplate.USER_PASSWORD_LONG_TIME_NO_CHANGE, duration.toDays());
                })
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    return null;
                });
    }

}
