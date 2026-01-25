package com.github.mengweijin.vita.framework.log.operation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mengweijin
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * 操作标题
     */
    String title() default "";

    /**
     * 操作类型
     */
    EOperationType operationType() default EOperationType.OTHER;

    /**
     * 是否保存请求参数
     */
    boolean saveRequestData() default true;

    /**
     * 是否保存响应参数
     */
    boolean saveResponseData() default true;

}
