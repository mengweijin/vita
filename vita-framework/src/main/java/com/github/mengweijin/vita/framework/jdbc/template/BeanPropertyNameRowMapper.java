package com.github.mengweijin.vita.framework.jdbc.template;

import org.dromara.hutool.core.text.CharSequenceUtil;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

/**
 * @author mengweijin
 * @since 2022/10/29
 */
@SuppressWarnings({"unused"})
public class BeanPropertyNameRowMapper<T> extends BeanPropertyRowMapper<T> {

    /**
     * 自定义数据库字段到实体类属性的映射规则。额外增加驼峰命名规则的映射。
     * @param pd the property descriptor discovered on initialization
     * @return a set of mapped names
     */
    @Override
    protected Set<String> mappedNames(PropertyDescriptor pd) {
        Set<String> mappedNames = new HashSet<>(4);
        mappedNames.add(lowerCaseName(pd.getName()));
        mappedNames.add(underscoreName(pd.getName()));
        mappedNames.add(camelCaseName(pd.getName()));
        return mappedNames;
    }

    protected String camelCaseName(String name) {
        return CharSequenceUtil.toCamelCase(name);
    }

}
