package com.github.mengweijin.vita.workflow.variable.impl;

import com.github.mengweijin.vita.oa.domain.entity.EmployeeLeaveDO;
import com.github.mengweijin.vita.oa.service.EmployeeLeaveService;
import com.github.mengweijin.vita.workflow.enums.EWorkflowCode;
import com.github.mengweijin.vita.workflow.variable.IWorkflowVariable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 *
 * @author mengweijin
 * @since 2026/8/14
 */
@Component
@AllArgsConstructor
public class EmployeeLeaveWorkflowVariable implements IWorkflowVariable<EmployeeLeaveDO> {

    private final EmployeeLeaveService employeeLeaveService;

    @Override
    public EWorkflowCode workflowCode() {
        return EWorkflowCode.EMPLOYEE_LEAVE;
    }

    @Override
    public EmployeeLeaveDO getByBusinessId(String businessId) {
        return employeeLeaveService.getById(businessId);
    }


}
