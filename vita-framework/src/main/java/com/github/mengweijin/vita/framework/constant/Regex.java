package com.github.mengweijin.vita.framework.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.regex.RegexPool;

/**
 * @author mengweijin
 * @since 2024/8/31
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Regex implements RegexPool {

    /**
     * 密码正则（密码长度应该在8-18位之间，并且为数字、字母、符号的至少任意两种的组合）
     */
    public static final String PWD_PATTERN = "^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)]|[()])+$)(?!^.*[\\u4E00-\\u9FA5].*$)([^(0-9a-zA-Z)]|[()]|[a-z]|[A-Z]|[0-9]){8,18}$";

    /**
     * TOTP 动态口令正则
     */
    public static final String TOTP_PATTERN = "^\\d{4}$";
}
