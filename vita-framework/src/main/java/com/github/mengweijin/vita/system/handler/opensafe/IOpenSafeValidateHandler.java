package com.github.mengweijin.vita.system.handler.opensafe;

import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.system.domain.bo.OpenSafeBO;
import com.github.mengweijin.vita.system.domain.vo.user.UserSessionVO;
import com.github.mengweijin.vita.system.enums.dict.ESafeMode;

/**
 * 二级认证验证处理器
 * @author mengweijin
 * @since 2026/3/14
 */
public interface IOpenSafeValidateHandler {

    /**
     * 支持的二级认证方式
     * @return ESafeMode
     */
    ESafeMode supported();

    /**
     * 二级认证接口抽象方法
     * @param sessionUser session user
     * @param bo OpenSafeBO
     * @throws ClientException when validation fails
     */
    void validate(UserSessionVO sessionUser, OpenSafeBO bo) throws ClientException;
}
