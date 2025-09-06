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

    LOGIN_CAPTCHA_ENABLED("vt_login_captcha_enabled"),

    LOGIN_OTP_ENABLED("vt_login_otp_enabled"),

    USER_DEFAULT_ROLE_CODE("vt_user_default_role_code"),

    USER_PASSWORD_DEFAULT("vt_user_password_default"),

    USER_PASSWORD_CHANGE_INTERVAL("vt_user_password_change_interval"),

    SYSTEM_ADMIN_ROLE_CODE("vt_system_admin_role_code"),

    LOG_RECORD_LEVEL("vt_log_record_level"),

    ;

    private final String value;

}
