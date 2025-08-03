package com.github.mengweijin.vita.framework.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 * @since 2025/8/3
 */
@Data
public class ExtData implements Serializable {

    /**
     * 列名称。同 VT_EXT_COLUMN 表中的 COLUMN_NAME
     */
    private String column;

    /**
     * 值
     */
    private String value;
}
