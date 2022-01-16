package com.github.mengweijin.quickboot.framework.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 * @date 2021/12/24
 */
@Slf4j
class AESUtilsTest {

    @Test
    void encrypt() {
        String password = "123456789";
        final String encrypt = AESUtils.encrypt(password);
        log.info("encrypt = " + encrypt);

        final String decrypt = AESUtils.decrypt(encrypt);
        log.info("decrypt = " + decrypt);

        Assertions.assertEquals(password, decrypt);
    }

    @Test
    void generateRandomKey() {
        String randomKey = AESUtils.generateRandomKey();
        log.info(randomKey);
        Assertions.assertEquals(16, randomKey.length());
    }

    @Test
    void encryptByKey() {
        String password = "123456789";
        log.info("password = " + password);

        String randomKey = AESUtils.generateRandomKey();
        log.info("randomKey = " + randomKey);

        String encrypt = AESUtils.encryptByKey(randomKey, password);
        log.info("encrypt = " + encrypt);

        String decrypt = AESUtils.decryptByKey(randomKey, encrypt);
        log.info("decrypt = " + decrypt);

        Assertions.assertEquals(password, decrypt);
    }
}