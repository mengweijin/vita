package com.github.mengweijin.vita.system.validator.rule;

import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.validator.BusinessCheckValidator;
import com.github.mengweijin.vita.system.domain.entity.ConfigDO;
import com.github.mengweijin.vita.system.service.ConfigService;

/**
 * @author mengweijin
 */
public class ConfigKeyDuplicateCheckRule implements BusinessCheckValidator.CheckRule {
    @Override
    public boolean isValid(CharSequence value) {
        ConfigService configService = SpringUtil.getBean(ConfigService.class);
        ConfigDO config = configService.getByConfigKey((String) value);
        return config == null;
    }

    @Override
    public String message(CharSequence value) {
        return CharSequenceUtil.format("The config key [{}] already exists!", value);
    }

}
