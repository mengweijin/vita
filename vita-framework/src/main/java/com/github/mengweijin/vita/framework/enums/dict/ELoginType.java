package com.github.mengweijin.vita.framework.enums.dict;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum ELoginType implements IEnum<String> {

    /**
     * 登入
     */
    LOGIN("LOGIN"),

    /**
     * 注销
     */
    LOGOUT("LOGOUT"),

    /**
     * 被踢下线
     */
    KICK_OUT("KICK_OUT"),

    /**
     * 被顶下线
     */
    REPLACED("REPLACED");

    private final String value;

}
