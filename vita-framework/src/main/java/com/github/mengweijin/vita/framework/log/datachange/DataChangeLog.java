package com.github.mengweijin.vita.framework.log.datachange;

import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.log.operation.EOperationType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mengweijin
 * @since 2026/01/18
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataChangeLog {

    /**
     * 实体类型
     */
    Class<? extends BaseEntity> entityClass();

    /**
     * 操作类型 {@link EOperationType}
     */
    EOperationType operationType() default EOperationType.UPDATE;

    /**
     * 业务 ID 的 Spring EL 表达式
     */
    String businessId();

}
