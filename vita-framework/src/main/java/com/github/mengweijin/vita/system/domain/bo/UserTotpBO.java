package com.github.mengweijin.vita.system.domain.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * User TOTP BO
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
public class UserTotpBO implements Serializable {

    private String key;

    private Integer code;
}
