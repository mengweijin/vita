package com.github.mengweijin.vita.system.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author mengweijin
 * @since 2023/4/16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings({"unused"})
public abstract class MenuConst {

    public static final long ROOT_ID = 0L;

    /**
     * 拥有所有权限的格式
     */
    public static final String ALL_PERMISSIONS = "*:*:*";

}
