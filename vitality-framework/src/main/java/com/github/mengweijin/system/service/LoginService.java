package com.github.mengweijin.system.service;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.framework.cache.CacheFactory;
import com.github.mengweijin.framework.exception.LoginFailedException;
import com.github.mengweijin.framework.util.BeanUtils;
import com.github.mengweijin.framework.util.ServletUtils;
import com.github.mengweijin.system.domain.entity.User;
import com.github.mengweijin.system.domain.vo.UserSessionVO;
import com.github.mengweijin.system.enums.ELoginType;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.swing.captcha.CaptchaUtil;
import org.dromara.hutool.swing.captcha.ICaptcha;
import org.dromara.hutool.swing.captcha.LineCaptcha;
import org.springframework.stereotype.Service;

import javax.cache.Cache;

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

    public String login(String username, String password, boolean rememberMe, String captchaCode) {
        HttpServletRequest request = ServletUtils.getRequest();
        UserAgent userAgent = ServletUtils.getUserAgent(request);
        String platformName = userAgent.getPlatform().getName();
        try {
            this.verifyCaptcha(captchaCode);

            User user = userService.getByUsername(username);
            if(user == null) {
                throw new LoginFailedException("The username or password incorrect!");
            }

            // 校验指定账号是否已被封禁，如果被封禁则抛出异常 `DisableServiceException`
            StpUtil.checkDisable(user.getId());

            if(!userService.checkPassword(password, user.getPassword())) {
                throw new LoginFailedException("The username or password incorrect!");
            }

            StpUtil.login(username, new SaLoginModel().setIsLastingCookie(rememberMe).setDevice(platformName));

            UserSessionVO userSessionVO = BeanUtils.copyBean(user, UserSessionVO.class);
            userService.setSessionUser(userSessionVO);
            return StpUtil.getTokenValue();
        } catch (RuntimeException e) {
            logLoginService.addLoginLogAsync(username, ELoginType.LOGIN, e.getMessage(), request);
            throw new LoginFailedException(e);
        }
    }

    public String createCaptcha() {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        LineCaptcha captcha = CaptchaUtil.ofLineCaptcha(200, 100, 4, 400);
        captcha.createCode();
        String sessionId = ServletUtils.getSession().getId();
        Cache<String, ICaptcha> cache = CacheFactory.getCaptchaCache();
        cache.remove(sessionId);
        cache.put(sessionId, captcha);
        return captcha.getImageBase64Data();
    }

    public void verifyCaptcha(String captchaCode) {
        if(StrUtil.isNotBlank(captchaCode)) {
            Cache<String, ICaptcha> cache = CacheFactory.getCaptchaCache();
            String sessionId = ServletUtils.getSession().getId();
            ICaptcha captcha = cache.get(sessionId);

            if(captcha == null || !captcha.verify(captchaCode)) {
                throw new LoginFailedException("Captcha code verify failed! " + captchaCode);
            }
        }
    }

}