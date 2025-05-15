package com.github.mengweijin.vita.framework.util;

import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import org.dromara.hutool.core.map.MapUtil;
import org.dromara.hutool.core.reflect.TypeUtil;
import org.dromara.hutool.core.tree.MapTree;
import org.dromara.hutool.core.tree.TreeNode;
import org.dromara.hutool.core.tree.TreeUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 * @since 2025/4/29
 */
class TreeUtilsTest {

    @Test
    void build() {
        List<CategoryDO> list = categoryDataList();
        List<Map<String, Object>> treeList = TreeUtils.buildByBeanList(list);
        Assertions.assertEquals(3, treeList.size());

        treeList = TreeUtils.buildByBeanList(list, TreeUtils.createTreeConfig().setStrict(true).setSortKey("seq"));
        Assertions.assertEquals(3, treeList.size());
    }

    private static List<CategoryDO> categoryDataList() {
        CategoryDO node1 = new CategoryDO();
        node1.setId(1L);
        node1.setParentId(0L);
        node1.setCode("shaanxi");
        node1.setName("陕西省");
        node1.setSeq(1);
        node1.setDisabled("N");

        CategoryDO node1001 = new CategoryDO();
        node1001.setId(1001L);
        node1001.setParentId(1L);
        node1001.setCode("xi'an");
        node1001.setName("西安市");
        node1001.setSeq(1);
        node1001.setDisabled("N");

        CategoryDO node1002 = new CategoryDO();
        node1002.setId(1002L);
        node1002.setParentId(1L);
        node1002.setCode("baoji");
        node1002.setName("宝鸡市");
        node1002.setSeq(2);
        node1002.setDisabled("Y");


        CategoryDO node2 = new CategoryDO();
        node2.setId(2L);
        node2.setParentId(0L);
        node2.setCode("sichuan");
        node2.setName("四川省");
        node2.setSeq(2);
        node2.setDisabled("N");

        CategoryDO node2001 = new CategoryDO();
        node2001.setId(2001L);
        node2001.setParentId(2L);
        node2001.setCode("chengdu");
        node2001.setName("成都市");
        node2001.setSeq(1);
        node2001.setDisabled("N");


        CategoryDO node3 = new CategoryDO();
        node3.setId(3L);
        node3.setParentId(null);
        node3.setCode("chongqing");
        node3.setName("重庆市");
        node3.setSeq(3);
        node3.setDisabled("N");

        List<CategoryDO> list = new ArrayList<>();
        list.add(node1);
        list.add(node1001);
        list.add(node1002);

        list.add(node2);
        list.add(node2001);

        list.add(node3);

        return list;
    }

    @Test
    void hutoolTreeNodeBuild() {
        List<MapTree<Integer>> treeList = TreeUtil.build(dataList(), 0);
        Assertions.assertEquals(2, treeList.size());
    }

    @Test
    void getTypeArgument() {
        List<String> list = new ArrayList<>();
        list.add("aaa");
        Type type = TypeUtil.getTypeArgument(list.getClass().getGenericSuperclass(), 0);
        Assertions.assertEquals("E", type.getTypeName());
    }

    private static List<TreeNode<Integer>> dataList() {
        TreeNode<Integer> node1 = new TreeNode<>(1, 0, "陕西省", 1);
        node1.setExtra(MapUtil.of("code", "shaanxi"));

        TreeNode<Integer> node1001 = new TreeNode<>(1001, 1, "西安市", 1);
        node1001.setExtra(MapUtil.of("code", "xi'an"));

        TreeNode<Integer> node1002 = new TreeNode<>(1002, 1, "宝鸡市", 2);
        node1002.setExtra(MapUtil.of("code", "baoji"));

        TreeNode<Integer> node1003 = new TreeNode<>(1003, 1, "铜川市", 3);
        node1003.setExtra(MapUtil.of("code", "tongchuan"));

        TreeNode<Integer> node2 = new TreeNode<>(2, 0, "四川省", 1);
        node2.setExtra(MapUtil.of("code", "shaanxi"));

        TreeNode<Integer> node2001 = new TreeNode<>(2001, 2, "成都市", 1);
        node2001.setExtra(MapUtil.of("code", "chengdu"));

        TreeNode<Integer> node2002 = new TreeNode<>(2002, 2, "绵阳市", 2);
        node2002.setExtra(MapUtil.of("code", "mianyang"));

        List<TreeNode<Integer>> list = new ArrayList<>();
        list.add(node1);
        list.add(node1001);
        list.add(node1002);
        list.add(node1003);

        list.add(node2);
        list.add(node2001);
        list.add(node2002);

        return list;
    }
}