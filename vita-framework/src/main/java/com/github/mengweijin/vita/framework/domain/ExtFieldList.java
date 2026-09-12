package com.github.mengweijin.vita.framework.domain;

import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * JSONArray 对应的扩展字段存储。
 * 实体类：
 * <p>
 * 实体类必须开启 autoResultMap：@TableName(value = "your_table", autoResultMap = true)
 * 实体类需指定 typeHandler。
 * 示例：
 * {@code
 * </p>
 *
 * @author mengweijin
 * @TableField(value = "ext", typeHandler = JsonTypeHandler.class)
 * private ExtFieldList ext;
 * }
 * @since 2026/9/12
 */
@NoArgsConstructor
public class ExtFieldList extends ArrayList<Object> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public ExtFieldList(ExtFieldList list) {
        if (list != null) {
            this.addAll(list);
        }
    }
}
