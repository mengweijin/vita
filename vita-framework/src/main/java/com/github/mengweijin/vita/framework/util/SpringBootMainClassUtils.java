package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.reflect.ClassUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * @author mengweijin
 * @since 2022/12/24
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SpringBootMainClassUtils {
    public static Class<?> getSpringBootApplicationClass() {
        ApplicationContext context = SpringUtil.getApplicationContext();
        Map<String, Object> annotatedBeans = context.getBeansWithAnnotation(SpringBootApplication.class);
        return annotatedBeans.isEmpty() ? null : annotatedBeans.values().toArray()[0].getClass();
    }

    public static String getSpringBootApplicationClassPackage() {
        return ClassUtil.getPackage(getSpringBootApplicationClass());
    }

}
