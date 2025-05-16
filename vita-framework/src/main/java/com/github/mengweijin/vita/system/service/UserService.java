package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.constant.ConfigConst;
import com.github.mengweijin.vita.system.domain.bo.ChangePasswordBO;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.domain.entity.UserAvatarDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.enums.EMessageCategory;
import com.github.mengweijin.vita.system.enums.EMessageTemplate;
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
        user.setPasswordLevel(PasswdStrength.getLevel(user.getPassword()).name());
        user.setSalt(BCrypt.gensalt());
        user.setPassword(DigestUtil.bcrypt(this.saltedPassword(user.getPassword(), user.getSalt())));
        user.setPasswordChangeTime(LocalDateTime.now());
        return super.save(user);
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
        wrapper.eq(StrUtil.isNotBlank(user.getPasswordLevel()), UserDO::getPasswordLevel, user.getPasswordLevel());
        wrapper.eq(StrUtil.isNotBlank(user.getGender()), UserDO::getGender, user.getGender());
        wrapper.eq(StrUtil.isNotBlank(user.getDisabled()), UserDO::getDisabled, user.getDisabled());
        wrapper.eq(user.getCreateBy() != null, UserDO::getCreateBy, user.getCreateBy());
        wrapper.eq(user.getUpdateBy() != null, UserDO::getUpdateBy, user.getUpdateBy());
        wrapper.gt(user.getSearchStartTime() != null, UserDO::getCreateTime, user.getSearchStartTime());
        wrapper.le(user.getSearchEndTime() != null, UserDO::getCreateTime, user.getSearchEndTime());
        wrapper.in(user.getDeptId() != null, UserDO::getDeptId, deptIds);
        if (StrUtil.isNotBlank(user.getKeywords())) {
            wrapper.or(w -> w.like(UserDO::getUsername, user.getKeywords()));
            wrapper.or(w -> w.like(UserDO::getNickname, user.getKeywords()));
            wrapper.or(w -> w.like(UserDO::getCitizenId, user.getKeywords()));
            wrapper.or(w -> w.like(UserDO::getMobile, user.getKeywords()));
            wrapper.or(w -> w.like(UserDO::getEmail, user.getKeywords()));
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

    public boolean changePassword(ChangePasswordBO bo) {
        UserDO user = this.getByUsername(bo.getUsername());
        boolean checked = this.checkPassword(bo.getPassword(), user.getPassword(), user.getSalt());
        if (!checked) {
            throw new ClientException("User or password check failed!");
        }

        return this.updatePassword(bo.getUsername(), bo.getNewPassword());
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

                    messageService.sendMessageToUser(user.getId(), EMessageCategory.SECURITY, EMessageTemplate.USER_PASSWORD_LONG_TIME_NO_CHANGE, duration.toDays());
                })
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    return null;
                });
    }

}
