package com.github.mengweijin.vita.framework.enums.dict;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * vt_form_type
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EFormType implements IEnum<String> {

    /**
     * 静态表单
     */
    STATIC("static"),

    /**
     * 动态表单
     */
    DYNAMIC("dynamic");

    private final String value;
}
