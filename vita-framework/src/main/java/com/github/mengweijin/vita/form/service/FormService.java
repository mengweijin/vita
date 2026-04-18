package com.github.mengweijin.vita.form.service;

import cn.hutool.v7.core.collection.CollUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.form.domain.entity.FormDO;
import com.github.mengweijin.vita.form.domain.vo.FormVO;
import com.github.mengweijin.vita.form.mapper.FormMapper;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.framework.util.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * 流程表单表 WorkflowForm Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Slf4j
@Service
@AllArgsConstructor
public class FormService extends BaseVitaService<FormMapper, FormDO, FormVO> {

    @Override
    public LambdaQueryWrapper<FormDO> buildQueryWrapper(FormDO form) {
        LambdaQueryWrapper<FormDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(form.getId() != null, FormDO::getId, form.getId());
        wrapper.eq(form.getParentId() != null, FormDO::getParentId, form.getParentId());
        wrapper.eq(StrUtil.isNotBlank(form.getType()), FormDO::getType, form.getType());
        wrapper.eq(form.getDynamicFormId() != null, FormDO::getDynamicFormId, form.getDynamicFormId());
        wrapper.gt(form.getStartCreateTime() != null, FormDO::getCreateTime, form.getStartCreateTime());
        wrapper.le(form.getEndCreateTime() != null, FormDO::getCreateTime, form.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(form.getName()), FormDO::getName, form.getName());
        wrapper.like(StrUtil.isNotBlank(form.getStaticFormPath()), FormDO::getStaticFormPath, form.getStaticFormPath());
        wrapper.like(StrUtil.isNotBlank(form.getRemark()), FormDO::getRemark, form.getRemark());
        return wrapper;
    }

    public LambdaQueryWrapper<FormDO> buildRootQueryWrapper(FormDO form) {
        if(ObjectUtils.isAllFieldsBlank(form)) {
            // 未携带任何参数时，查询 parent id 为 null 的，即为根节点
            LambdaQueryWrapper<FormDO> wrapper = Wrappers.lambdaQuery();
            wrapper.isNull(FormDO::getParentId);
            return wrapper;
        }

        // 携带参数时，根据条件过滤所有数据
        return this.buildQueryWrapper(form);
    }

    public List<FormVO> listChildrenByParentId(Long parentId) {
        List<Long> idList = this.getBaseMapper().selectChildrenIdsById(parentId);
        if(CollUtil.isEmpty(idList)){
            return Collections.emptyList();
        }
        List<FormDO> list = this.lambdaQuery().in(FormDO::getId, idList).list();
        return MapstructUtils.getConverter().convert(list, FormVO.class);
    }
}
