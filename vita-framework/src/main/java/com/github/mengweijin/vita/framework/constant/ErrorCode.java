package com.github.mengweijin.vita.framework.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 前三位数字为标准的 {@link org.springframework.http.HttpStatus}类别，后三位数字为具体业务错误码
 *
 * @author mengweijin
 * @since 2024/8/31
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorCode {

    /**
     * 需要二级认证
     */
    public static final int SECONDARY_AUTH_REQUIRED = 400_1001;
}
