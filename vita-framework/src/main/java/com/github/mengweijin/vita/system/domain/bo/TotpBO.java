package com.github.mengweijin.vita.system.domain.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@Data
public class TotpBO implements Serializable {

    /**
     * 密钥
     */
    @NotBlank
    private String key;

    /**
     *  TOTP 验证码
     */
    @NotNull
    private Integer code;

}
