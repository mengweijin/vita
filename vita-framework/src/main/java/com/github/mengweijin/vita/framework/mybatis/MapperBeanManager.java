package com.github.mengweijin.vita.framework.mybatis;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mengweijin
 * @since 2026/1/3
 */
@Component
public class MapperBeanManager implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 缓存实体类与 Mapper 的对应关系
     */
    private final Map<Class<?>, BaseMapper<?>> mapperCache = new ConcurrentHashMap<>();

    private final Map<Class<?>, Class<?>> mapperClassCache = new ConcurrentHashMap<>();

    /**
     * 根据实体类获取对应的 Mapper 实例
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T> BaseMapper<T> getMapper(Class<T> entityClass) {
        BaseMapper<?> baseMapper = mapperCache.computeIfAbsent(entityClass, clazz -> {
            Map<String, BaseMapper> beansOfType = applicationContext.getBeansOfType(BaseMapper.class);
            for (BaseMapper mapper : beansOfType.values()) {
                Class<?> mapperInterface = getMapperInterface(mapper);
                if (mapperInterface != null) {
                    Class<?> entityType = getEntityTypeFromMapper(mapperInterface);
                    if (entityClass.equals(entityType)) {
                        return mapper;
                    }
                }
            }
            return null;
        });
        return (BaseMapper<T>) baseMapper;
    }

    /**
     * 根据实体类获取 Mapper 类
     */
    public Class<?> getMapperClass(Class<?> entityClass) {
        return mapperClassCache.computeIfAbsent(entityClass, clazz -> {
            BaseMapper<?> mapper = getMapper(entityClass);
            return getMapperInterface(mapper);
        });
    }

    /**
     * 获取 Mapper 接口的 Class
     */
    private Class<?> getMapperInterface(BaseMapper<?> mapper) {
        Class<?>[] interfaces = mapper.getClass().getInterfaces();
        for (Class<?> interfaceClass : interfaces) {
            if (BaseMapper.class.isAssignableFrom(interfaceClass)) {
                return interfaceClass;
            }
        }
        return null;
    }

    /**
     * 从 Mapper 接口获取实体类型
     */
    private Class<?> getEntityTypeFromMapper(Class<?> mapperInterface) {
        Type[] genericInterfaces = mapperInterface.getGenericInterfaces();
        for (Type genericInterface : genericInterfaces) {
            if (genericInterface instanceof ParameterizedType parameterizedType) {
                if (parameterizedType.getRawType().equals(BaseMapper.class)) {
                    Type[] typeArgs = parameterizedType.getActualTypeArguments();
                    if (typeArgs.length > 0) {
                        return (Class<?>) typeArgs[0];
                    }
                }
            }
        }
        return null;
    }


    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
}
