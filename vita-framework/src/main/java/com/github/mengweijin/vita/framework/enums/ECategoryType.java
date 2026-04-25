package com.github.mengweijin.vita.framework.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum ECategoryType implements IEnum<String> {

    /**
     * 流程分类
     */
    VT_WORKFLOW("vt_workflow");

    private final String value;

}
