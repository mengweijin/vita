package com.github.mengweijin.vita;

import cn.hutool.v7.core.data.id.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 */
@Slf4j
class IdGeneratorTest {

    @Test
    void generateId() {
        for (int i = 0; i < 10; i++) {
            long id = IdUtil.getSnowflakeNextId();
            System.out.println(id);
            Assertions.assertTrue(id > 0);
        }
    }
}
