package com.github.mengweijin.vita.framework.cache;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * {@link CacheSpecification}
 *
 * @author mengweijin
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CacheNames {

    public static final String DICT_VAL_TO_LABEL = "DICT_VAL_TO_LABEL#1d";

    public static final String DICT_TYPE_ID_TO_CODE = "DICT_TYPE_ID_TO_CODE#1d";

    public static final String DEPT_ID_TO_NAME = "DEPT_ID_TO_NAME#1d";

    public static final String USER_ID_TO_USERNAME = "USER_ID_TO_USERNAME#1d";

    public static final String USER_ID_TO_NICKNAME = "USER_ID_TO_NICKNAME#1d";

    public static final String USER_ID_TO_AVATAR = "USER_ID_TO_AVATAR#1d";

    public static final String REPEAT_SUBMIT = "REPEAT_SUBMIT#10s";

    public static final String SSE_EMITTER_MESSAGE = "SSE_EMITTER_MESSAGE#2h";

    public static final String RATE_LIMIT = "RATE_LIMIT#5m";

    public static final String CAPTCHA = "CAPTCHA#1m";

    public static final String CATEGORY_ID_TO_NAME = "CATEGORY_ID_TO_NAME#1d";
}
