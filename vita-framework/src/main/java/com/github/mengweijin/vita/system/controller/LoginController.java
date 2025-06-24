package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.ratelimit.ERateLimitStrategy;
import com.github.mengweijin.vita.framework.ratelimit.RateLimit;
import com.github.mengweijin.vita.framework.repeatsubmit.RepeatSubmit;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.system.domain.bo.LoginBO;
import com.github.mengweijin.vita.system.domain.vo.LoginUserVO;
import com.github.mengweijin.vita.system.service.ConfigService;
import com.github.mengweijin.vita.system.service.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.dromara.hutool.core.map.MapUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@Validated
@RestController
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    private ConfigService configService;

    @SaIgnore
    @RepeatSubmit(interval = 3000)
    @PostMapping("/login")
    public R<Map<String, Object>> login(@Valid @RequestBody LoginBO loginBO) {
        String token = loginService.login(loginBO);
        return R.ok(MapUtil.of("token", token));
    }

    @PostMapping("/logout")
    public R<Void> logout() {
        StpUtil.logout();
        return R.ok();
    }

    @SaIgnore
    @GetMapping("/captchaEnabled")
    public boolean getCaptchaEnabled() {
        return configService.getCaptchaEnabled();
    }

    @SaIgnore
    @GetMapping("/loginOtpEnabled")
    public boolean getLoginOtpEnabled() {
        return configService.getLoginOtpEnabled();
    }

    @SaIgnore
    @RateLimit(duration = 3, max = 1, strategy = ERateLimitStrategy.IP)
    @GetMapping("/captcha")
    public String getCaptcha() {
        return loginService.getCaptcha();
    }

    @GetMapping("/get/login-user")
    public LoginUserVO getLoginUser() {
        return LoginHelper.getLoginUser();
    }
}