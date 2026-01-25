package com.github.mengweijin.vita.system.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author mengweijin
 * @since 2023/4/16
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class VitaConst {

    // region ----- User

    public static final long USER_ADMIN_ID = 1L;

    public static final String USER_ADMIN_USERNAME = "admin";

    // endregion

    // region ----- Role

    public static final String ROLE_ADMIN_CODE = "role_admin";

    public static final String ROLE_GENERAL_CODE = "role_general";

    // endregion

    // region ----- <TABLE_NAME>

    public static final String TABLE_VT_USER = "VT_USER";
    public static final String TABLE_VT_DEPT = "VT_DEPT";
    public static final String TABLE_VT_ROLE = "VT_ROLE";
    public static final String TABLE_VT_USER_DEPT = "VT_USER_DEPT";
    public static final String TABLE_VT_USER_ROLE = "VT_USER_ROLE";

    // endregion

    // region ----- <COLUMN_NAME>

    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_DEPT_ID = "DEPT_ID";
    public static final String COLUMN_ROLE_ID = "ROLE_ID";
    public static final String COLUMN_CREATE_BY = "CREATE_BY";
    public static final String COLUMN_CREATE_TIME = "CREATE_TIME";
    public static final String COLUMN_UPDATE_BY = "UPDATE_BY";
    public static final String COLUMN_UPDATE_TIME = "UPDATE_TIME";

    // endregion
}
