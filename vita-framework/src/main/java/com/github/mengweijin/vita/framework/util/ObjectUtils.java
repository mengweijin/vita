package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.bean.BeanUtil;
import cn.hutool.v7.core.text.StrUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @author mengweijin
 * @since 2026-04-12
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ObjectUtils {

    /**
     * 判断对象所有字段是否为空（null 或 ""空白符）
     *
     * @param bean Object
     * @return true/false
     */
    public static boolean isAllFieldsBlank(Object bean) {
        if (bean == null) {
            return true;
        }

        // 对象转Map（忽略静态/transient）
        Map<String, Object> fieldMap = BeanUtil.beanToMap(bean);

        // 所有值满足 isBlank
        return fieldMap.values().stream()
                .allMatch(value -> {
                    if (value == null) {
                        return true;
                    }
                    // 字符串：判断空白
                    if (value instanceof String str) {
                        return StrUtil.isBlank(str);
                    }
                    // 包装类型（Integer/Long）：!=null → 非空
                    return false;
                });
    }
}
