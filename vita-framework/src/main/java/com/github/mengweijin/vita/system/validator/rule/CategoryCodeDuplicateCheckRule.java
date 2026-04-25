package com.github.mengweijin.vita.system.validator.rule;

import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.validator.CheckValidator;
import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import com.github.mengweijin.vita.system.service.CategoryService;

/**
 * @author mengweijin
 */
public class CategoryCodeDuplicateCheckRule implements CheckValidator.CheckRule {
    @Override
    public boolean isValid(CharSequence value) {
        CategoryService categoryService = SpringUtil.getBean(CategoryService.class);
        CategoryDO categoryDO = categoryService.getByCode((String) value);
        return categoryDO == null;
    }

    @Override
    public String message(CharSequence value) {
        return CharSequenceUtil.format("The category code[{}] already exists!", value);
    }

}
