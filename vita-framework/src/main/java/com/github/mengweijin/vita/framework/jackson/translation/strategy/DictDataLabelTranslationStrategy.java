package com.github.mengweijin.vita.framework.jackson.translation.strategy;

import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import com.github.mengweijin.vita.system.service.DictDataService;
import lombok.AllArgsConstructor;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.stereotype.Component;

/**
 * 用户名翻译
 * @author mengweijin
 * @since 2023/5/20
 */
@Component
@AllArgsConstructor
public class DictDataLabelTranslationStrategy implements ITranslationStrategy {

    private DictDataService dictDataService;

    @Override
    public ETranslateType translateType() {
        return ETranslateType.DICT_DATA_TO_LABEL;
    }

    @Override
    public String translation(Object value, Translation translation) {
        if (value instanceof String dictVal && StrValidator.isNotBlank(dictVal)) {
            return dictDataService.getLabelByCodeAndVal(translation.dictType(), dictVal);
        }
        return null;
    }

}
