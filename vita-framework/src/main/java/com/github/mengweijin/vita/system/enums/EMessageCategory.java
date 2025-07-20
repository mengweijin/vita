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
public enum EMessageCategory implements IEnum<String> {

    /**
     * 来自系统发送的消息
     */
    SYSTEM("system"),

    /**
     * 安全
     */
    SECURITY("security"),

    /**
     * 告警消息
     */
    ALERT("alert"),

    /**
     * 来自用户的消息
     */
    USER("user"),

    /**
     * 其它
     */
    OTHER("other");

    private final String value;

}
