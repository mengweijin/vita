package com.github.mengweijin.vita.system.service;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import com.github.mengweijin.vita.framework.cache.CacheFactory;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.util.AESUtils;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.framework.util.ServletUtils;
import com.github.mengweijin.vita.framework.util.TOTPUtils;
import com.github.mengweijin.vita.monitor.service.LogLoginService;
import com.github.mengweijin.vita.system.domain.bo.LoginBO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.LoginUserVO;
import com.github.mengweijin.vita.system.enums.ELoginType;
import com.github.mengweijin.vita.system.enums.EYesNo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.http.server.servlet.ServletUtil;
import org.dromara.hutool.http.useragent.Platform;
import org.dromara.hutool.http.useragent.UserAgent;
import org.dromara.hutool.swing.captcha.AbstractCaptcha;
import org.dromara.hutool.swing.captcha.CaptchaUtil;
import org.dromara.hutool.swing.captcha.generator.MathGenerator;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
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

    private RoleService roleService;

    private MenuService menuService;

    private LogLoginService logLoginService;

    private ConfigService configService;

    private MathGenerator mathGenerator;

    public String login(LoginBO loginBO) {
        HttpServletRequest request = ServletUtils.getRequest();
        if (configService.getCaptchaEnabled()) {
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

        // TOTP
        if(configService.getLoginOtpEnabled()) {
            String totpSecretKey = user.getTotp();
            if(StrUtil.isNotBlank(totpSecretKey)) {
                String decrypted = AESUtils.getAES().decryptStr(totpSecretKey);
                boolean validated = TOTPUtils.validate(decrypted, loginBO.getOtp());
                if(!validated) {
                    String msg = I18nUtils.msg("system.login.dynamic.password.expired");
                    throw new ClientException(msg);
                }
            }
        }

        SaLoginParameter saLoginParameter = new SaLoginParameter()
                .setIsLastingCookie(loginBO.isRemember())
                .setDeviceType(platformName);
        if(loginBO.isRemember()) {
            // 7 天免登录（7 * 24 * 60 * 60）。覆盖 sa-token.timeout 配置。
            saLoginParameter.setTimeout(604800);
        }

        StpUtil.login(loginBO.getUsername(), saLoginParameter);

        LoginUserVO loginUser = this.buildLoginUser(user);
        LoginHelper.setLoginUser(loginUser);

        return loginUser.getToken();
    }

    private LoginUserVO buildLoginUser(UserDO user) {
        LoginUserVO loginUser = new LoginUserVO();
        loginUser.setUserId(user.getId());
        loginUser.setUsername(user.getUsername());
        loginUser.setNickname(user.getNickname());
        loginUser.setAvatar(userService.getAvatarById(user.getId()));
        loginUser.setRoles(roleService.getRoleCodeByUsername(user.getUsername()));
        loginUser.setPermissions(menuService.getMenuPermissionListByUsername(user.getUsername()));
        loginUser.setToken(StpUtil.getTokenValue());
        return loginUser;
    }

    public String getCaptcha() {
        String ip = ServletUtil.getClientIP(ServletUtils.getRequest());
        Cache<String, AbstractCaptcha> captchaCache = CacheFactory.getCaptchaCache();

        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        AbstractCaptcha captcha = CaptchaUtil.ofLineCaptcha(140, 40, 4, 50);
        // 自定义验证码内容为四则运算方式，每个数字的长度为 1 位
        captcha.setGenerator(mathGenerator);

        captcha.createCode();
        // 放入缓存
        captchaCache.put(ip, captcha);
        return captcha.getImageBase64Data();
    }

    private boolean checkCaptcha(HttpServletRequest request, @NotBlank String captcha) {
        Cache<String, AbstractCaptcha> captchaCache = CacheFactory.getCaptchaCache();
        String ip = ServletUtil.getClientIP(request);
        AbstractCaptcha abstractCaptcha = captchaCache.get(ip);
        return abstractCaptcha != null && abstractCaptcha.verify(captcha);
    }

}
