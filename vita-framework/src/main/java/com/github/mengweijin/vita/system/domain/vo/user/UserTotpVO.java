package com.github.mengweijin.vita.system.domain.vo.user;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * User TOTP VO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
public class UserTotpVO implements Serializable {

    private String key;

    private String qrCode;
}
