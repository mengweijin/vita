package com.github.mengweijin.vita.workflow.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2026/5/23
 */
@Getter
@AllArgsConstructor
public enum EWarmFlowDelFlag {

    ZERO("0", "未删除"),

    ONE("1", "已删除"),
    ;

    private final String code;

    private final String desc;

}
