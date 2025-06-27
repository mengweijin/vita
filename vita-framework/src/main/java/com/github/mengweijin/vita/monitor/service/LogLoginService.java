package com.github.mengweijin.vita.monitor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.util.Ip2regionUtils;
import com.github.mengweijin.vita.framework.util.ServletUtils;
import com.github.mengweijin.vita.monitor.domain.entity.LogLoginDO;
import com.github.mengweijin.vita.monitor.mapper.LogLoginMapper;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.enums.ELoginType;
import com.github.mengweijin.vita.system.enums.EYesNo;
import com.github.mengweijin.vita.system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.text.StrValidator;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.http.server.servlet.ServletUtil;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.http.useragent.UserAgentInfo;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 * LogLogin Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class LogLoginService extends CrudRepository<LogLoginMapper, LogLoginDO> {

    private UserService userService;

    public LambdaQueryWrapper<LogLoginDO> getQueryWrapper(LogLoginDO logLogin) {
        LambdaQueryWrapper<LogLoginDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(logLogin.getId() != null, LogLoginDO::getId, logLogin.getId());

        wrapper.eq(StrValidator.isNotBlank(logLogin.getIpLocation()), LogLoginDO::getIpLocation, logLogin.getIpLocation());
        wrapper.eq(StrValidator.isNotBlank(logLogin.getLoginType()), LogLoginDO::getLoginType, logLogin.getLoginType());
        wrapper.eq(StrValidator.isNotBlank(logLogin.getBrowser()), LogLoginDO::getBrowser, logLogin.getBrowser());
        wrapper.eq(StrValidator.isNotBlank(logLogin.getPlatform()), LogLoginDO::getPlatform, logLogin.getPlatform());
        wrapper.eq(StrValidator.isNotBlank(logLogin.getOs()), LogLoginDO::getOs, logLogin.getOs());
        wrapper.eq(StrValidator.isNotBlank(logLogin.getSuccess()), LogLoginDO::getSuccess, logLogin.getSuccess());
        wrapper.eq(StrValidator.isNotBlank(logLogin.getErrorMsg()), LogLoginDO::getErrorMsg, logLogin.getErrorMsg());

        wrapper.eq(logLogin.getCreateBy() != null, LogLoginDO::getCreateBy, logLogin.getCreateBy());
        wrapper.eq(logLogin.getUpdateBy() != null, LogLoginDO::getUpdateBy, logLogin.getUpdateBy());
        wrapper.gt(logLogin.getSearchStartTime() != null, LogLoginDO::getCreateTime, logLogin.getSearchStartTime());
        wrapper.le(logLogin.getSearchEndTime() != null, LogLoginDO::getCreateTime, logLogin.getSearchEndTime());
        if (StrUtil.isNotBlank(logLogin.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(LogLoginDO::getUsername, logLogin.getKeywords()));
                w.or(w1 -> w1.like(LogLoginDO::getIp, logLogin.getKeywords()));
            });
        }
        return wrapper;
    }

    public void addLoginLogAsync(String username, ELoginType loginType, String errorMsg, HttpServletRequest request) {
        CompletableFuture.runAsync(() -> {
                    LogLoginDO logLogin = new LogLoginDO();
                    if (request != null) {
                        UserAgent userAgent = ServletUtils.getUserAgent(request);
                        String ip = ServletUtil.getClientIP(request);
                        logLogin.setIp(ip);
                        logLogin.setIpLocation(Ip2regionUtils.search(ip));
                        logLogin.setBrowser(Optional.ofNullable(userAgent).map(UserAgent::getBrowser).map(UserAgentInfo::getName).orElse(null));
                        logLogin.setPlatform(Optional.ofNullable(userAgent).map(UserAgent::getPlatform).map(UserAgentInfo::getName).orElse(null));
                        logLogin.setOs(Optional.ofNullable(userAgent).map(UserAgent::getOs).map(UserAgentInfo::getName).orElse(null));
                    }
                    logLogin.setUsername(username);
                    logLogin.setLoginType(loginType.getValue());
                    logLogin.setSuccess(StrValidator.isBlank(errorMsg) ? EYesNo.Y.getValue() : EYesNo.N.getValue());
                    logLogin.setErrorMsg(errorMsg);

                    UserDO user = userService.getByUsername(username);
                    Long userId = Optional.ofNullable(user).map(UserDO::getId).orElse(null);
                    logLogin.setCreateBy(userId);
                    logLogin.setUpdateBy(userId);

                    SpringUtil.getBean(LogLoginService.class).save(logLogin);
                })
                .exceptionally(e -> {
                    log.error(e.getMessage(), e);
                    return null;
                });
    }
}
