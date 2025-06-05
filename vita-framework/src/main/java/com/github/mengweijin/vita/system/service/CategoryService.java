package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import com.github.mengweijin.vita.system.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Category Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 */
@Slf4j
@Service
public class CategoryService extends CrudRepository<CategoryMapper, CategoryDO> {

    public LambdaQueryWrapper<CategoryDO> getQueryWrapper(CategoryDO category) {
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category.getId() != null, CategoryDO::getId, category.getId());
        wrapper.eq(category.getParentId() != null, CategoryDO::getParentId, category.getParentId());
        wrapper.eq(StrUtil.isNotBlank(category.getDisabled()), CategoryDO::getDisabled, category.getDisabled());
        wrapper.eq(category.getCreateBy() != null, CategoryDO::getCreateBy, category.getCreateBy());
        wrapper.eq(category.getUpdateBy() != null, CategoryDO::getUpdateBy, category.getUpdateBy());
        wrapper.gt(category.getSearchStartTime() != null, CategoryDO::getCreateTime, category.getSearchStartTime());
        wrapper.le(category.getSearchEndTime() != null, CategoryDO::getCreateTime, category.getSearchEndTime());
        if (StrUtil.isNotBlank(category.getKeywords())) {
            wrapper.and(w -> {
                w.or(w1 -> w1.like(CategoryDO::getName, category.getKeywords()));
                w.or(w1 -> w1.like(CategoryDO::getCode, category.getKeywords()));
            });
        }
        return wrapper;
    }

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

}
