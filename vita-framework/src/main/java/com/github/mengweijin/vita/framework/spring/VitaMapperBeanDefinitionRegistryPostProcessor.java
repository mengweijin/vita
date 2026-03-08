package com.github.mengweijin.vita.framework.spring;

import cn.hutool.v7.core.reflect.ClassUtil;
import cn.hutool.v7.core.text.CharSequenceUtil;
import com.github.mengweijin.vita.framework.VitaConfiguration;
import com.github.mengweijin.vita.framework.constant.Const;
import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author mengweijin
 * @since 2022/7/27
 * @deprecated since 2.0
 */
@Deprecated(since = "2.0", forRemoval = true)
public class VitaMapperBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        String pkg = ClassUtil.getPackage(VitaConfiguration.class);
        String parentPkg = CharSequenceUtil.subBefore(pkg, Const.DOT, true);
        ClassPathMapperScanner mapperScanner = new ClassPathMapperScanner(beanDefinitionRegistry, applicationContext.getEnvironment());
        mapperScanner.registerFilters();
        mapperScanner.scan(parentPkg + ".**.mapper");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
