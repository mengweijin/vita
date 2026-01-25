package com.github.mengweijin.vita.framework.mybatis;

import cn.hutool.v7.core.reflect.TypeUtil;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mengweijin
 * @since 2026/1/3
 */
@Component
public class MybatisMapperHelper implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    /**
     * 缓存实体类与 Mapper 的对应关系
     */
    private final Map<Class<?>, BaseMapper<?>> mapperCache = new ConcurrentHashMap<>();

    /**
     * 缓存 Mapper 接口与其对应的实体类型
     * key: entityClass
     * value: MapperClass
     */
    private final Map<Class<?>, Class<?>> entityCache = new ConcurrentHashMap<>();

    /**
     * 获取实体类对应的表名
     */
    public String getTableName(Class<?> entityClass) {
        Objects.requireNonNull(entityClass, "Entity class cannot be null");
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        if (tableInfo == null) {
            throw new IllegalArgumentException("No table info found for entity: " + entityClass.getName());
        }
        return tableInfo.getTableName();
    }

    /**
     * 根据实体类获取对应的 Mapper 实例
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public <T> BaseMapper<T> getMapper(Class<T> entityClass) {
        Objects.requireNonNull(entityClass, "Entity class cannot be null");

        BaseMapper<?> baseMapper = mapperCache.computeIfAbsent(entityClass, clazz -> {
            Map<String, BaseMapper> beansOfType = applicationContext.getBeansOfType(BaseMapper.class);
            for (BaseMapper mapper : beansOfType.values()) {
                Class<?> entityType = this.getEntityClassByMapper(mapper);
                if (entityClass.equals(entityType)) {
                    return mapper;
                }
            }
            return null;
        });
        return (BaseMapper<T>) baseMapper;
    }

    public <T> Class<?> getEntityClassByMapper(BaseMapper<T> mapper) {
        return getGenericTypeByMapper(mapper,0);
    }

    public <T> Class<?> getVoClassByMapper(BaseMapper<T> mapper) {
        return getGenericTypeByMapper(mapper,1);
    }

    private <T> Class<?> getGenericTypeByMapper(BaseMapper<T> mapper, int index) {
        ParameterizedType parameterizedType = TypeUtil.toParameterizedType(mapper.getClass());
        Type[] typeArgs = parameterizedType.getActualTypeArguments();
        return (Class<?>) typeArgs[index];
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
}
