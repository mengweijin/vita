package com.github.mengweijin.vita.framework.mybatis;

import cn.hutool.v7.core.net.NetUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.incrementer.DefaultIdentifierGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 * @since 2024/12/3
 */
class MybatisPlusConfigTest {

    @Test
    void idGenerator() {
        Long id = new DefaultIdentifierGenerator(NetUtil.getLocalhostV4()).nextId(null);
        String strId = String.valueOf(id);
        Assertions.assertEquals(19, strId.length());

        String subId = StrUtil.subPre(strId, strId.length() - 2);
        Assertions.assertEquals(17, subId.length());
    }
}