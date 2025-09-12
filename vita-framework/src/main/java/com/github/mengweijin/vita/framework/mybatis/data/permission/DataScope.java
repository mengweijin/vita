package com.github.mengweijin.vita.framework.mybatis.data.permission;

import com.github.mengweijin.vita.system.constant.VitaConst;
import lombok.Getter;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can only be used in *Mapper.java. More detail refer to {@link BaseDataPermissionHandler}
 * @author mengweijin
 * @since 2022/11/20
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface DataScope {

    String tableAlias() default "";

    /**
     * For Examples:
     * DEPT: dept_id
     * ROLE: role_id
     * USER: create_by
     * */
    String tableColumnName() default "";

    Scope scope() default Scope.USER;

    /**
     * 数据范围枚举
     */
    @Getter
    enum Scope {

        /**
         * 用户级数据范围
         */
        USER(VitaConst.COLUMN_CREATE_BY),

        /**
         * 部门级数据范围
         */
        DEPT(VitaConst.COLUMN_DEPT_ID),

        /**
         * 角色级数据范围
         */
        ROLE(VitaConst.COLUMN_ROLE_ID);

        private final String columnName;

        Scope(String columnName) {
            this.columnName = columnName;
        }
    }
}
