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
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.entity.UserAvatarDO;
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
public class UserService extends CrudRepository<UserMapper, UserDO> {

    private UserAvatarService userAvatarService;

    private DeptService deptService;

    private MessageService messageService;

    private ConfigService configService;

    @Override
    public boolean save(UserDO user) {
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
     * @param user {@link UserDO}
     * @return IPage
     */
    public IPage<UserDO> page(IPage<UserDO> page, UserDO user) {
        List<Long> deptIds = new ArrayList<>();
        if (!Objects.isNull(user.getDeptId())) {
            deptIds = deptService.selectChildrenIdsWithCurrentIdById(user.getDeptId());
        }
        LambdaQueryWrapper<UserDO> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(user.getPasswordLevel()), UserDO::getPasswordLevel, user.getPasswordLevel())
                .eq(StrValidator.isNotBlank(user.getCitizenId()), UserDO::getCitizenId, user.getCitizenId())
                .eq(StrValidator.isNotBlank(user.getGender()), UserDO::getGender, user.getGender())
                .eq(StrValidator.isNotBlank(user.getDisabled()), UserDO::getDisabled, user.getDisabled())
                .eq(StrValidator.isNotBlank(user.getRemark()), UserDO::getRemark, user.getRemark())
                .eq(!Objects.isNull(user.getId()), UserDO::getId, user.getId())
                .eq(!Objects.isNull(user.getCreateBy()), UserDO::getCreateBy, user.getCreateBy())
                .eq(!Objects.isNull(user.getCreateTime()), UserDO::getCreateTime, user.getCreateTime())
                .eq(!Objects.isNull(user.getUpdateBy()), UserDO::getUpdateBy, user.getUpdateBy())
                .eq(!Objects.isNull(user.getUpdateTime()), UserDO::getUpdateTime, user.getUpdateTime())
                .in(!Objects.isNull(user.getDeptId()), UserDO::getDeptId, deptIds)
                .like(StrValidator.isNotBlank(user.getUsername()), UserDO::getUsername, user.getUsername())
                .like(StrValidator.isNotBlank(user.getNickname()), UserDO::getNickname, user.getNickname())
                .like(StrValidator.isNotBlank(user.getMobile()), UserDO::getMobile, user.getMobile())
                .like(StrValidator.isNotBlank(user.getEmail()), UserDO::getEmail, user.getEmail());
        return this.page(page, query);
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

    public boolean checkPassword(UserDO user, String password) {
        String passwordInDb = user.getPassword();
        String hashedPassword = this.hashPassword(user.getUsername(), password, user.getSalt());
        return passwordInDb.equals(hashedPassword);
    }

    public boolean changePassword(ChangePasswordBO bo) {
        UserDO user = this.getByUsername(bo.getUsername());
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

                    messageService.sendMessageToUser(user.getId(), EMessageCategory.SECURITY, EMessageTemplate.USER_PASSWORD_LONG_TIME_NO_CHANGE, duration.toDays());
                })
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    return null;
                });
    }

}
