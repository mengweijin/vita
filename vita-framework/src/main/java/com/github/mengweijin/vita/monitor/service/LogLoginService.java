package com.github.mengweijin.vita.monitor.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.vita.framework.util.Ip2regionUtils;
import com.github.mengweijin.vita.framework.util.ServletUtils;
import com.github.mengweijin.vita.monitor.domain.entity.LogLoginDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.enums.ELoginType;
import com.github.mengweijin.vita.system.enums.EYesNo;
import com.github.mengweijin.vita.monitor.mapper.LogLoginMapper;
import com.github.mengweijin.vita.system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.dromara.hutool.http.server.servlet.ServletUtil;
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
public class LogLoginService extends ServiceImpl<LogLoginMapper, LogLoginDO> {

    private UserService userService;

    /**
     * Custom paging query
     * @param page page
     * @param logLogin {@link LogLoginDO}
     * @return IPage
     */
    public IPage<LogLoginDO> page(IPage<LogLoginDO> page, LogLoginDO logLogin){
        LambdaQueryWrapper<LogLoginDO> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(logLogin.getLoginType()), LogLoginDO::getLoginType, logLogin.getLoginType())
                .eq(StrValidator.isNotBlank(logLogin.getIp()), LogLoginDO::getIp, logLogin.getIp())
                .eq(StrValidator.isNotBlank(logLogin.getIpLocation()), LogLoginDO::getIpLocation, logLogin.getIpLocation())
                .eq(StrValidator.isNotBlank(logLogin.getBrowser()), LogLoginDO::getBrowser, logLogin.getBrowser())
                .eq(StrValidator.isNotBlank(logLogin.getPlatform()), LogLoginDO::getPlatform, logLogin.getPlatform())
                .eq(StrValidator.isNotBlank(logLogin.getOs()), LogLoginDO::getOs, logLogin.getOs())
                .eq(StrValidator.isNotBlank(logLogin.getSuccess()), LogLoginDO::getSuccess, logLogin.getSuccess())
                .eq(StrValidator.isNotBlank(logLogin.getErrorMsg()), LogLoginDO::getErrorMsg, logLogin.getErrorMsg())
                .eq(!Objects.isNull(logLogin.getId()), LogLoginDO::getId, logLogin.getId())
                .eq(!Objects.isNull(logLogin.getCreateBy()), LogLoginDO::getCreateBy, logLogin.getCreateBy())
                .eq(!Objects.isNull(logLogin.getCreateTime()), LogLoginDO::getCreateTime, logLogin.getCreateTime())
                .eq(!Objects.isNull(logLogin.getUpdateBy()), LogLoginDO::getUpdateBy, logLogin.getUpdateBy())
                .eq(!Objects.isNull(logLogin.getUpdateTime()), LogLoginDO::getUpdateTime, logLogin.getUpdateTime())
                .like(StrValidator.isNotBlank(logLogin.getUsername()), LogLoginDO::getUsername, logLogin.getUsername());
        query.orderByDesc(LogLoginDO::getCreateTime);
        return this.page(page, query);
    }

    public void addLoginLogAsync(String username, ELoginType loginType, String errorMsg, HttpServletRequest request) {
        CompletableFuture.runAsync(() -> {
            LogLoginDO logLogin = new LogLoginDO();
            if(request != null) {
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
