package com.github.mengweijin.vita.framework.jackson.translation.handler;

import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import com.github.mengweijin.vita.system.service.DictTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 字典翻译
 *
 * @author mengweijin
 * @since 2023/5/20
 */
@Slf4j
@Component
@AllArgsConstructor
public class DictTypeCodeTranslationHandler implements ITranslationHandler {

    private DictTypeService dictTypeService;

    @Override
    public ETranslateType translateType() {
        return ETranslateType.DICT_TYPE_ID_TO_CODE;
    }

    @Override
    public String translation(Object value, Translation translation) {
        if (value instanceof Long id) {
            return dictTypeService.queryCodeById(id);
        }
        return null;
    }

}
