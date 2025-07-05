package com.github.mengweijin.vita.system.enums;

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

    NOT_STARTED("not_started"),

    RUNNING("running"),

    FINISHED("finished");

    private final String value;
}
