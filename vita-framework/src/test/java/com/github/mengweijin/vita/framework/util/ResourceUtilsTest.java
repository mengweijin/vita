package com.github.mengweijin.vita.framework.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 *
 * @author mengweijin
 * @since 2026/8/10
 */
class ResourceUtilsTest {

    @Test
    void load() {
        String locationPattern = "generator/**/*.*";
        List<ResourceUtils.ResourceInfo> list = ResourceUtils.load(locationPattern);
        Assertions.assertFalse(list.isEmpty());
    }
}