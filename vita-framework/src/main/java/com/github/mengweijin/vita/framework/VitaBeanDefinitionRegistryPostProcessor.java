package com.github.mengweijin.vita.framework;

import cn.hutool.v7.core.reflect.ClassUtil;
import cn.hutool.v7.core.text.CharSequenceUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

/**
 * @author mengweijin
 * @since 2022/7/27
 */
public class VitaBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String pkg = ClassUtil.getPackage(VitaAutoConfiguration.class);
        String parentPkg = CharSequenceUtil.subBefore(pkg, Const.DOT, true);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        scanner.scan(parentPkg);
    }
}
