package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.io.IoUtil;
import com.github.mengweijin.vita.framework.exception.ServerException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.xdb.LongByteArray;
import org.lionsoul.ip2region.xdb.Searcher;
import org.lionsoul.ip2region.xdb.Version;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mengweijin
 * @since 2023/5/13
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IpRegionUtils {

    private static final String XDB = "ip2region_v4.xdb";

    private static final String IP_LOCALHOST_V6 = "0:0:0:0:0:0:0:1";

    private static volatile LongByteArray xdbBytesArray;

    public static String search(String ip) {
        if (IP_LOCALHOST_V6.equals(ip.trim())) {
            return "0|0|内网IP|内网IP";
        }
        return search(ip, Version.IPv4);
    }

    /**
     *
     * @param ip ip
     * @return 数据格式：国家|省份|城市|ISP。例如：中国|广东省|广州市|联通
     */
    public static String search(String ip, Version version) {
        if (ip == null) {
            return null;
        }

        Searcher searcher = null;
        try {
            loadData();
            searcher = Searcher.newWithBuffer(version, xdbBytesArray);
            return searcher.search(ip);
        } catch (Exception e) {
            log.error("Search ip to region error! ip={}", ip);
            return null;
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    private static void loadData() {
        if (xdbBytesArray == null) {
            synchronized (XDB) {
                if (xdbBytesArray == null) {
                    InputStream in = IpRegionUtils.class.getClassLoader().getResourceAsStream(XDB);
                    try (in) {
                        xdbBytesArray = new LongByteArray(IoUtil.readBytes(in));
                    } catch (IOException e) {
                        throw new ServerException(e);
                    }
                }
            }
        }
    }

    public static IpRegion searchIpRegion(String ip) {
        String regionString = search(ip);
        if (regionString == null) {
            return null;
        }
        try {
            String[] split = regionString.split("\\|");
            IpRegion region = new IpRegion();
            region.setCountry(split[0]);
            region.setProvince(split[1]);
            region.setCity(split[2]);
            region.setIsp(split[3]);
            return region;
        } catch (RuntimeException e) {
            log.error("Split ip region string error! regionString={}", regionString);
        }

        return null;
    }

    @Data
    public static class IpRegion {
        /**
         * 国家
         */
        private String country;
        /**
         * 省
         */
        private String province;
        /**
         * 市
         */
        private String city;
        /**
         * 运营商
         */
        private String isp;
    }

}
