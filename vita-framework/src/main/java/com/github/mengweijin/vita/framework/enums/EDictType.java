package com.github.mengweijin.vita.framework.enums;

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

    /**
     * 停用/启用
     */
    VT_DISABLED("vt_disabled"),

    /**
     * 是/否
     */
    VT_YES_NO("vt_yes_no"),

    /**
     * 成功/失败
     */
    VT_SUCCEEDED("vt_succeeded"),

    /**
     * 用户性别
     */
    VT_USER_GENDER("vt_user_gender"),

    /**
     * 登录类型
     */
    VT_LOGIN_TYPE("vt_login_type"),

    /**
     * 菜单类型
     */
    VT_MENU_TYPE("vt_menu_type"),

    /**
     * 密码强度
     */
    VT_PASSWORD_LEVEL("vt_password_level"),

    /**
     * 操作日志类型
     */
    VT_OPERATION_LOG_TYPE("vt_operation_log_type"),

    /**
     * HTTP请求类型
     */
    VT_HTTP_REQUEST_TYPE("vt_http_request_type"),

    /**
     * 系统日志记录级别
     */
    VT_LOG_LEVEL("vt_log_level"),

    /**
     * 已发布/未发布
     */
    VT_RELEASED("vt_released"),

    /**
     * 差异类型枚举
     */
    VT_DIFF_TYPE("vt_diff_type"),

    /**
     * 二级认证模式
     */
    VT_SAFE_MODE("vt_safe_mode");

    private final String value;

}
