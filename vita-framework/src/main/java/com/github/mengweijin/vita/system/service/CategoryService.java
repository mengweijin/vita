package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.framework.util.ObjectUtils;
import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import com.github.mengweijin.vita.system.domain.vo.CategoryVO;
import com.github.mengweijin.vita.system.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Category Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 */
@Slf4j
@Service
public class CategoryService extends BaseVitaService<CategoryMapper, CategoryDO, CategoryVO> {

    public CategoryDO getByCode(String code) {
        return this.lambdaQuery().eq(CategoryDO::getCode, code).one();
    }

    public List<CategoryDO> getChildrenListByCode(String code) {
        List<Long> ids = this.getChildrenIdsByCode(code);
        return this.lambdaQuery().in(CategoryDO::getId, ids).list();
    }

    public List<Long> getChildrenIdsByCode(String code) {
        CategoryDO categoryDO = this.getByCode(code);
        if (categoryDO == null) {
            return new ArrayList<>();
        }
        return this.getBaseMapper().selectChildrenIdsById(categoryDO.getId());
    }

    @Override
    public LambdaQueryWrapper<CategoryDO> buildQueryWrapper(CategoryDO category) {
        LambdaQueryWrapper<CategoryDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(category.getId() != null, CategoryDO::getId, category.getId());
        wrapper.eq(category.getParentId() != null, CategoryDO::getParentId, category.getParentId());
        wrapper.eq(StrUtil.isNotBlank(category.getDisabled()), CategoryDO::getDisabled, category.getDisabled());
        wrapper.eq(category.getCreateBy() != null, CategoryDO::getCreateBy, category.getCreateBy());
        wrapper.eq(category.getUpdateBy() != null, CategoryDO::getUpdateBy, category.getUpdateBy());
        wrapper.gt(category.getStartCreateTime() != null, CategoryDO::getCreateTime, category.getStartCreateTime());
        wrapper.le(category.getEndCreateTime() != null, CategoryDO::getCreateTime, category.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(category.getName()), CategoryDO::getName, category.getName());
        wrapper.like(StrUtil.isNotBlank(category.getCode()), CategoryDO::getCode, category.getCode());
        return wrapper;
    }

    public LambdaQueryWrapper<CategoryDO> buildRootQueryWrapper(CategoryDO category) {
        if(ObjectUtils.isAllFieldsBlank(category)) {
            // 未携带任何参数时，查询 parent id 为 null 的，即为根节点
            LambdaQueryWrapper<CategoryDO> wrapper = Wrappers.lambdaQuery();
            wrapper.isNull(CategoryDO::getParentId);
            return wrapper;
        }
        // 携带参数时，根据条件过滤所有数据
        return this.buildQueryWrapper(category);
    }

    public List<CategoryVO> listChildrenByParentId(Long parentId) {
        List<Long> idList = this.getBaseMapper().selectChildrenIdsById(parentId);
        if(CollUtil.isEmpty(idList)){
            return Collections.emptyList();
        }
        List<CategoryDO> list = this.lambdaQuery().in(CategoryDO::getId, idList).list();
        return MapstructUtils.getConverter().convert(list, CategoryVO.class);
    }


    public List<CategoryVO> listChildrenByParentCode(String code) {
        CategoryDO categoryDO = this.getByCode(code);
        if (categoryDO == null) {
            return new ArrayList<>();
        }
        return this.listChildrenByParentId(categoryDO.getId());
    }

    public List<CategoryVO> listChildrenWithParentByCode(String code) {
        CategoryDO categoryDO = this.getByCode(code);
        if (categoryDO == null) {
            return new ArrayList<>();
        }
        List<Long> ids = this.getBaseMapper().selectChildrenIdsById(categoryDO.getId());
        ids.add(0, categoryDO.getId());
        List<CategoryDO> list = this.lambdaQuery().in(CategoryDO::getId, ids).list();
        return MapstructUtils.getConverter().convert(list, CategoryVO.class);
    }

    @Cacheable(value = CacheNames.CATEGORY_ID_TO_NAME, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getNameById(Long id) {
        return this.lambdaQuery()
                .select(CategoryDO::getName)
                .eq(CategoryDO::getId, id)
                .oneOpt()
                .map(CategoryDO::getName)
                .orElse(null);
    }

}
