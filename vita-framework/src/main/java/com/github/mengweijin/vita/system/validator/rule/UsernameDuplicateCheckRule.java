package com.github.mengweijin.vita.system.validator.rule;

import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.validator.CheckValidator;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.service.UserService;

/**
 * @author mengweijin
 */
public class UsernameDuplicateCheckRule implements CheckValidator.CheckRule {
    @Override
    public boolean isValid(CharSequence value) {
        UserService userService = SpringUtil.getBean(UserService.class);
        UserDO user = userService.getByUsername((String) value);
        return user == null;
    }

    @Override
    public String message(CharSequence value) {
        return CharSequenceUtil.format("The username[{}] already exists!", value);
    }

}
