package com.github.mengweijin.vita.framework.jackson.translation.handler;

import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;

import java.io.Serializable;

/**
 * 数据翻译转换接口
 *
 * @author mengweijin
 * @since 2023/5/20
 */
public interface ITranslationHandler {

    /**
     * 支持的翻译类型
     * @return ETranslateType
     */
    ETranslateType translateType();

    /**
     * 数据翻译
     *
     * @param value 需要被翻译的值
     * @param translation 注解 {@link Translation}
     * @return 返回转换后的值
     */
    Serializable translation(Object value, Translation translation);

}
