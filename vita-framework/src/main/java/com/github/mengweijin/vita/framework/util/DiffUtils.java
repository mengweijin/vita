package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.collection.ListUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.log.datachange.DiffModel;
import com.github.mengweijin.vita.framework.enums.dict.EDiffType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.Diff;
import org.apache.commons.lang3.builder.DiffBuilder;
import org.apache.commons.lang3.builder.DiffResult;
import org.apache.commons.lang3.builder.ReflectionDiffBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jspecify.annotations.NonNull;

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
@SuppressWarnings({"java:S3776"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DiffUtils {

    public static final String DEFAULT_FIELD_NAME = "item";

    public static List<DiffModel> diffLists(List<?> o1, List<?> o2) {
        List<?> list1 = CollUtil.emptyIfNull(o1);
        List<?> list2 = CollUtil.emptyIfNull(o2);

        List<DiffModel> diff = new ArrayList<>();

        // 差集 list1 - list2     移除的项
        list1.stream()
                .filter(item -> !list2.contains(item))
                .forEach(i -> {
                    DiffModel diffModel = new DiffModel(EDiffType.REMOVED, DEFAULT_FIELD_NAME, StrUtil.toStringOrNull(i), null);
                    diff.add(diffModel);
                });

        // 差集 list2 - list1     新增的项
        list2.stream()
                .filter(item -> !list1.contains(item))
                .forEach(i -> {
                    DiffModel diffModel = new DiffModel(EDiffType.ADDED, DEFAULT_FIELD_NAME, null, StrUtil.toStringOrNull(i));
                    diff.add(diffModel);
                });

        return cleanNullAndEmptyEqualsValue(diff);
    }

    public static List<DiffModel> diffMaps(Map<String, String> m1, Map<String, String> m2, String... ignoreKeys) {
        List<DiffModel> diff = new ArrayList<>();
        if(m1 == null && m2 == null) {
            return diff;
        }

        List<String> ignoreKeyList = ListUtil.of(ignoreKeys);

        if(m1 == null) {
            for (Map.Entry<String, String> entry : m2.entrySet()) {
                if(!ignoreKeyList.contains(entry.getKey())) {
                    DiffModel diffModel = new DiffModel();
                    diffModel.setDiffType(EDiffType.ADDED);
                    diffModel.setFieldName(entry.getKey());
                    diffModel.setOldValue(null);
                    diffModel.setNewValue(entry.getValue());
                    diff.add(diffModel);
                }
            }
            return diff;
        }

        if(m2 == null) {
            for (Map.Entry<String, String> entry : m1.entrySet()) {
                if(!ignoreKeyList.contains(entry.getKey())) {
                    DiffModel diffModel = new DiffModel();
                    diffModel.setDiffType(EDiffType.REMOVED);
                    diffModel.setFieldName(entry.getKey());
                    diffModel.setOldValue(entry.getValue());
                    diffModel.setNewValue(null);
                    diff.add(diffModel);
                }
            }
            return diff;
        }

        Set<String> keySet = new HashSet<>();
        keySet.addAll(m1.keySet());
        keySet.addAll(m2.keySet());
        ignoreKeyList.forEach(keySet::remove);

        for (String key : keySet) {
            String v1 = m1.get(key);
            String v2 = m2.get(key);

            // 处理 null 值
            if (v1 == null && v2 == null) {
                continue;
            }

            if(v1 == null) {
                diff.add(new DiffModel(EDiffType.ADDED, key, null, v2));
            } else if (v2 == null) {
                diff.add(new DiffModel(EDiffType.REMOVED, key, v1, null));
            } else if (!v1.equals(v2)) {
                diff.add(new DiffModel(EDiffType.MODIFIED, key, v1, v2));
            }
        }
        return cleanNullAndEmptyEqualsValue(diff);
    }

    public static <T> List<DiffModel> diffBeans(T t1, T t2, String... ignoreFields) {
        List<DiffModel> list = new ArrayList<>();

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
        DiffResult<T> diffResult = diffBuilder.build();

        List<Diff<?>> diffs = diffResult.getDiffs();
        for (Diff<?> diff : diffs) {
            DiffModel diffModel = getDiffModel(diff);
            list.add(diffModel);
        }
        return cleanNullAndEmptyEqualsValue(list);
    }

    private static @NonNull DiffModel getDiffModel(Diff<?> diff) {
        DiffModel diffModel = new DiffModel();
        if(diff.getLeft() == null) {
            diffModel.setDiffType(EDiffType.ADDED);
        } else if(diff.getRight() == null) {
            diffModel.setDiffType(EDiffType.REMOVED);
        } else {
            diffModel.setDiffType(EDiffType.MODIFIED);
        }
        // 发生变化的字段名
        diffModel.setFieldName(diff.getFieldName());
        // 字段对应的旧值
        diffModel.setOldValue(StrUtil.nullIfBlank(StrUtil.toStringOrNull(diff.getLeft())));
        // 字段对应的新值
        diffModel.setNewValue(StrUtil.nullIfBlank(StrUtil.toStringOrNull(diff.getRight())));
        return diffModel;
    }

    private static List<DiffModel> cleanNullAndEmptyEqualsValue(List<DiffModel> list) {
        return list.stream().filter(item -> {
            String oldValue = StrUtil.defaultIfBlank(item.getOldValue(), Const.EMPTY);
            String newValue = StrUtil.defaultIfBlank(item.getNewValue(), Const.EMPTY);
            return !oldValue.equals(newValue);
        }).toList();
    }

}
