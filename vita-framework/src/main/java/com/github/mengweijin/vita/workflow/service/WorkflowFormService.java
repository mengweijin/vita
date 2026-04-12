package com.github.mengweijin.vita.workflow.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.workflow.domain.entity.WorkflowFormDO;
import com.github.mengweijin.vita.workflow.domain.vo.WorkflowFormVO;
import com.github.mengweijin.vita.workflow.mapper.WorkflowFormMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class WorkflowFormService extends BaseVitaService<WorkflowFormMapper, WorkflowFormDO, WorkflowFormVO> {

    @Override
    public LambdaQueryWrapper<WorkflowFormDO> buildQueryWrapper(WorkflowFormDO workflowForm) {
        LambdaQueryWrapper<WorkflowFormDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(workflowForm.getId() != null, WorkflowFormDO::getId, workflowForm.getId());
        wrapper.eq(StrUtil.isNotBlank(workflowForm.getType()), WorkflowFormDO::getType, workflowForm.getType());
        wrapper.gt(workflowForm.getStartCreateTime() != null, WorkflowFormDO::getCreateTime, workflowForm.getStartCreateTime());
        wrapper.le(workflowForm.getEndCreateTime() != null, WorkflowFormDO::getCreateTime, workflowForm.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(workflowForm.getName()), WorkflowFormDO::getName, workflowForm.getName());
        wrapper.like(StrUtil.isNotBlank(workflowForm.getFormPath()), WorkflowFormDO::getFormPath, workflowForm.getFormPath());
        return wrapper;
    }
}
