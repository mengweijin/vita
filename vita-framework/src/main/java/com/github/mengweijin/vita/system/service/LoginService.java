package com.github.mengweijin.vita.system.service;

import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import cn.hutool.v7.http.server.servlet.ServletUtil;
import cn.hutool.v7.http.useragent.Platform;
import cn.hutool.v7.http.useragent.UserAgent;
import cn.hutool.v7.swing.captcha.AbstractCaptcha;
import cn.hutool.v7.swing.captcha.CaptchaUtil;
import cn.hutool.v7.swing.captcha.generator.MathGenerator;
import com.github.mengweijin.vita.framework.cache.CacheFactory;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.properties.VitaProperties;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.framework.util.ServletUtils;
import com.github.mengweijin.vita.monitor.service.LogLoginService;
import com.github.mengweijin.vita.system.domain.bo.LoginBO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.user.UserSessionVO;
import com.github.mengweijin.vita.system.enums.dict.ELoginType;
import com.github.mengweijin.vita.system.enums.dict.EYesNo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 登录 服务类
 *
 * @author mengweijin
 * @since 2023-06-09
 */
@Service
@AllArgsConstructor
public class LoginService {

    private UserService userService;

    private LogLoginService logLoginService;

    private MathGenerator mathGenerator;

    private VitaProperties vitaProperties;

    public String login(LoginBO loginBO) {
        HttpServletRequest request = ServletUtils.getRequest();
        if (vitaProperties.getLoginCaptchaEnabled()) {
            boolean validate = this.checkCaptcha(request, loginBO.getCaptcha());
            if (!validate) {
                throw new ClientException(I18nUtils.msg("system.login.captcha.code.invalid"));
            }
        }

        UserAgent userAgent = ServletUtils.getUserAgent(request);
        String platformName = Optional.ofNullable(userAgent).map(UserAgent::getPlatform).map(Platform::getName).orElse(null);

        // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
        StpUtil.checkDisable(loginBO.getUsername());

        UserDO user = userService.getByUsername(loginBO.getUsername());

        if (user == null) {
            String msg = I18nUtils.msg("system.login.username.or.password.incorrect");
            logLoginService.addLoginLogAsync(loginBO.getUsername(), ELoginType.LOGIN, msg, request);
            throw new ClientException(msg);
        }

        if(EYesNo.Y.getValue().equalsIgnoreCase(user.getDisabled())) {
            String msg = I18nUtils.msg("system.login.account.disabled");
            logLoginService.addLoginLogAsync(loginBO.getUsername(), ELoginType.LOGIN, msg, request);
            throw new ClientException(msg);
        }

        if (!userService.checkPassword(loginBO.getPassword(), user.getPassword(), user.getSalt())) {
            String msg = I18nUtils.msg("system.login.username.or.password.incorrect");
            logLoginService.addLoginLogAsync(loginBO.getUsername(), ELoginType.LOGIN, msg, request);
            throw new ClientException(msg);
        }

        SaLoginParameter saLoginParameter = new SaLoginParameter()
                .setIsLastingCookie(loginBO.isRemember())
                .setDeviceId(loginBO.getDeviceId())
                .setDeviceType(platformName);
        if(loginBO.isRemember()) {
            // 7 天免登录（7 * 24 * 60 * 60）。覆盖 sa-token.timeout 配置。
            saLoginParameter.setTimeout(604800);
            saLoginParameter.setActiveTimeout(604800);
        }

        StpUtil.login(user.getUsername(), saLoginParameter);

        UserSessionVO loginUser = this.buildSessionUser(user);
        StpUtil.getTokenSession().set(SaSession.USER, loginUser);

        return loginUser.getToken();
    }

    private UserSessionVO buildSessionUser(UserDO user) {
        UserSessionVO loginUser = new UserSessionVO();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setNickname(user.getNickname());
        loginUser.setToken(StpUtil.getTokenValue());
        return loginUser;
    }

    public String getCaptcha() {
        String ip = ServletUtil.getClientIP(ServletUtils.getRequest());
        Cache captchaCache = CacheFactory.getCaptchaCache();

        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        AbstractCaptcha captcha = CaptchaUtil.ofLineCaptcha(140, 40, 4, 40);
        // 自定义验证码内容为四则运算方式，每个数字的长度为 1 位
        captcha.setGenerator(mathGenerator);

        captcha.createCode();
        // 放入缓存
        captchaCache.put(ip, captcha);
        return captcha.getImageBase64Data();
    }

    private boolean checkCaptcha(HttpServletRequest request, @NotBlank String captcha) {
        Cache captchaCache = CacheFactory.getCaptchaCache();
        String ip = ServletUtil.getClientIP(request);
        AbstractCaptcha abstractCaptcha = captchaCache.get(ip, AbstractCaptcha.class);
        return abstractCaptcha != null && abstractCaptcha.verify(captcha);
    }

}
