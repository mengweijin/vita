package com.github.mengweijin.vita.framework.mybatis.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author mengweijin
 * @since 2023/4/16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings({"unused"})
public abstract class ColumnConst {

    public static final String DEPT_ID = "DEPT_ID";
    public static final String ROLE_ID = "ROLE_ID";
    public static final String CREATE_BY = "CREATE_BY";
    public static final String UPDATE_BY = "UPDATE_BY";
}
