package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.data.PasswdStrength;
import cn.hutool.v7.crypto.digest.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 * @since 2025/3/29
 */
@Slf4j
class UserServiceTest {

    @Test
    void hashPasswordTest() {
        String password = "aday.fun";

        String passwordLevel = PasswdStrength.getLevel(password).name();
        String hashedPwd = this.hashPassword(password);

        log.info(passwordLevel);
        log.info(hashedPwd);
        log.info(String.valueOf(hashedPwd.length()));

        Assertions.assertEquals("EASY", passwordLevel);

        boolean checked = this.checkPassword(password, hashedPwd);
        Assertions.assertTrue(checked);
    }

    private String hashPassword(String password) {
        return DigestUtil.bcrypt(password);
    }

    private boolean checkPassword(String checkingPwd, String dbPwd) {
        return DigestUtil.bcryptCheck(checkingPwd, dbPwd);
    }

}