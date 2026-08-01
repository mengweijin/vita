package com.github.mengweijin.vita.workflow.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EWarmFlowNodeCode implements IEnum<String> {

    /**
     * 开始
     */
    START("start"),
    /**
     * 发起
     */
    LAUNCH("launch"),
    /**
     * 结束
     */
    END("end"),

    ;

    private final String value;

}
