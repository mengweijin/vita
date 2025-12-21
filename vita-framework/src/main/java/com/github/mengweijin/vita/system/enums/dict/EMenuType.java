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
public enum EMenuType implements IEnum<String> {

    /**
     * 目录
     */
    DIR("DIR"),

    /**
     * 菜单
     */
    MENU("MENU"),

    /**
     * 按钮
     */
    BTN("BTN"),

    /**
     * URL
     */
    URL("URL");

    private final String value;

}
