package com.github.mengweijin.vita.framework.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.util.MapstructUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * VitaBaseMapper 类（ 泛型：T 是实体, V 是实体对应的 VO ）
 * @author mengweijin
 * @since 2026/1/11
 */
public interface BaseVitaMapper<T, V> extends BaseMapper<T> {

    /**
     * 获取实体对应的 Class 对象
     *
     * @return 实体对应的 Class 对象
     */
    @SuppressWarnings({"unchecked"})
    default Class<T> entityClass() {
        return (Class<T>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseVitaMapper.class, 0);
    }

    /**
     * 获取实体对应的 VO 的 Class 对象
     *
     * @return 实体对应的 VO 的 Class 对象
     */
    @SuppressWarnings({"unchecked"})
    default Class<V> voClass() {
        return (Class<V>) ReflectionKit.getSuperClassGenericType(this.getClass(), BaseVitaMapper.class, 1);
    }

    /**
     * 根据 ID 查询 VO
     *
     * @param id 主键 ID
     * @return VO
     */
    default V selectVoById(Serializable id) {
        T t = this.selectById(id);
        return MapstructUtils.getInstance().convert(t, this.voClass());
    }

    /**
     * 查询 VO（根据ID 批量查询）
     *
     * @param idList 主键ID列表(不能为 null 以及 empty)
     * @return 数据列表
     */
    default List<V> selectVoByIds(Collection<? extends Serializable> idList) {
        List<T> list = this.selectByIds(idList);
        return MapstructUtils.getInstance().convert(list, this.voClass());
    }

    /**
     * 根据 entity 条件，查询一条记录的 VO
     * <p>查询一条记录，例如 qw.last("limit 1") 限制取一条记录, 注意：多条数据会报异常</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return VO
     */
    default V selectVoOne(Wrapper<T> queryWrapper) {
        return this.selectVoOne(queryWrapper, true);
    }

    /**
     * 根据 entity 条件，查询一条 VO 记录，现在会根据{@code throwEx}参数判断是否抛出异常，如果为false就直接返回一条数据
     * <p>查询一条记录，例如 qw.last("limit 1") 限制取一条记录, 注意：多条数据会报异常</p>
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @param throwEx      boolean 参数，为true如果存在多个结果直接抛出异常
     * @return VO
     */
    default V selectVoOne(Wrapper<T> queryWrapper, boolean throwEx) {
        T t = this.selectOne(queryWrapper, throwEx);
        return MapstructUtils.getInstance().convert(t, this.voClass());
    }

    /**
     * 根据 entity 条件，查询全部 VO 记录
     *
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return VO list
     */
    default List<V> selectVoList(Wrapper<T> queryWrapper) {
        List<T> list = this.selectList(queryWrapper);
        return MapstructUtils.getInstance().convert(list, this.voClass());
    }

    /**
     * 根据 entity 条件，查询全部 VO 记录（并翻页）
     *
     * @param pageQuery     {@link PageQuery} 分页查询对象
     * @param queryWrapper 实体对象封装操作类（可以为 null）
     * @return VO Page
     */
    default PageQuery<V> selectVoPage(PageQuery<T> pageQuery, Wrapper<T> queryWrapper) {
        IPage<T> page = this.selectPage(pageQuery.toPage(), queryWrapper);
        return this.toVoPageQuery(page);
    }

    /**
     * IPage<T> 转 PageQuery<V>
     * @param page IPage<T>
     * @return PageQuery<V>
     */
    default PageQuery<V> toVoPageQuery(IPage<T> page) {
        List<V> list = MapstructUtils.getInstance().convert(page.getRecords(), this.voClass());
        return new PageQuery<>(page.getCurrent(), page.getSize(), page.getTotal(), list);
    }

    /**
     * 插入一条记录
     *
     * @param bo 实体对应的 BO 对象
     * @return count
     */
    default <B> int insertByBo(B bo) {
        T t = MapstructUtils.getInstance().convert(bo, this.entityClass());
        return this.insert(t);
    }

    /**
     * 主键存在更新记录，否则插入一条记录
     *
     * @param bo bo
     * @return boolean
     * @param <B> BO Class Type
     */
    default <B> boolean insertOrUpdateByBo(B bo) {
        T t = MapstructUtils.getInstance().convert(bo, this.entityClass());
        return this.insertOrUpdate(t);
    }


    /**
     * 根据 ID 修改
     *
     * @param bo 实体对应的 BO 对象
     * @return count
     */
    default <B> int updateByBoById(B bo) {
        T t = MapstructUtils.getInstance().convert(bo, this.entityClass());
        return this.updateById(t);
    }

}
