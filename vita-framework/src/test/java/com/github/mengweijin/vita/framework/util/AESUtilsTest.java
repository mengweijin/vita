package com.github.mengweijin.vita.framework.util;

import org.dromara.hutool.core.text.StrUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 */
class AESUtilsTest {

    @Test
    void encryptByKey() {
        String key = "b8df6136f2588722";
        String value = "Hello";

        byte[] valueBytes = value.getBytes();
        Assertions.assertEquals("72, 101, 108, 108, 111", getBytesString(valueBytes));

        byte[] encrypted = AESUtils.encryptByKey(key, valueBytes);
        Assertions.assertEquals("-35, 73, -2, -107, -125, 127, 59, -107, 94, -102, 124, -36, -105, 73, -38, 109", getBytesString(encrypted));
    }

    @Test
    void generateSecretKey() {
        byte[] secretKey = AESUtils.generateSecretKey("vita", 128);
        String bytesString = getBytesString(secretKey);
        Assertions.assertEquals("-23, 120, -87, 80, 108, 66, -30, 109, 103, -88, 94, -14, -47, 114, -16, -38", bytesString);
    }

    private String getBytesString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(b).append(", ");
        }
        String string = builder.toString().trim();
        return StrUtil.subBefore(string, ",", true);
    }
}