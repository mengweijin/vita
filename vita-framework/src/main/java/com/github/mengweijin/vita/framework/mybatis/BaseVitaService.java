package com.github.mengweijin.vita.framework.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.domain.PageQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * BaseService 实现类（ 泛型：M 是 mapper 对象，T 是实体，V 是实体对应的 VO ）
 *
 * @author mengweijin
 * @since 2026/1/11
 */
public abstract class BaseVitaService<M extends BaseVitaMapper<T, V>, T, V> extends CrudRepository<M, T> {

    /**
     * 构建自定义 Lambda 语法查询 Wrapper
     *
     * @param entity 实体
     * @return LambdaQueryWrapper<T>
     */
    public abstract LambdaQueryWrapper<T> buildQueryWrapper(T entity);

    /**
     * 默认等值 Lambda 语法查询 Wrapper
     *
     * @param entity 实体
     * @return LambdaQueryWrapper<T>
     */
    public LambdaQueryWrapper<T> defaultQueryWrapper(T entity) {
        return Wrappers.lambdaQuery(entity);
    }

    /**
     * 根据 ID 查询 VO
     *
     * @param id 主键 ID
     */
    public V getVoById(Serializable id) {
        return getBaseMapper().selectVoById(id);
    }

    /**
     * 查询 VO（根据 ID 批量查询）
     *
     * @param idList 主键 ID 列表
     */
    public List<V> listVoByIds(Collection<? extends Serializable> idList) {
        return getBaseMapper().selectVoByIds(idList);
    }

    /**
     * 根据 Wrapper，查询一条记录 VO <br/>
     * <p>结果集，如果是多个会抛出异常，随机取一条加上限制条件 wrapper.last("LIMIT 1")</p>
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    public V getVoOne(Wrapper<T> queryWrapper) {
        return this.getVoOne(queryWrapper, true);
    }

    /**
     * 根据 Wrapper，查询一条记录 VO
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     * @param throwEx      有多个 result 是否抛出异常
     */
    public V getVoOne(Wrapper<T> queryWrapper, boolean throwEx) {
        return getBaseMapper().selectVoOne(queryWrapper, throwEx);
    }

    /**
     * 查询列表 VO
     *
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    public List<V> listVo(Wrapper<T> queryWrapper) {
        return getBaseMapper().selectVoList(queryWrapper);
    }

    /**
     * 查询所有 VO
     *
     * @see Wrappers#emptyWrapper()
     */
    public List<V> listVo() {
        return getBaseMapper().selectVoList(Wrappers.emptyWrapper());
    }

    /**
     * 翻页查询
     *
     * @param pageQuery    {@link PageQuery} 翻页对象
     * @param queryWrapper 实体对象封装操作类 {@link com.baomidou.mybatisplus.core.conditions.query.QueryWrapper}
     */
    public PageQuery<V> pageVo(PageQuery<T> pageQuery, Wrapper<T> queryWrapper) {
        return getBaseMapper().selectVoPage(pageQuery, queryWrapper);
    }

    /**
     * 分页转换
     *
     * @param page IPage<T>
     * @return PageQuery<V>
     */
    public PageQuery<V> toVoPageQuery(IPage<T> page) {
        return getBaseMapper().toVoPageQuery(page);
    }

}
