package com.github.mengweijin.vita.system.enums.dict;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum ESchedulingTaskStatus implements IEnum<String> {

    /**
     * 未开始
     */
    NOT_STARTED("not_started"),

    /**
     * 执行中
     */
    RUNNING("running"),

    /**
     * 已完成
     */
    FINISHED("finished");

    private final String value;
}
