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
    void searchV4() {
        String region = IpRegionUtils.search("127.0.0.1");
        assertEquals("0|0|内网IP|0", region);

        region = IpRegionUtils.search("192.168.0.1");
        assertEquals("0|0|内网IP|0", region);

        region = IpRegionUtils.search("121.28.254.34");
        assertEquals("中国|河北省|石家庄市|联通", region);
    }

    @Test
    void searchV6() {
        String region = IpRegionUtils.search("0:0:0:0:0:0:0:1");
        assertEquals("", region);

        region = IpRegionUtils.search("240e:3b7:3272:d8d0:db09:c067:8d59:539e");
        assertEquals("中国|广东省|深圳市|电信", region);
    }
}