package com.github.mengweijin.vita.system.handler.opensafe.impl;

import cn.hutool.v7.extra.spring.SpringUtil;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.handler.opensafe.IOpenSafeValidateHandler;
import com.github.mengweijin.vita.system.domain.bo.OpenSafeBO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
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
public class PasswordOpenSafeValidateHandler implements IOpenSafeValidateHandler {

    @Override
    public ESafeMode supported() {
        return ESafeMode.PASSWORD;
    }

    @Override
    public void validate(UserSessionVO sessionUser, OpenSafeBO bo) throws ClientException {
        UserService userService = SpringUtil.getBean(UserService.class);
        UserDO user = userService.getById(sessionUser.getUserId());
        boolean checked = userService.checkPassword(bo.getValue(), user.getPassword(), user.getSalt());
        if (!checked) {
            throw new ClientException("二级认证失败，密码错误");
        }
    }
}
