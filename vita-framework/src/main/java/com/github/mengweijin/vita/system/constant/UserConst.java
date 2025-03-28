package com.github.mengweijin.vita.system.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author mengweijin
 * @since 2023/4/16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings({"unused"})
public abstract class UserConst {

    public static final long ADMIN_USER_ID = 1L;

    public static final String ADMIN_USERNAME = "admin";

    public static final String SESSION_LOGIN_USER = "loginUser";

}
