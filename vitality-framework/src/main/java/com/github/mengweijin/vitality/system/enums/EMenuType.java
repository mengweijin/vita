package com.github.mengweijin.vitality.system.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EMenuType implements IEnum<String> {

    MENU("MENU"),

    BTN("BTN"),

    IFRAME("IFRAME"),

    URL("URL");

    private final String value;

}
