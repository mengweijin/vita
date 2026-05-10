package com.github.mengweijin.vita.system.validator.rule;

import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.validator.CheckValidator;
import com.github.mengweijin.vita.system.domain.entity.DictTypeDO;
import com.github.mengweijin.vita.system.service.DictTypeService;

/**
 * @author mengweijin
 */
public class DictTypeCodeDuplicateCheckRule implements CheckValidator.CheckRule {
    @Override
    public boolean isValid(CharSequence value) {
        DictTypeService dictTypeService = SpringUtil.getBean(DictTypeService.class);
        DictTypeDO dictType = dictTypeService.queryByCode((String) value);
        return dictType == null;
    }

    @Override
    public String message(CharSequence value) {
        return CharSequenceUtil.format("The dict type code[{}] already exists!", value);
    }

}
