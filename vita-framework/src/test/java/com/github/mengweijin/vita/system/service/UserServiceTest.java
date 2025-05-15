package com.github.mengweijin.vita.system.service;

import com.github.mengweijin.vita.framework.constant.Const;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.PasswdStrength;
import org.dromara.hutool.crypto.digest.BCrypt;
import org.dromara.hutool.crypto.digest.DigestUtil;
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
        String salt = "$2a$10$v/3HEiNTDK8ww2yAMLa3y.";

        String passwordLevel = PasswdStrength.getLevel(password).name();
        String hashedPwd = this.hashPassword(password, salt);

        log.info(passwordLevel);
        log.info(hashedPwd);
        log.info(String.valueOf(hashedPwd.length()));

        Assertions.assertEquals("EASY", passwordLevel);

        boolean checked = this.checkPassword(password, hashedPwd, salt);
        Assertions.assertTrue(checked);
    }

    @Test
    void generateSalt() {
        String salt = BCrypt.gensalt();
        log.info(salt);
        Assertions.assertNotNull(salt);
    }

    private String saltedPassword(String password, String salt) {
        return String.join(Const.COMMA, password, salt);
    }

    private String hashPassword(String password, String salt) {
        return DigestUtil.bcrypt(this.saltedPassword(password, salt));
    }

    private boolean checkPassword(String checkingPwd, String dbPwd, String salt) {
        String saltedPassword = this.saltedPassword(checkingPwd, salt);
        return DigestUtil.bcryptCheck(saltedPassword, dbPwd);
    }

}