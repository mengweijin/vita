package com.github.mengweijin.vita.framework.jackson.translation.handler;

import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import com.github.mengweijin.vita.system.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 分类名称翻译
 *
 * @author mengweijin
 * @since 2023/5/20
 */
@Slf4j
@Component
@AllArgsConstructor
public class CategoryNameTranslationHandler implements ITranslationHandler {

    private CategoryService categoryService;

    @Override
    public ETranslateType translateType() {
        return ETranslateType.CATEGORY_ID_TO_NAME;
    }

    @Override
    public String translation(Object value, Translation translation) {
        try {
            long id = NumberUtil.parseLong(StrUtil.toStringOrNull(value));
            return categoryService.getNameById(id);
        } catch (NumberFormatException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
