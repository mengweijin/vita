package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import jakarta.validation.ConstraintViolation;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author mengweijin
 * @since 2025/9/13
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtils {

    public static void validate(Object object) throws IllegalArgumentException {
        LocalValidatorFactoryBean validator = SpringUtil.getBean(LocalValidatorFactoryBean.class);
        Set<ConstraintViolation<Object>> set = validator.validate(object);
        if (!set.isEmpty()) {
            String className = object.getClass().getName();
            // 拼接错误信息
            String errors = set.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.joining(";  "));

            String message = StrUtil.format("Bean Class [{}] validate failed: {}", className, errors);
            throw new IllegalArgumentException(message);
        }
    }

}
