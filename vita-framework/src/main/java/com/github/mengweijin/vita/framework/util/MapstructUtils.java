package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.extra.spring.SpringUtil;
import io.github.linpeilie.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author mengweijin
 * @since 2022/5/17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapstructUtils {

    private static volatile Converter instance = null;

    public static Converter getInstance() {
        if (instance == null) {
            synchronized (MapstructUtils.class) {
                if (instance == null) {
                    instance = SpringUtil.getBean(Converter.class);
                }
            }
        }
        return instance;
    }
}
