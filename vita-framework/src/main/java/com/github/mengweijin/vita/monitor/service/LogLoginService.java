package com.github.mengweijin.vita.monitor.service;

import cn.hutool.v7.core.text.StrValidator;
import cn.hutool.v7.extra.spring.SpringUtil;
import cn.hutool.v7.http.server.servlet.ServletUtil;
import cn.hutool.v7.http.useragent.UserAgent;
import cn.hutool.v7.http.useragent.UserAgentInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.enums.dict.ELoginType;
import com.github.mengweijin.vita.framework.enums.dict.EYesNo;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.util.IpRegionUtils;
import com.github.mengweijin.vita.framework.util.ServletUtils;
import com.github.mengweijin.vita.monitor.domain.entity.LogLoginDO;
import com.github.mengweijin.vita.monitor.domain.vo.LogLoginVO;
import com.github.mengweijin.vita.monitor.mapper.LogLoginMapper;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.home.HomeConsoleChartDataVO;
import com.github.mengweijin.vita.system.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
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
public class LogLoginService extends BaseVitaService<LogLoginMapper, LogLoginDO, LogLoginVO> {

    private UserService userService;

    @Override
    public LambdaQueryWrapper<LogLoginDO> buildQueryWrapper(LogLoginDO logLogin) {
        LambdaQueryWrapper<LogLoginDO> wrapper = Wrappers.lambdaQuery();
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
        wrapper.gt(logLogin.getStartCreateTime() != null, LogLoginDO::getCreateTime, logLogin.getStartCreateTime());
        wrapper.le(logLogin.getEndCreateTime() != null, LogLoginDO::getCreateTime, logLogin.getEndCreateTime());

        wrapper.like(StrValidator.isNotBlank(logLogin.getUsername()), LogLoginDO::getUsername, logLogin.getUsername());
        wrapper.like(StrValidator.isNotBlank(logLogin.getIp()), LogLoginDO::getIp, logLogin.getIp());
        return wrapper;
    }

    public void addLoginLogAsync(String username, ELoginType loginType, String errorMsg, HttpServletRequest request) {
        CompletableFuture.runAsync(() -> {
                    LogLoginDO logLogin = new LogLoginDO();
                    if (request != null) {
                        UserAgent userAgent = ServletUtils.getUserAgent(request);
                        String ip = ServletUtil.getClientIP(request);
                        logLogin.setIp(ip);
                        logLogin.setIpLocation(IpRegionUtils.search(ip));
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

    public Long getDailyUserLoginCount() {
        LocalDate localDate = LocalDate.now(Const.ZONE);
        LocalDateTime startTime = localDate.atTime(LocalTime.MIN);
        LocalDateTime endTime = localDate.atTime(LocalTime.MAX);
        return this.lambdaQuery().eq(LogLoginDO::getLoginType, ELoginType.LOGIN.getValue()).between(LogLoginDO::getCreateTime, startTime, endTime).count();
    }

    public Long getTotalUserLoginCount() {
        return this.lambdaQuery().eq(LogLoginDO::getLoginType, ELoginType.LOGIN.getValue()).count();
    }

    public List<HomeConsoleChartDataVO> selectDailyUserLoginCountBetweenTime(LocalDateTime startTime, LocalDateTime endTime) {
        return this.getBaseMapper().selectDailyUserLoginCountBetweenTime(startTime, endTime);
    }

    public IPage<LogLoginDO> pageByLoginUser(IPage<LogLoginDO> page) {
        String username = LoginHelper.getSessionUsername();
        LambdaQueryWrapper<LogLoginDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(LogLoginDO::getUsername, username);
        wrapper.orderByDesc(LogLoginDO::getCreateTime);
        return this.page(page, wrapper);
    }
}
