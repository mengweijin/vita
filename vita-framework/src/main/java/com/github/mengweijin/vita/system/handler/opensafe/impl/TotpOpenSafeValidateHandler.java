package com.github.mengweijin.vita.system.handler.opensafe.impl;

import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.handler.opensafe.IOpenSafeValidateHandler;
import com.github.mengweijin.vita.system.domain.bo.OpenSafeBO;
import com.github.mengweijin.vita.system.domain.vo.user.UserSessionVO;
import com.github.mengweijin.vita.system.enums.dict.ESafeMode;
import com.github.mengweijin.vita.system.service.UserService;
import org.springframework.stereotype.Component;

/**
 *
 * @author mengweijin
 * @since 2026/3/14
 */
@Component
public class TotpOpenSafeValidateHandler implements IOpenSafeValidateHandler {

    @Override
    public ESafeMode supported() {
        return ESafeMode.TOTP;
    }

    @Override
    public void validate(UserSessionVO sessionUser, OpenSafeBO bo) throws ClientException {
        UserService userService = SpringUtil.getBean(UserService.class);
        Integer code = NumberUtil.parseInt(bo.getValue());
        boolean checked = userService.validateTotp(code);
        if (!checked) {
            throw new ClientException("二级认证失败，口令错误");
        }
    }
}
