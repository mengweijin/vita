package com.github.mengweijin.vita.framework.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.exception.ServerException;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 */
public final class BeanCopyUtils extends BeanUtils {

    private BeanCopyUtils() {}

    public static <T> T copyBean(Object source, T target) {
        if (source == null) {
            return null;
        }
        copyProperties(source, target);
        return target;
    }

    public static <T> T copyBean(Object source, Class<T> cls) {
        if (source == null) {
            return null;
        }
        T object;
        try {
            object = cls.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            throw new ServerException(e);
        }
        copyProperties(source, object);
        return object;
    }

    public static <T> List<T> copyList(List<?> source, Class<T> cls) {
        List<T> target = new ArrayList<>();
        if (source != null && !source.isEmpty()){
            for (Object src: source) {
                T object = copyBean(src, cls);
                target.add(object);
            }
        }
        return target;
    }

    public static <T> IPage<T> copyPage(IPage<?> source, Class<T> cls) {
        if(source == null) {
            return new Page<>();
        }
        List<T> list = copyList(source.getRecords(), cls);
        Page<T> target = Page.of(source.getCurrent(), source.getSize(), source.getTotal());
        target.setRecords(list);
        return target;
    }
}
