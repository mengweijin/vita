package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.system.domain.entity.FormDO;
import com.github.mengweijin.vita.system.domain.vo.FormVO;
import com.github.mengweijin.vita.system.mapper.FormMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public LambdaQueryWrapper<FormDO> buildQueryWrapper(FormDO form) {
        LambdaQueryWrapper<FormDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(form.getId() != null, FormDO::getId, form.getId());
        wrapper.eq(StrUtil.isNotBlank(form.getType()), FormDO::getType, form.getType());
        wrapper.eq(form.getDynamicId() != null, FormDO::getDynamicId, form.getDynamicId());
        wrapper.gt(form.getStartCreateTime() != null, FormDO::getCreateTime, form.getStartCreateTime());
        wrapper.le(form.getEndCreateTime() != null, FormDO::getCreateTime, form.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(form.getName()), FormDO::getName, form.getName());
        wrapper.like(StrUtil.isNotBlank(form.getStaticRoute()), FormDO::getStaticRoute, form.getStaticRoute());
        wrapper.like(StrUtil.isNotBlank(form.getRemark()), FormDO::getRemark, form.getRemark());
        return wrapper;
    }
}
