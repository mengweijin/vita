package com.github.mengweijin.vita.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author mengweijin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TotpVO implements Serializable {

    /**
     * 密钥
     */
    private String key;

    /**
     * 二维码 Base64 编码字符串
     */
    private String qrcode;

}
