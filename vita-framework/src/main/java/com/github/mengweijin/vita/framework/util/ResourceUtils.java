package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.io.resource.MultiResource;
import cn.hutool.v7.core.io.resource.Resource;
import cn.hutool.v7.core.io.resource.ResourceFinder;
import com.github.mengweijin.vita.framework.exception.ServerException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Meng Wei Jin
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResourceUtils {

    /**
     * 加载 resources 下指定 locationPattern 所有的文件。
     *
     * @param locationPattern 扫描路径模式。示例：{@link com.github.mengweijin.vita.generator.service.TemplateService#LOCATION_PATTERN}
     * @return List<ContentInfo>
     */
    public static List<ResourceInfo> load(String locationPattern) {
        List<ResourceInfo> list = new ArrayList<>();
        MultiResource resources = ResourceFinder.of().find(locationPattern);
        try {
            for (Resource resource : resources) {
                ResourceInfo resourceInfo = new ResourceInfo();
                resourceInfo.setUrl(resource.getUrl().toURI().getPath());
                resourceInfo.setName(resource.getName());
                resourceInfo.setContent(resource.readUtf8Str());
                list.add(resourceInfo);
            }
        } catch (URISyntaxException e) {
            throw new ServerException(e);
        }
        return list;
    }

    @Data
    public static class ResourceInfo {

        private String url;

        private String name;

        private String content;

    }
}
