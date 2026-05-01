package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.text.split.SplitUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.enums.dict.EYesNo;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.system.domain.entity.CategoryDO;
import com.github.mengweijin.vita.system.domain.vo.CategoryVO;
import com.github.mengweijin.vita.system.mapper.CategoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Category Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 */
@Slf4j
@Service
public class CategoryService extends BaseVitaService<CategoryMapper, CategoryDO, CategoryVO> {

    @Override
    public boolean save(CategoryDO entity) {
        // 设置祖级列表
        String ancestors = this.generateAncestors(entity.getParentId());
        entity.setAncestors(ancestors);

        // 设置启用/停用状态（优先与父级状态保持一致）
        String disabled = this.getParentDisabledStatus(entity.getParentId());
        entity.setDisabled(disabled);

        // 保存
        return super.save(entity);
    }

    private String getParentDisabledStatus(Long parentId) {
        if (parentId == null) {
            return EYesNo.N.getValue();
        }
        CategoryDO parent = this.getById(parentId);
        return Optional.ofNullable(parent).map(CategoryDO::getDisabled).orElse(EYesNo.N.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(CategoryDO entity) {
        CategoryDO dept = this.getById(entity.getId());
        // 父部门未发生改变
        if (dept.getParentId().equals(entity.getParentId())) {
            return super.updateById(entity);
        }

        // 父部门发生了改变。先更新本身的祖级列表，再递归更新子部门的祖级列表
        String ancestors = this.generateAncestors(entity.getParentId());
        entity.setAncestors(ancestors);
        // 更新本身（包括本身的祖级列表）
        super.updateById(entity);

        // 更新子部门的祖级列表
        this.updateChildAncestors(entity.getId());
        return true;
    }

    /**
     * 递归更新父部门下所有子部门的祖级列表
     *
     * @param parentId 父部门 parentId
     */
    public void updateChildAncestors(Long parentId) {
        LambdaQueryChainWrapper<CategoryDO> wrapper = this.lambdaQuery();
        // 1. 查询直接子部门
        if (parentId == null) {
            wrapper.isNull(CategoryDO::getParentId);
        } else {
            wrapper.eq(CategoryDO::getParentId, parentId);
        }
        List<CategoryDO> list = wrapper.list();

        // 2. 循环更新子部门
        for (CategoryDO dept : list) {
            String ancestors = this.generateAncestors(dept.getParentId());
            this.lambdaUpdate()
                    .set(CategoryDO::getAncestors, ancestors)
                    .eq(CategoryDO::getId, dept.getId())
                    .update();
            // 3. 递归更新子部门
            this.updateChildAncestors(dept.getId());
        }
    }

    public List<Long> getParentIds(Long id) {
        CategoryDO dept = this.getById(id);
        String ancestors = dept.getAncestors();
        List<String> strings = SplitUtil.splitTrim(ancestors, Const.SLASH);
        return strings.stream().map(Long::valueOf).toList();
    }

    public List<Long> getChildrenIds(Long id, boolean withSelf) {
        List<CategoryDO> children = this.getChildren(id, withSelf);
        return children.stream().map(CategoryDO::getId).toList();
    }

    public List<CategoryDO> getChildren(Long id, boolean withSelf) {
        LambdaQueryChainWrapper<CategoryDO> wrapper = this.lambdaQuery();
        if (id == null) {
            return wrapper.list();
        }
        CategoryDO category = this.getById(id);
        List<CategoryDO> list = wrapper.likeRight(CategoryDO::getAncestors, category.getAncestors() + category.getId()).list();
        if (withSelf) {
            List<CategoryDO> withSelfList = new ArrayList<>(list);
            withSelfList.add(0, category);
            return withSelfList;
        }

        return list;
    }

    /**
     * 生成部门祖级列表
     *
     * @param parentId 父部门 parentId
     * @return 祖级列表
     */
    private String generateAncestors(Long parentId) {
        if (parentId == null) {
            return Const.SLASH;
        }
        CategoryDO parent = this.getById(parentId);
        return parent.getAncestors() + parent.getId() + Const.SLASH;
    }

    @Override
    public boolean removeByIds(Collection<?> ids) {
        Long subDeptCount = this.lambdaQuery().in(CategoryDO::getParentId, ids).count();
        if (subDeptCount > 0) {
            throw new ClientException("Please delete the child node first!");
        }
        return super.removeByIds(ids);
    }

    public CategoryDO getByCode(String code) {
        return this.lambdaQuery().eq(CategoryDO::getCode, code).one();
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
        LambdaQueryWrapper<CategoryDO> wrapper = Wrappers.lambdaQuery();
        // 筛选根节点
        wrapper.isNull(CategoryDO::getParentId);

        wrapper.eq(category.getId() != null, CategoryDO::getId, category.getId());
        wrapper.eq(StrUtil.isNotBlank(category.getDisabled()), CategoryDO::getDisabled, category.getDisabled());
        wrapper.eq(category.getCreateBy() != null, CategoryDO::getCreateBy, category.getCreateBy());
        wrapper.eq(category.getUpdateBy() != null, CategoryDO::getUpdateBy, category.getUpdateBy());
        wrapper.gt(category.getStartCreateTime() != null, CategoryDO::getCreateTime, category.getStartCreateTime());
        wrapper.le(category.getEndCreateTime() != null, CategoryDO::getCreateTime, category.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(category.getName()), CategoryDO::getName, category.getName());
        wrapper.like(StrUtil.isNotBlank(category.getCode()), CategoryDO::getCode, category.getCode());
        return wrapper;
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

    public boolean enable(Long id) {
        return this.lambdaUpdate()
                .set(CategoryDO::getDisabled, EYesNo.N.getValue())
                .eq(CategoryDO::getId, id)
                .update();
    }

    public boolean disable(Long id) {
        List<Long> list = this.getChildrenIds(id, true);
        return this.lambdaUpdate()
                .set(CategoryDO::getDisabled, EYesNo.Y.getValue())
                .in(CategoryDO::getId, list)
                .update();
    }

    public PageQuery<CategoryVO> pageRootTree(PageQuery<CategoryDO> page, LambdaQueryWrapper<CategoryDO> wrapper) {
        PageQuery<CategoryVO> rootPage = this.pageVo(page, wrapper);

        List<CategoryVO> list = new ArrayList<>(rootPage.getPageRecords());
        rootPage.getPageRecords().forEach(i -> {
            List<CategoryDO> children = this.getChildren(i.getId(), false);
            List<CategoryVO> voList = MapstructUtils.getConverter().convert(children, CategoryVO.class);
            list.addAll(voList);
        });

        rootPage.setPageRecords(list);
        return rootPage;
    }

    public List<CategoryDO> getChildrenListByCode(String code, boolean withSelf) {
        CategoryDO category = this.getByCode(code);
        if (category == null) {
            return Collections.emptyList();
        }
        return this.getChildren(category.getId(), withSelf);
    }
}
