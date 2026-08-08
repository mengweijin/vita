package com.github.mengweijin.vita.framework.domain;

import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;

/**
 * <p>
 * 表 VT_FIELD_META 关联的扩展数据存储结构。
 * key: FIELD_KEY
 * value: 属性字段的值
 * </p>
 * 实体类：
 * <p>
 * 实体类必须开启 autoResultMap：@TableName(value = "your_table", autoResultMap = true)
 * 实体类需指定 typeHandler。
 * 示例：
 * {@code
 *
 * @author mengweijin
 *
 * </p>
 * @TableField(value = "ext", typeHandler = JsonTypeHandler.class)
 * private ExtFieldMap ext;          // 使用自定义 ExtFieldMap 类，和 Map 一样的。
 * @TableField(value = "ext", typeHandler = JsonTypeHandler.class)
 * private Map<String, Object> ext;  // 也可以使用 Object，但 Map 更常用
 * }
 * @since 2025/8/3
 */
@NoArgsConstructor
public class ExtFieldMap extends LinkedHashMap<String, Object> {

    public ExtFieldMap(ExtFieldMap map) {
        if (map != null) {
            this.putAll(map);
        }
    }
}
