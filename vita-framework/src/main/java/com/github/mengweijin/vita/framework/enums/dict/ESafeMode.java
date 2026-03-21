package com.github.mengweijin.vita.framework.enums.dict;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 二级认证模式
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum ESafeMode implements IEnum<String> {

    /**
     * 密码认证
     */
    PASSWORD("PASSWORD"),

    /**
     * TOTP 口令认证
     */
    TOTP("TOTP");

    private final String value;
}
