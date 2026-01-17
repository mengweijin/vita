package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.io.IoUtil;
import com.github.mengweijin.vita.framework.exception.ServerException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lionsoul.ip2region.service.Config;
import org.lionsoul.ip2region.service.InvalidConfigException;
import org.lionsoul.ip2region.service.Ip2Region;
import org.lionsoul.ip2region.xdb.InetAddressException;
import org.lionsoul.ip2region.xdb.XdbException;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author mengweijin
 * @since 2023/5/13
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IpRegionUtils {

    private static final String XDB4 = "ip2region_v4.xdb";

    private static final String XDB6 = "ip2region_v6.xdb";

    private static volatile Ip2Region ip2Region;

    /**
     * ip 地址查询
     * @param ip ip
     * @return 国家|省|市|运营商。返回示例：中国|河北省|石家庄市|联通
     */
    public static String search(String ip) {
        try {
            return initIp2Region().search(ip);
        } catch (InetAddressException | IOException e) {
            throw new ServerException(e);
        } catch ( InterruptedException e) {
            /* Clean up whatever needs to be handled before interrupting  */
            Thread.currentThread().interrupt();
            throw new ServerException(e);
        }
    }

    public static RegionInfo searchRegionInfo(String ip) {
        String regionString = search(ip);
        if (regionString == null) {
            return null;
        }
        try {
            String[] split = regionString.split("\\|");
            RegionInfo region = new RegionInfo();
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


    private static Ip2Region initIp2Region() {
        if(ip2Region == null) {
            synchronized (IpRegionUtils.class) {
                if(ip2Region == null) {
                    ip2Region = build();
                }
            }
        }
        return ip2Region;
    }

    /**
     * 备注：Xdb 三种初始化输入的优先级：XdbInputStream -> XdbFile -> XdbPath
     * setXdbInputStream 仅方便使用者从 jar 包中加载 xdb 文件内容，这时 cachePolicy 只能设置为 Config.BufferCache
     * @return {@link Ip2Region}
     */
    private static Ip2Region build() {
        InputStream xdb4InputStream = null;
        InputStream xdb6InputStream = null;
        try {
            xdb4InputStream = IpRegionUtils.class.getClassLoader().getResourceAsStream(XDB4);
            final Config v4Config = Config.custom()
                    // 指定缓存策略:  NoCache / VIndexCache / BufferCache
                    .setCachePolicy(Config.BufferCache)
                    // 设置初始化的查询器数量
                    .setSearchers(15)
                    // 设置 v4 xdb 文件的 inputStream 对象
                    .setXdbInputStream(xdb4InputStream)
                    // 指定为 v4 配置
                    .asV4();

            xdb6InputStream = IpRegionUtils.class.getClassLoader().getResourceAsStream(XDB6);
            Config v6Config = Config.custom()
                    .setCachePolicy(Config.BufferCache)
                    .setSearchers(15)
                    .setXdbInputStream(xdb6InputStream)
                    .asV6();

            return Ip2Region.create(v4Config, v6Config);
        } catch (IOException | XdbException | InvalidConfigException e) {
            throw new ServerException(e);
        } finally {
            IoUtil.closeQuietly(xdb4InputStream, xdb6InputStream);
        }
    }

    @Data
    public static class RegionInfo {
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
