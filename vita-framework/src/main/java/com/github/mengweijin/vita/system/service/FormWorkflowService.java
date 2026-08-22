package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.system.domain.entity.FormWorkflowDO;
import com.github.mengweijin.vita.system.domain.vo.FormWorkflowVO;
import com.github.mengweijin.vita.system.mapper.FormWorkflowMapper;
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
public class FormWorkflowService extends BaseVitaService<FormWorkflowMapper, FormWorkflowDO, FormWorkflowVO> {

    @Override
    public LambdaQueryWrapper<FormWorkflowDO> buildQueryWrapper(FormWorkflowDO form) {
        LambdaQueryWrapper<FormWorkflowDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(form.getId() != null, FormWorkflowDO::getId, form.getId());
        wrapper.eq(StrUtil.isNotBlank(form.getRoutePath()), FormWorkflowDO::getRoutePath, form.getRoutePath());
        wrapper.gt(form.getStartCreateTime() != null, FormWorkflowDO::getCreateTime, form.getStartCreateTime());
        wrapper.le(form.getEndCreateTime() != null, FormWorkflowDO::getCreateTime, form.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(form.getName()), FormWorkflowDO::getName, form.getName());
        wrapper.like(StrUtil.isNotBlank(form.getRemark()), FormWorkflowDO::getRemark, form.getRemark());
        return wrapper;
    }
}
