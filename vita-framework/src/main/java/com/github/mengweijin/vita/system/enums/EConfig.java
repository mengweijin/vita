package com.github.mengweijin.vita.system.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EConfig implements IEnum<String> {

    /**
     * 用户登录是否启用验证码
     */
    LOGIN_CAPTCHA_ENABLED("vt_login_captcha_enabled"),

    /**
     * 用户登录是否启用动态口令验证
     */
    LOGIN_OTP_ENABLED("vt_login_otp_enabled"),

    /**
     * 用户默认角色编码
     */
    USER_DEFAULT_ROLE_CODE("vt_user_default_role_code"),

    /**
     * 用户初始密码
     */
    USER_PASSWORD_DEFAULT("vt_user_password_default"),

    /**
     * 修改密码的时间间隔
     */
    USER_PASSWORD_CHANGE_INTERVAL("vt_user_password_change_interval"),

    /**
     * 系统管理员角色编码
     */
    SYSTEM_ADMIN_ROLE_CODE("vt_system_admin_role_code"),

    /**
     * 系统日志记录的级别
     */
    LOG_RECORD_LEVEL("vt_log_record_level"),

    ;

    private final String value;

}
