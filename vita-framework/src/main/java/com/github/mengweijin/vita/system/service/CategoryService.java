package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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

    /**
     * Custom paging query
     *
     * @param page     page
     * @param categoryDO {@link CategoryDO}
     * @return IPage
     */
    public IPage<CategoryDO> page(IPage<CategoryDO> page, CategoryDO categoryDO) {
        LambdaQueryWrapper<CategoryDO> query = new LambdaQueryWrapper<>();
        query
                .eq(!Objects.isNull(categoryDO.getParentId()), CategoryDO::getParentId, categoryDO.getParentId())
                .eq(StrValidator.isNotBlank(categoryDO.getCode()), CategoryDO::getCode, categoryDO.getCode())
                .eq(StrValidator.isNotBlank(categoryDO.getRemark()), CategoryDO::getRemark, categoryDO.getRemark())
                .eq(!Objects.isNull(categoryDO.getSeq()), CategoryDO::getSeq, categoryDO.getSeq())
                .eq(StrValidator.isNotBlank(categoryDO.getDisabled()), CategoryDO::getDisabled, categoryDO.getDisabled())
                .eq(!Objects.isNull(categoryDO.getId()), CategoryDO::getId, categoryDO.getId())
                .eq(!Objects.isNull(categoryDO.getCreateBy()), CategoryDO::getCreateBy, categoryDO.getCreateBy())
                .eq(!Objects.isNull(categoryDO.getCreateTime()), CategoryDO::getCreateTime, categoryDO.getCreateTime())
                .eq(!Objects.isNull(categoryDO.getUpdateBy()), CategoryDO::getUpdateBy, categoryDO.getUpdateBy())
                .eq(!Objects.isNull(categoryDO.getUpdateTime()), CategoryDO::getUpdateTime, categoryDO.getUpdateTime())
                .like(StrValidator.isNotBlank(categoryDO.getName()), CategoryDO::getName, categoryDO.getName());
        return this.page(page, query);
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
