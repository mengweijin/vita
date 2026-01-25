package com.github.mengweijin.vita.framework.log.datachange;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 * @author mengweijin
 * @since 2026/1/18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiffModel<K, V> implements Serializable {

    private DiffType diffType;

    private K fieldName;

    private V oldValue;

    private V newValue;

    /**
     *  差异类型枚举
     */
    public enum DiffType {
        // 新增的键
        ADDED,
        // 删除的键
        REMOVED,
        // 修改的值
        MODIFIED
    }
}
