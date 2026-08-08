package com.github.mengweijin.vita.framework.domain;

import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.util.ObjectMapperUtils;
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
class ExtFieldMapTest {

    @Test
    @SneakyThrows
    void test() {
        ExtFieldMap ext = new ExtFieldMap();
        ext.put("username", "admin");
        ext.put("nickname", "管理员");
        ext.put("startTime", LocalDateTime.now(Const.ZONE));

        String str = ObjectMapperUtils.getSensitiveObjectMapperWrapper().writeValueAsString(ext);

        log.info(str);
        Assertions.assertNotNull(str);
    }

}