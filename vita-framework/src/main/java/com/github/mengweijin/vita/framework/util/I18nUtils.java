package com.github.mengweijin.vita.framework.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hutool.extra.spring.SpringUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * @author mengweijin
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class I18nUtils {

    /**
     * 根据消息键和参数 获取消息 委托给 spring messageSource
     * 如果后台没有设置默认语言，url参数可以添加 lang=zh_CN或lang=en_US这样的参数来实现国际化，也可以不添加。
     * 当不添加lang=en_US参数时，如果测试时语言没变化，一般是由于浏览器缓存，Shift+F5刷新一下即可。
     * <p>
     * messages文件命名规则：
     * messages.properties
     * messages_zh_CN.properties
     * messages_en_US.properties
     * <p>
     * 建议开启以下配置：
     * spring:
     *   # MessageSourceProperties.java
     *   messages:
     *     # 指向 classpath 目录下的 /i18n/messages.properties
     *     basename: i18n/messages
     *     # 消息未找到时，是否返回原始键。默认为 false，这里改为 true 以避免抛出 NoSuchMessageException 异常
     *     use-code-as-default-message: true
     *
     * @param key  message key in messages.properties
     * @param args args
     * @return value
     */
    public static String msg(String key, Object... args) {
        MessageSource messageSource = SpringUtil.getBean(MessageSource.class);
        return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
    }

}
