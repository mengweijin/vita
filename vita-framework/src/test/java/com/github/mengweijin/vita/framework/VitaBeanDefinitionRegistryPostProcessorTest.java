package com.github.mengweijin.vita.framework;

import cn.hutool.v7.core.reflect.ClassUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author mengweijin
 */
class VitaBeanDefinitionRegistryPostProcessorTest {

    @Test
    void packagePath() {
        String pkg = ClassUtil.getPackage(VitaConfiguration.class);
        Assertions.assertEquals("com.github.mengweijin.framework", pkg);

        String parentPkg = StrUtil.subBefore(pkg, Const.DOT, true);
        Assertions.assertEquals("com.github.mengweijin", parentPkg);
    }
}