package com.github.mengweijin.vita.system.validator.rule;

import com.github.mengweijin.vita.framework.validator.BusinessCheckValidator;
import com.github.mengweijin.vita.system.domain.entity.Category;
import com.github.mengweijin.vita.system.service.CategoryService;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.extra.spring.SpringUtil;

/**
 * @author mengweijin
 */
public class CategoryCodeDuplicateCheckRule implements BusinessCheckValidator.CheckRule {
    @Override
    public boolean isValid(CharSequence value) {
        CategoryService categoryService = SpringUtil.getBean(CategoryService.class);
        Category category = categoryService.getByCode((String) value);
        return category == null;
    }

    @Override
    public String message(CharSequence value) {
        return StrUtil.format("The category code[{}] already exists!", value);
    }

}
