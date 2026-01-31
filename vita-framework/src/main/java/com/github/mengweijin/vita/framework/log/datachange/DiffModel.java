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
public class DiffModel implements Serializable {

    /**
     * 差异类型枚举
     */
    private DiffType diffType;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 旧值
     */
    private String oldValue;

    /**
     * 新值
     */
    private String newValue;

    /**
     *  差异类型枚举
     */
    public enum DiffType {
        // 新增的键
        ADDED,
        // 修改的值
        MODIFIED,
        // 删除的键
        REMOVED
    }
}
