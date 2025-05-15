package com.github.mengweijin.vita.framework.mybatis.data;

import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.impl.ServerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @author mengweijin
 * @since 2023/5/7
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MapperUtils {

    public static <T extends Annotation, R> R processMethodExpression(String mappedStatementId, Class<T> annotationClass, Function<T, R> function) {
        try {
            Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(Const.DOT)));
            String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(Const.DOT) + 1);
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                T annotation = method.getAnnotation(annotationClass);
                if (annotation != null && method.getName().equals(methodName)) {
                    return function.apply(annotation);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new ServerException(e);
        }

        return null;
    }
}
