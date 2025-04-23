package com.github.mengweijin.vita.system.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author mengweijin
 * @since 2023/4/16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings({"unused"})
public abstract class ConfigConst {

    public static final String CAPTCHA_ENABLED = "vt_captcha_enabled";

    public static final String USER_INIT_PASSWORD = "vt_user_init_password";

    public static final String USER_PASSWORD_CHANGE_INTERVAL = "vt_user_password_change_interval";

    public static final String SYSTEM_ADMIN_ROLE_CODE = "vt_system_admin_role_code";

}
