package com.github.mengweijin.vita.framework.cache;

import lombok.Data;
import org.springframework.boot.convert.DurationStyle;
import org.springframework.util.StringUtils;

/**
 * 缓存规格
 *
 * @author mengweijin
 * @since 2025/12/14
 */
@Data
public class CacheSpecification {

    /**
     * 缓存名称
     */
    private String cacheName;

    /**
     * 过期时间。单位：毫秒。
     * 参考：{@link org.springframework.boot.convert.DurationStyle}
     */
    private long ttl;

    public CacheSpecification(String cacheName, long ttl) {
        this.cacheName = cacheName;
        this.ttl = ttl;
    }


    /**
     * 解析 @Cacheable 的 name 参数为一个 {@link CacheSpecification} 对象。
     *
     * @param originalName @Cacheable 的 name 参数。
     *                     格式：{cacheName}#{ttl}#{cacheLocation}
     *                     - {cacheName}：缓存名称。
     *                     - {ttl}：过期时间。非必须。不指定则默认为 0，即永不过期。{@link DurationStyle}
     *                     例如：
     *                     - users#600s：意思为缓存名称为 users 的缓存，过期时间为 600 秒，默认存放到 local 本地缓存。
     * @return {@link CacheSpecification}
     */
    public static CacheSpecification parse(String originalName) {
        String cacheName = originalName;
        long ttl = 0L;

        String[] array = StringUtils.delimitedListToStringArray(originalName, "#");
        if (array.length > 0) {
            cacheName = array[0];
        }
        if (array.length > 1) {
            ttl = DurationStyle.detectAndParse(array[1]).toMillis();
        }
        return new CacheSpecification(cacheName, ttl);
    }
}
