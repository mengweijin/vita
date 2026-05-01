package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.core.text.split.SplitUtil;
import cn.hutool.v7.extra.spring.SpringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.github.mengweijin.vita.framework.cache.CacheConst;
import com.github.mengweijin.vita.framework.cache.CacheNames;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.enums.dict.EYesNo;
import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.system.domain.entity.DeptDO;
import com.github.mengweijin.vita.system.domain.entity.UserDO;
import com.github.mengweijin.vita.system.domain.vo.DeptVO;
import com.github.mengweijin.vita.system.mapper.DeptMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * Dept Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
public class DeptService extends BaseVitaService<DeptMapper, DeptDO, DeptVO> {

    @Override
    public boolean save(DeptDO entity) {
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
        DeptDO parent = this.getById(parentId);
        return Optional.ofNullable(parent).map(DeptDO::getDisabled).orElse(EYesNo.N.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(DeptDO entity) {
        DeptDO dept = this.getById(entity.getId());
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
        LambdaQueryChainWrapper<DeptDO> wrapper = this.lambdaQuery();
        // 1. 查询直接子部门
        if (parentId == null) {
            wrapper.isNull(DeptDO::getParentId);
        } else {
            wrapper.eq(DeptDO::getParentId, parentId);
        }
        List<DeptDO> list = wrapper.list();

        // 2. 循环更新子部门
        for (DeptDO dept : list) {
            String ancestors = this.generateAncestors(dept.getParentId());
            this.lambdaUpdate()
                    .set(DeptDO::getAncestors, ancestors)
                    .eq(DeptDO::getId, dept.getId())
                    .update();
            // 3. 递归更新子部门
            this.updateChildAncestors(dept.getId());
        }
    }

    public List<Long> getParentIds(Long id) {
        DeptDO dept = this.getById(id);
        String ancestors = dept.getAncestors();
        List<String> strings = SplitUtil.splitTrim(ancestors, Const.SLASH);
        return strings.stream().map(Long::valueOf).toList();
    }

    public List<Long> getChildrenIds(Long id) {
        List<DeptDO> children = this.getChildren(id);
        return children.stream().map(DeptDO::getId).toList();
    }

    public List<DeptDO> getChildren(Long id) {
        LambdaQueryChainWrapper<DeptDO> wrapper = this.lambdaQuery();
        if (id == null) {
            return wrapper.list();
        }
        DeptDO dept = this.getById(id);
        return wrapper.likeRight(DeptDO::getAncestors, dept.getAncestors() + dept.getId()).list();
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
        DeptDO parent = this.getById(parentId);
        return parent.getAncestors() + parent.getId() + Const.SLASH;
    }

    @Override
    public boolean removeByIds(Collection<?> ids) {
        Long subDeptCount = this.lambdaQuery().in(DeptDO::getParentId, ids).count();
        if (subDeptCount > 0) {
            throw new ClientException("Please delete the child node first!");
        }

        UserService userService = SpringUtil.getBean(UserService.class);
        Long userCount = userService.lambdaQuery().in(UserDO::getDeptId, ids).count();
        if (userCount > 0) {
            throw new ClientException("Please remove all users under the department first!");
        }
        return super.removeByIds(ids);
    }

    @Override
    public LambdaQueryWrapper<DeptDO> buildQueryWrapper(DeptDO dept) {
        LambdaQueryWrapper<DeptDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(dept.getId() != null, DeptDO::getId, dept.getId());
        wrapper.eq(dept.getParentId() != null, DeptDO::getParentId, dept.getParentId());
        wrapper.eq(StrUtil.isNotBlank(dept.getDisabled()), DeptDO::getDisabled, dept.getDisabled());
        wrapper.eq(dept.getCreateBy() != null, DeptDO::getCreateBy, dept.getCreateBy());
        wrapper.eq(dept.getUpdateBy() != null, DeptDO::getUpdateBy, dept.getUpdateBy());
        wrapper.gt(dept.getStartCreateTime() != null, DeptDO::getCreateTime, dept.getStartCreateTime());
        wrapper.le(dept.getEndCreateTime() != null, DeptDO::getCreateTime, dept.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(dept.getCode()), DeptDO::getCode, dept.getCode());
        wrapper.like(StrUtil.isNotBlank(dept.getName()), DeptDO::getName, dept.getName());
        return wrapper;
    }

    @Cacheable(value = CacheNames.DEPT_ID_TO_NAME, key = "#id + ''", unless = CacheConst.UNLESS_OBJECT_NULL)
    public String getNameById(Long id) {
        return this.lambdaQuery()
                .select(DeptDO::getName)
                .eq(DeptDO::getId, id)
                .oneOpt()
                .map(DeptDO::getName)
                .orElse(null);
    }

    public boolean enable(Long id) {
        return this.lambdaUpdate()
                .set(DeptDO::getDisabled, EYesNo.N.getValue())
                .eq(DeptDO::getId, id)
                .update();
    }

    public boolean disable(Long id) {
        List<Long> ids = this.getChildrenIds(id);
        ArrayList<Long> list = new ArrayList<>(ids);
        list.add(id);
        return this.lambdaUpdate()
                .set(DeptDO::getDisabled, EYesNo.Y.getValue())
                .in(DeptDO::getId, list)
                .update();
    }
}
