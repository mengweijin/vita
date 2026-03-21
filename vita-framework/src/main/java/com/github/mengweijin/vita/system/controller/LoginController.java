package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.v7.core.map.MapUtil;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.properties.VitaProperties;
import com.github.mengweijin.vita.framework.ratelimit.ERateLimitStrategy;
import com.github.mengweijin.vita.framework.ratelimit.RateLimit;
import com.github.mengweijin.vita.framework.repeatsubmit.RepeatSubmit;
import com.github.mengweijin.vita.system.domain.bo.LoginBO;
import com.github.mengweijin.vita.system.service.LoginService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    private VitaProperties vitaProperties;

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
        return vitaProperties.getLoginCaptchaEnabled();
    }

    @SaIgnore
    @RateLimit(duration = 3, max = 1, strategy = ERateLimitStrategy.IP)
    @GetMapping("/captcha")
    public String getCaptcha() {
        return loginService.getCaptcha();
    }

}
