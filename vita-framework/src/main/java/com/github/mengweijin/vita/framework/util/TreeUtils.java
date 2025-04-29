package com.github.mengweijin.vita.framework.util;

import org.dromara.hutool.core.bean.BeanUtil;
import org.dromara.hutool.core.collection.CollUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2025/4/26
 */
public class TreeUtils {

    /**
     * 树构建
     *
     * @param list 源数据集合
     * @return List
     */
    public static List<Map<String, Object>> build(Collection<?> list) {
        List<Map<String, Object>> beanList = list.stream().map(BeanUtil::beanToMap).toList();

        // parentId == null 的所有 bean 的 id 先过滤出来，作为根节点
        List<Object> nullParentIdRootList = beanList.stream()
                .filter(map -> map.get("parentId") == null)
                .map(map -> map.get("id"))
                .toList();

        // 根据 parentId 分组，此时 groupedMap 中的 key 既包含根节点，又包含子节点
        Map<Object, List<Map<String, Object>>> groupedMap = beanList.stream()
                // 过滤掉 parentId 为 null 的 key 值，否则 Collectors.groupingBy() 会报错
                .filter(map -> map.get("parentId") != null)
                .collect(Collectors.groupingBy(map -> map.get("parentId")));


        List<Object> allIdList = beanList.stream().map(map -> map.get("id")).toList();

        // 从分组后的 map 中，筛选根节点（即 map 中 key 值（即 parentId）在 beanList 中找不到一条记录的，也作为根节点）
        List<Object> notExistsRootIdList = groupedMap.keySet().stream().filter(key -> !allIdList.contains(key)).toList();

        // 所有根节点 id 集合
        List<Object> rootIdList = new ArrayList<>(nullParentIdRootList);
        groupedMap.forEach((k, v) -> {
            if(notExistsRootIdList.contains(k)) {
                rootIdList.addAll(v.stream().map(m -> m.get("id")).toList());
            }
        });

        // 这里处理全部，给每个节点下面根据条件放 children 子节点
        for (Map<String, Object> nodeMap : beanList) {
            List<Map<String, Object>> children = groupedMap.get(nodeMap.get("id"));
            if (CollUtil.isNotEmpty(children)) {
                nodeMap.put("children", children);
            }
        }

        return beanList.stream().filter(map -> rootIdList.contains(map.get("id"))).toList();
    }

}
