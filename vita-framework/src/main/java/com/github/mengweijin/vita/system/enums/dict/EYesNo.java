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
public enum EYesNo implements IEnum<String> {

    /**
     * 是
     */
    Y("Y"),

    /**
     * 否
     */
    N("N");

    private final String value;
}
