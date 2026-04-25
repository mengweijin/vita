package com.github.mengweijin.vita.framework.validator.annotation;

import com.github.mengweijin.vita.framework.validator.CheckValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义业务校验注解
 *
 * @author mengweijin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Constraint(validatedBy = {CheckValidator.class})
public @interface Check {

    String message() default "Business check failed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends CheckValidator.CheckRule>[] rules();

}
