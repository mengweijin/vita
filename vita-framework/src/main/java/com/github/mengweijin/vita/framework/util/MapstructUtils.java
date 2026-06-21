package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import io.github.linpeilie.Converter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.warm.flow.core.utils.page.Page;

import java.util.List;

/**
 * @author mengweijin
 * @since 2022/5/17
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MapstructUtils {

    private static volatile Converter instance = null;

    public static Converter getConverter() {
        if (instance == null) {
            synchronized (MapstructUtils.class) {
                if (instance == null) {
                    instance = SpringUtil.getBean(Converter.class);
                }
            }
        }
        return instance;
    }

    /**
     * WarmFlow Page to PageQuery<T>
     */
    public static <T> PageQuery<T> convertToPageQuery(Page<?> page, Class<T> targetType) {
        List<T> converted = getConverter().convert(page.getList(), targetType);
        return PageQuery.of(page.getPageNum(), page.getPageSize(), page.getTotal(), converted);
    }

    /**
     * MyBatisPlus IPage/Page to PageQuery<T>
     */
    public static <T> PageQuery<T> convertToPageQuery(IPage<?> page, Class<T> targetType) {
        List<T> converted = getConverter().convert(page.getRecords(), targetType);
        return PageQuery.of(page.getCurrent(), page.getSize(), page.getTotal(), converted);
    }
}
