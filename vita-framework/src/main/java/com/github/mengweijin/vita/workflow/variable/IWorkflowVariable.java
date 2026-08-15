package com.github.mengweijin.vita.workflow.variable;

import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.workflow.enums.EWorkflowCode;

/**
 * 工作流变量获取接口
 *
 * @author mengweijin
 * @since 2026/8/15
 */
public interface IWorkflowVariable<T extends BaseEntity> {

    /**
     * 支持的流程类型编码
     *
     * @return EWorkflowCode
     */
    EWorkflowCode workflowCode();

    T getByBusinessId(String businessId);

}
