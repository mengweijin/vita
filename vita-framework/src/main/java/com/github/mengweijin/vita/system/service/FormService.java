package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.text.split.SplitUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.system.domain.entity.FormDO;
import com.github.mengweijin.vita.system.domain.vo.FormVO;
import com.github.mengweijin.vita.system.mapper.FormMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 流程表单表 WorkflowForm Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Slf4j
@Service
@AllArgsConstructor
public class FormService extends BaseVitaService<FormMapper, FormDO, FormVO> {

    @Override
    public boolean save(FormDO entity) {
        // 设置祖级列表
        String ancestors = this.generateAncestors(entity.getParentId());
        entity.setAncestors(ancestors);
        // 保存
        return super.save(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(FormDO entity) {
        // 更新本身的祖级列表
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
        LambdaQueryChainWrapper<FormDO> wrapper = this.lambdaQuery();
        // 1. 查询直接子部门
        if (parentId == null) {
            wrapper.isNull(FormDO::getParentId);
        } else {
            wrapper.eq(FormDO::getParentId, parentId);
        }
        List<FormDO> list = wrapper.list();

        // 2. 循环更新子部门
        for (FormDO form : list) {
            String ancestors = this.generateAncestors(form.getParentId());
            this.lambdaUpdate()
                    .set(FormDO::getAncestors, ancestors)
                    .eq(FormDO::getId, form.getId())
                    .update();
            // 3. 递归更新子部门
            this.updateChildAncestors(form.getId());
        }
    }

    public List<Long> getParentIds(Long id) {
        FormDO form = this.getById(id);
        String ancestors = form.getAncestors();
        List<String> strings = SplitUtil.splitTrim(ancestors, Const.SLASH);
        return strings.stream().map(Long::valueOf).toList();
    }

    public List<Long> getChildrenIds(Long id, boolean withSelf) {
        List<FormDO> children = this.getChildren(id, withSelf);
        return children.stream().map(FormDO::getId).toList();
    }

    public List<FormDO> getChildren(Long id, boolean withSelf) {
        LambdaQueryChainWrapper<FormDO> wrapper = this.lambdaQuery();
        if (id == null) {
            return wrapper.list();
        }
        FormDO form = this.getById(id);
        List<FormDO> list = wrapper.likeRight(FormDO::getAncestors, form.getAncestors() + form.getId()).list();
        if (withSelf) {
            List<FormDO> withSelfList = new ArrayList<>(list);
            withSelfList.add(0, form);
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
        FormDO parent = this.getById(parentId);
        return parent.getAncestors() + parent.getId() + Const.SLASH;
    }

    @Override
    public boolean removeByIds(Collection<?> ids) {
        Long subDeptCount = this.lambdaQuery().in(FormDO::getParentId, ids).count();
        if (subDeptCount > 0) {
            throw new ClientException("Please delete the child node first!");
        }
        return super.removeByIds(ids);
    }

    @Override
    public LambdaQueryWrapper<FormDO> buildQueryWrapper(FormDO form) {
        LambdaQueryWrapper<FormDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(form.getId() != null, FormDO::getId, form.getId());
        wrapper.eq(StrUtil.isNotBlank(form.getType()), FormDO::getType, form.getType());
        wrapper.eq(StrUtil.isNotBlank(form.getFormPath()), FormDO::getFormPath, form.getFormPath());
        wrapper.gt(form.getStartCreateTime() != null, FormDO::getCreateTime, form.getStartCreateTime());
        wrapper.le(form.getEndCreateTime() != null, FormDO::getCreateTime, form.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(form.getName()), FormDO::getName, form.getName());
        wrapper.like(StrUtil.isNotBlank(form.getRemark()), FormDO::getRemark, form.getRemark());
        return wrapper;
    }

    public LambdaQueryWrapper<FormDO> buildRootQueryWrapper(FormDO form) {
        LambdaQueryWrapper<FormDO> wrapper = Wrappers.lambdaQuery();
        // 筛选根节点
        wrapper.isNull(FormDO::getParentId);

        wrapper.eq(form.getId() != null, FormDO::getId, form.getId());
        wrapper.eq(StrUtil.isNotBlank(form.getType()), FormDO::getType, form.getType());
        wrapper.eq(StrUtil.isNotBlank(form.getFormPath()), FormDO::getFormPath, form.getFormPath());
        wrapper.gt(form.getStartCreateTime() != null, FormDO::getCreateTime, form.getStartCreateTime());
        wrapper.le(form.getEndCreateTime() != null, FormDO::getCreateTime, form.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(form.getName()), FormDO::getName, form.getName());
        wrapper.like(StrUtil.isNotBlank(form.getRemark()), FormDO::getRemark, form.getRemark());
        return wrapper;
    }

    public PageQuery<FormVO> pageRootTree(PageQuery<FormDO> page, LambdaQueryWrapper<FormDO> wrapper) {
        PageQuery<FormVO> rootPage = this.pageVo(page, wrapper);

        List<FormVO> list = new ArrayList<>(rootPage.getPageRecords());
        rootPage.getPageRecords().forEach(i -> {
            List<FormDO> children = this.getChildren(i.getId(), false);
            List<FormVO> voList = MapstructUtils.getConverter().convert(children, FormVO.class);
            list.addAll(voList);
        });

        rootPage.setPageRecords(list);
        return rootPage;
    }
}
