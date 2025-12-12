package com.github.mengweijin.vita;

import cn.hutool.v7.json.jwt.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

/**
 *
 * @author mengweijin
 * @since 2025/11/9
 */
@Slf4j
class JwtTest {

    @Test
    void generateJwt() {
        String key = "AfpqdFEUpOzBQkwFWBW8lWiC9D33wRXycKBKOAh6wvM=";
        String token = JWTUtil.createToken(null, key.getBytes(StandardCharsets.UTF_8));
        log.info(token);

        boolean verify = JWTUtil.verify(token, key.getBytes(StandardCharsets.UTF_8));
        Assertions.assertTrue(verify);
    }
}
