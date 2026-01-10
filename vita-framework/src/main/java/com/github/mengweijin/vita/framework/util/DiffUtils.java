package com.github.mengweijin.vita.framework.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author mengweijin
 * @since 2026/1/3
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DiffUtils {

    public static <T> DiffResult<?> diff(T t1, T t2) {
        DiffBuilder<Object> builder = DiffBuilder.builder().setLeft(t1).setRight(t2).setStyle(ToStringStyle.JSON_STYLE).build();
        ReflectionDiffBuilder<Object> diffBuilder = ReflectionDiffBuilder.builder().setDiffBuilder(builder).build();
        return diffBuilder.build();
    }
}
