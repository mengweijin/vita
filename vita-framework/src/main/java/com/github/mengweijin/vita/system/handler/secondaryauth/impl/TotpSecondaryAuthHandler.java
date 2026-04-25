package com.github.mengweijin.vita.system.handler.secondaryauth.impl;

import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.enums.dict.ESafeMode;
import com.github.mengweijin.vita.system.domain.bo.OpenSafeBO;
import com.github.mengweijin.vita.system.domain.vo.user.UserSessionVO;
import com.github.mengweijin.vita.system.handler.secondaryauth.ISecondaryAuthHandler;
import com.github.mengweijin.vita.system.service.UserService;
import org.springframework.stereotype.Component;

/**
 *
 * @author mengweijin
 * @since 2026/3/14
 */
@Component
public class TotpSecondaryAuthHandler implements ISecondaryAuthHandler {

    @Override
    public ESafeMode supported() {
        return ESafeMode.TOTP;
    }

    @Override
    public boolean validate(UserSessionVO sessionUser, OpenSafeBO bo) {
        UserService userService = SpringUtil.getBean(UserService.class);
        Integer code = NumberUtil.parseInt(bo.getValue());
        return userService.validateTotp(code);
    }
}
