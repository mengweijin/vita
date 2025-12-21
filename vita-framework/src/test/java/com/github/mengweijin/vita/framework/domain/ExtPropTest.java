package com.github.mengweijin.vita.framework.domain;

import com.github.mengweijin.vita.framework.jackson.wrapper.SensitiveObjectMapperWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

/**
 *
 * @author mengweijin
 * @since 2025/9/6
 */
@Slf4j
class ExtPropTest {

    @Test
    @SneakyThrows
    void test() {
        ExtProp ext = new ExtProp();
        ext.put("username", "admin");
        ext.put("nickname", "管理员");
        ext.put("startTime", LocalDateTime.now());

        String str = P.getSensitiveObjectMapperWrapper().writeValueAsString(ext);

        log.info(str);
        Assertions.assertNotNull(str);
    }

}