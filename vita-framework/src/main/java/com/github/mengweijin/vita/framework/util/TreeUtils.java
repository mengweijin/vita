package com.github.mengweijin.vita.framework.util;

import com.github.mengweijin.vita.framework.exception.impl.ServerException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.math.NumberUtil;
import org.dromara.hutool.core.text.StrUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2025/4/26
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TreeUtils {

    public static TreeConfig createTreeConfig() {
        return new TreeConfig();
    }

    /**
     * 树构建
     *
     * @param list 源数据集合
     * @return List
     */
    public static List<Map<String, Object>> buildByBeanList(Collection<?> list) {
        return buildByBeanList(list, createTreeConfig());
    }

    public static List<Map<String, Object>> buildByBeanList(Collection<?> list, TreeConfig config) {
        List<Map<String, Object>> beanList = list.stream().map(BeanUtil::beanToMap).toList();
        return buildByMapList(beanList, config);
    }

    public static List<Map<String, Object>> buildByMapList(List<Map<String, Object>> list) {
        return buildByMapList(list, createTreeConfig());
    }

    public static List<Map<String, Object>> buildByMapList(List<Map<String, Object>> list, TreeConfig config) {
        // parentId == null 的所有 bean 的 id 先过滤出来，作为根节点
        List<Object> nullParentIdRootList = list.stream()
                .filter(map -> map.get(config.getParentIdKey()) == null)
                .map(map -> map.get(config.getIdKey()))
                .toList();

        // 根据 parentId 分组，此时 groupedMap 中的 key 既包含根节点，又包含子节点
        Map<Object, List<Map<String, Object>>> groupedMap = list.stream()
                // 过滤掉 parentId 为 null 的 key 值，否则 Collectors.groupingBy() 会报错
                .filter(map -> map.get(config.getParentIdKey()) != null)
                .collect(Collectors.groupingBy(map -> map.get(config.getParentIdKey())));


        List<Object> allIdList = list.stream().map(map -> map.get(config.getIdKey())).toList();

        // 从分组后的 map 中，筛选根节点（即 map 中 key 值（即 parentId）在 list 中找不到一条记录的，也作为根节点）
        List<Object> notExistsRootIdList = groupedMap.keySet().stream().filter(key -> !allIdList.contains(key)).toList();

        // 所有根节点 id 集合
        List<Object> rootIdList = new ArrayList<>(nullParentIdRootList);
        groupedMap.forEach((k, v) -> {
            if (notExistsRootIdList.contains(k)) {
                rootIdList.addAll(v.stream().map(m -> m.get(config.getIdKey())).toList());
            }
        });

        // 这里处理全部，给每个节点下面根据条件放 children 子节点
        for (Map<String, Object> nodeMap : list) {
            List<Map<String, Object>> children = groupedMap.get(nodeMap.get(config.getIdKey()));

            if (CollUtil.isNotEmpty(children)) {
                List<Map<String, Object>> sortedChildren = children.stream()
                        .sorted(Comparator.comparingInt(m -> parseInt(m.get(config.getSortKey()))))
                        .toList();
                nodeMap.put(config.getChildrenKey(), sortedChildren);
            } else {
                if (!config.getStrict()) {
                    nodeMap.put(config.getChildrenKey(), new ArrayList<>());
                }
            }
        }

        return list.stream()
                .filter(map -> rootIdList.contains(map.get(config.getIdKey())))
                .sorted(Comparator.comparingInt(m -> parseInt(m.get(config.getSortKey()))))
                .toList();
    }

    private static Integer parseInt(Object sortKeyValue) {
        if(sortKeyValue == null) {
            return Integer.MAX_VALUE;
        }
        if(!NumberUtil.isNumber(StrUtil.toString(sortKeyValue))) {
            throw new ServerException("The sortKey value is not a Integer! Please check your data or config[TreeConfig]!");
        }
        return (Integer) sortKeyValue;
    }

    @Data
    @Accessors(chain = true)
    public static class TreeConfig {

        /**
         * ID对应的字段名称
         */
        private String idKey = "id";

        /**
         * 父节点对应的字段名称
         */
        private String parentIdKey = "parentId";

        /**
         * 排序对应的字段名称
         */
        private String sortKey = "seq";

        /**
         * 子点对应的字段名称
         */
        private String childrenKey = "children";

        /**
         * 是否严格模式。为 true 则当子节点为空时将没有 children 属性
         */
        private Boolean strict = false;
    }

}
