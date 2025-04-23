package com.github.mengweijin.vita.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EDictType implements IEnum<String> {

    VT_DISABLED("vt_disabled"),

    VT_YES_NO("vt_yes_no"),

    VT_SUCCEEDED("vt_succeeded"),

    VT_USER_GENDER("vt_user_gender"),

    VT_LOGIN_TYPE("vt_login_type"),

    VT_MENU_TYPE("vt_menu_type"),

    VT_PASSWORD_LEVEL("vt_password_level"),

    VT_OPERATION_LOG_TYPE("vt_operation_log_type"),

    VT_HTTP_REQUEST_TYPE("vt_http_request_type"),

    VT_LOG_LEVEL("vt_log_level"),

    VT_RELEASED("vt_released");

    private final String value;

}
