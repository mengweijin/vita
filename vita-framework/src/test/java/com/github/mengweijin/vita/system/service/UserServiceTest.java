package com.github.mengweijin.vita.system.service;

import cn.dev33.satoken.secure.SaSecureUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.PasswdStrength;
import org.dromara.hutool.core.data.id.IdUtil;
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
        String username = "guest";
        String password = "aday.fun";
        String salt = "89D92F225B2218F589D92F225B2218F5";

        String passwordLevel = PasswdStrength.getLevel(password).name();
        String hashedPwd = this.hashPassword(username, password, salt);

        log.info(passwordLevel);
        log.info(hashedPwd);
        log.info(String.valueOf(hashedPwd.length()));

        Assertions.assertEquals("EASY", passwordLevel);
        Assertions.assertEquals(64, hashedPwd.length());
    }

    @Test
    void generateSalt() {
        String salt = IdUtil.simpleUUID().toUpperCase();
        log.info(salt);
        Assertions.assertEquals(32, salt.length());
    }

    private String hashPassword(String username, String password, String salt) {
        return SaSecureUtil.sha256(String.join(username, password, salt));
    }

}