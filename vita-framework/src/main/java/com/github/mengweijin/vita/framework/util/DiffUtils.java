package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.log.datachange.DiffModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author mengweijin
 * @since 2026/1/3
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DiffUtils {

    public static <M extends Map<K, V>, K, V> List<DiffModel<K, V>> diffMaps(M m1, M m2) {
        List<DiffModel<K, V>> diff = new ArrayList<>();
        if(m1 == null && m2 == null) {
            return diff;
        }

        if(m1 == null) {
            for (Map.Entry<K, V> entry : m2.entrySet()) {
                DiffModel<K, V> diffModel = new DiffModel<>();
                diffModel.setDiffType(DiffModel.DiffType.ADDED);
                diffModel.setFieldName(entry.getKey());
                diffModel.setOldValue(null);
                diffModel.setNewValue(entry.getValue());
                diff.add(diffModel);
            }
            return diff;
        }

        if(m2 == null) {
            for (Map.Entry<K, V> entry : m1.entrySet()) {
                DiffModel<K, V> diffModel = new DiffModel<>();
                diffModel.setDiffType(DiffModel.DiffType.REMOVED);
                diffModel.setFieldName(entry.getKey());
                diffModel.setOldValue(entry.getValue());
                diffModel.setNewValue(null);
                diff.add(diffModel);
            }
            return diff;
        }

        Set<K> keySet = new HashSet<>();
        keySet.addAll(m1.keySet());
        keySet.addAll(m2.keySet());
        for (K key : keySet) {
            V v1 = m1.get(key);
            V v2 = m2.get(key);

            // 处理 null 值
            if (v1 == null && v2 == null) {
                continue;
            }

            if(v1 == null) {
                diff.add(new DiffModel<>(DiffModel.DiffType.ADDED, key, null, v2));
                continue;
            }

            if (v2 == null) {
                diff.add(new DiffModel<>(DiffModel.DiffType.REMOVED, key, v1, null));
                continue;
            }

            if (!v1.equals(v2)) {
                diff.add(new DiffModel<>(DiffModel.DiffType.MODIFIED, key, v1, v2));
            }
        }
        return diff;
    }



    public static <T> List<DiffModel<String, String>> getDiffModelByApacheCommonsLang3(T t1, T t2, String... ignoreFields) {
        List<DiffModel<String, String>> list = new ArrayList<>();

        DiffResult<T> diffResult = diffBeanByApacheCommonsLang3(t1, t2, ignoreFields);
        List<Diff<?>> diffs = diffResult.getDiffs();
        for (Diff<?> diff : diffs) {
            DiffModel<String, String> diffModel = new DiffModel<>();
            // 发生变化的字段名
            diffModel.setFieldName(diff.getFieldName());
            // 字段对应的旧值
            diffModel.setOldValue(StrUtil.toStringOrNull(diff.getLeft()));
            // 字段对应的新值
            diffModel.setNewValue(StrUtil.toStringOrNull(diff.getRight()));
            list.add(diffModel);
        }
        return list;
    }

    private static <T> DiffResult<T> diffBeanByApacheCommonsLang3(T t1, T t2, String... ignoreFields) {
        // DiffBuilder.<T>builder()：显式指定类型参数，告诉编译器泛型方法应该使用什么类型参数，在类型推断不明确时是必需的。如果不写，编译器会尝试推断，但有可能推断为 DiffBuilder<Object>
        DiffBuilder<T> builder = DiffBuilder.<T>builder()
                .setLeft(t1)
                .setRight(t2)
                .setStyle(ToStringStyle.JSON_STYLE)
                .build();
        ReflectionDiffBuilder<T> diffBuilder = ReflectionDiffBuilder.<T>builder()
                .setDiffBuilder(builder)
                .setExcludeFieldNames(ignoreFields)
                .build();
        return diffBuilder.build();
    }

}
