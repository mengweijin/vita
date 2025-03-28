package com.github.mengweijin.vita.framework;

import com.github.mengweijin.vita.framework.constant.Const;
import org.dromara.hutool.core.reflect.ClassUtil;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.lang.NonNull;

/**
 * @author mengweijin
 * @since 2022/7/27
 */
public class VitaBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(@NonNull BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String pkg = ClassUtil.getPackage(VitaBeanDefinitionRegistryPostProcessor.class);
        String parentPkg = CharSequenceUtil.subBefore(pkg, Const.DOT, true);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        scanner.scan(parentPkg);
    }
}
