package com.github.mengweijin.vita.framework.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author mengweijin
 * @since 2025/10/5
 */
class IpRegionUtilsTest {

    @Test
    void search() {
        String region = IpRegionUtils.search("127.0.0.1");
        assertEquals("0|0|内网IP|内网IP", region);

        region = IpRegionUtils.search("192.168.0.1");
        assertEquals("0|0|内网IP|内网IP", region);

        region = IpRegionUtils.search("121.28.254.34");
        assertEquals("中国|河北省|石家庄市|联通", region);
    }
}