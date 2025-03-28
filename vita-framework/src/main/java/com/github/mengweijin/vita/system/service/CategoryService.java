package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.system.domain.entity.Category;
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
public class CategoryService extends CrudRepository<CategoryMapper, Category> {

    /**
     * Custom paging query
     *
     * @param page     page
     * @param category {@link Category}
     * @return IPage
     */
    public IPage<Category> page(IPage<Category> page, Category category) {
        LambdaQueryWrapper<Category> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(category.getParentId()), Category::getParentId, category.getParentId())
                .eq(StrValidator.isNotBlank(category.getCode()), Category::getCode, category.getCode())
                .eq(StrValidator.isNotBlank(category.getRemark()), Category::getRemark, category.getRemark())
                .eq(!Objects.isNull(category.getSeq()), Category::getSeq, category.getSeq())
                .eq(StrValidator.isNotBlank(category.getDisabled()), Category::getDisabled, category.getDisabled())
                .eq(!Objects.isNull(category.getId()), Category::getId, category.getId())
                .eq(!Objects.isNull(category.getCreateBy()), Category::getCreateBy, category.getCreateBy())
                .eq(!Objects.isNull(category.getCreateTime()), Category::getCreateTime, category.getCreateTime())
                .eq(!Objects.isNull(category.getUpdateBy()), Category::getUpdateBy, category.getUpdateBy())
                .eq(!Objects.isNull(category.getUpdateTime()), Category::getUpdateTime, category.getUpdateTime())
                .like(StrValidator.isNotBlank(category.getName()), Category::getName, category.getName());
        return this.page(page, query);
    }

    public Category getByCode(String code) {
        return this.lambdaQuery().eq(Category::getCode, code).one();
    }

    public List<Category> getChildrenListByCode(String code) {
        List<Long> ids = this.getChildrenIdsByCode(code);
        return this.lambdaQuery().in(Category::getId, ids).list();
    }

    public List<Long> getChildrenIdsByCode(String code) {
        Category category = this.getByCode(code);
        if (category == null) {
            return new ArrayList<>();
        }
        return this.getBaseMapper().selectChildrenIdsById(category.getId());
    }

}
