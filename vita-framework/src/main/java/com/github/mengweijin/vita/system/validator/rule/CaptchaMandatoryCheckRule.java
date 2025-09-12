package com.github.mengweijin.vita.system.validator.rule;

import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.core.text.StrValidator;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.properties.VitaProperties;
import com.github.mengweijin.vita.framework.validator.BusinessCheckValidator;

/**
 * @author mengweijin
 */
public class CaptchaMandatoryCheckRule implements BusinessCheckValidator.CheckRule {
    @Override
    public boolean isValid(CharSequence value) {
        VitaProperties vitaProperties = SpringUtil.getBean(VitaProperties.class);
        if(vitaProperties.getLoginCaptchaEnabled()) {
            return StrValidator.isNotBlank(value);
        }
        return true;
    }

    @Override
    public String message(CharSequence value) {
        return CharSequenceUtil.format("The captcha code must not be empty!");
    }

}
