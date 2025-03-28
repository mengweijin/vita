package com.github.mengweijin.vita.framework.util;

import org.springframework.aop.framework.AopContext;

/**
 * @author mengweijin
 */
public final class AopUtils {

    private AopUtils() {}

    /**
     * 获取 aop 代理对象。
     * E.g.: UserService userService = AopUtils.getAopProxy(this);
     *
     * @param invoker invoker object
     * @return proxy object
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T> T getAopProxy(T invoker) {
        return (T) AopContext.currentProxy();
    }

}
