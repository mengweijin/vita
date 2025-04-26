package com.github.mengweijin.vita.framework.mybatis.data.permission;

import com.github.mengweijin.vita.framework.constant.ConstColumn;
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

    @Getter
    enum Scope {

        USER(ConstColumn.CREATE_BY),

        DEPT(ConstColumn.DEPT_ID),

        ROLE(ConstColumn.ROLE_ID);

        private final String columnName;

        Scope(String columnName) {
            this.columnName = columnName;
        }
    }
}
