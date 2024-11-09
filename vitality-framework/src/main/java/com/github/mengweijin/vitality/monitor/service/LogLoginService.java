package com.github.mengweijin.vitality.monitor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vitality.framework.util.Ip2regionUtils;
import com.github.mengweijin.vitality.framework.util.ServletUtils;
import com.github.mengweijin.vitality.monitor.domain.entity.LogLogin;
import com.github.mengweijin.vitality.monitor.mapper.LogLoginMapper;
import com.github.mengweijin.vitality.system.domain.entity.User;
import com.github.mengweijin.vitality.system.enums.ELoginType;
import com.github.mengweijin.vitality.system.enums.EYesNo;
import com.github.mengweijin.vitality.system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.http.useragent.UserAgentInfo;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * <p>
 *  LogLogin Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class LogLoginService extends ServiceImpl<LogLoginMapper, LogLogin> {

    private UserService userService;

    /**
     * Custom paging query
     * @param page page
     * @param logLogin {@link LogLogin}
     * @return IPage
     */
    public IPage<LogLogin> page(IPage<LogLogin> page, LogLogin logLogin){
        LambdaQueryWrapper<LogLogin> query = new LambdaQueryWrapper<>();
        query
                .eq(StrUtil.isNotBlank(logLogin.getLoginType()), LogLogin::getLoginType, logLogin.getLoginType())
                .eq(StrUtil.isNotBlank(logLogin.getIp()), LogLogin::getIp, logLogin.getIp())
                .eq(StrUtil.isNotBlank(logLogin.getIpLocation()), LogLogin::getIpLocation, logLogin.getIpLocation())
                .eq(StrUtil.isNotBlank(logLogin.getBrowser()), LogLogin::getBrowser, logLogin.getBrowser())
                .eq(StrUtil.isNotBlank(logLogin.getPlatform()), LogLogin::getPlatform, logLogin.getPlatform())
                .eq(StrUtil.isNotBlank(logLogin.getOs()), LogLogin::getOs, logLogin.getOs())
                .eq(StrUtil.isNotBlank(logLogin.getSuccess()), LogLogin::getSuccess, logLogin.getSuccess())
                .eq(StrUtil.isNotBlank(logLogin.getErrorMsg()), LogLogin::getErrorMsg, logLogin.getErrorMsg())
                .eq(!Objects.isNull(logLogin.getId()), LogLogin::getId, logLogin.getId())
                .eq(!Objects.isNull(logLogin.getCreateBy()), LogLogin::getCreateBy, logLogin.getCreateBy())
                .eq(!Objects.isNull(logLogin.getCreateTime()), LogLogin::getCreateTime, logLogin.getCreateTime())
                .eq(!Objects.isNull(logLogin.getUpdateBy()), LogLogin::getUpdateBy, logLogin.getUpdateBy())
                .eq(!Objects.isNull(logLogin.getUpdateTime()), LogLogin::getUpdateTime, logLogin.getUpdateTime())
                .like(StrUtil.isNotBlank(logLogin.getUsername()), LogLogin::getUsername, logLogin.getUsername());
        query.orderByDesc(LogLogin::getCreateTime);
        return this.page(page, query);
    }

    public void addLoginLogAsync(String username, ELoginType loginType, String errorMsg, HttpServletRequest request) {
        CompletableFuture.runAsync(() -> {
            LogLogin logLogin = new LogLogin();
            if(request != null) {
                UserAgent userAgent = ServletUtils.getUserAgent(request);
                String ip = ServletUtils.getClientIP(request);
                logLogin.setIp(ip);
                logLogin.setIpLocation(Ip2regionUtils.search(ip));
                logLogin.setBrowser(Optional.ofNullable(userAgent).map(UserAgent::getBrowser).map(UserAgentInfo::getName).orElse(null));
                logLogin.setPlatform(Optional.ofNullable(userAgent).map(UserAgent::getPlatform).map(UserAgentInfo::getName).orElse(null));
                logLogin.setOs(Optional.ofNullable(userAgent).map(UserAgent::getOs).map(UserAgentInfo::getName).orElse(null));
            }
            logLogin.setUsername(username);
            logLogin.setLoginType(loginType.getValue());
            logLogin.setSuccess(StrUtil.isBlank(errorMsg) ? EYesNo.Y.getValue() : EYesNo.N.getValue());
            logLogin.setErrorMsg(errorMsg);

            User user = userService.getByUsername(username);
            Long userId = Optional.ofNullable(user).map(User::getId).orElse(null);
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
