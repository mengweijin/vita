package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import com.github.mengweijin.vita.system.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        wrapper.eq(!Objects.isNull(category.getId()), CategoryDO::getId, category.getId());
        wrapper.eq(!Objects.isNull(category.getParentId()), CategoryDO::getParentId, category.getParentId());
        wrapper.eq(StrValidator.isNotBlank(category.getDisabled()), CategoryDO::getDisabled, category.getDisabled());
        wrapper.eq(!Objects.isNull(category.getCreateBy()), CategoryDO::getCreateBy, category.getCreateBy());
        wrapper.eq(!Objects.isNull(category.getUpdateBy()), CategoryDO::getUpdateBy, category.getUpdateBy());
        wrapper.gt(!Objects.isNull(category.getSearchStartTime()), CategoryDO::getCreateTime, category.getSearchStartTime());
        wrapper.le(!Objects.isNull(category.getSearchEndTime()), CategoryDO::getCreateTime, category.getSearchEndTime());
        if (StrValidator.isNotBlank(category.getKeywords())) {
            wrapper.or(w -> w.like(CategoryDO::getName, category.getKeywords()));
            wrapper.or(w -> w.like(CategoryDO::getCode, category.getKeywords()));
        }
        wrapper.orderByAsc(CategoryDO::getSeq);
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
