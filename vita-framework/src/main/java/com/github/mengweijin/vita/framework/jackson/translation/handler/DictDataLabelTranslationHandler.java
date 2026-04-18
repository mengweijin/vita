package com.github.mengweijin.vita.framework.jackson.translation.handler;

import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import com.github.mengweijin.vita.system.service.DictDataService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 字典翻译
 * @author mengweijin
 * @since 2023/5/20
 */
@Slf4j
@Component
@AllArgsConstructor
public class DictDataLabelTranslationHandler implements ITranslationHandler {

    private DictDataService dictDataService;

    @Override
    public ETranslateType translateType() {
        return ETranslateType.DICT_DATA_TO_LABEL;
    }

    @Override
    public String translation(Object value, Translation translation) {
        try {
            String dictValue = StrUtil.toStringOrNull(value);
            if(StrUtil.isNotBlank(dictValue)) {
                return dictDataService.getLabelByCodeAndVal(translation.dictType(), dictValue);
            }
            return null;
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
