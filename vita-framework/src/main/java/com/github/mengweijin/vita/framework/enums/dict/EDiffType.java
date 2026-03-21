package com.github.mengweijin.vita.framework.enums.dict;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 差异类型枚举。字典：vt_diff_type
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EDiffType implements IEnum<String> {

    /**
     * 新增
     */
    ADDED("ADDED"),

    /**
     * 修改
     */
    MODIFIED("MODIFIED"),

    /**
     * 删除
     */
    REMOVED("REMOVED");

    private final String value;

}
