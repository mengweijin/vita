package com.github.mengweijin.vita.workflow.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author mengweijin
 * @since 2023/5/20
 */
@Getter
@AllArgsConstructor
public enum EWorkflowCode implements IEnum<String> {

    // region ----- <人事流程>
    /**
     * 员工请假流程
     */
    EMPLOYEE_LEAVE("employee_leave"),
    /**
     * 员工入职流程
     */
    EMPLOYEE_ONBOARDING("employee_leave"),
    /**
     * 员工转正流程
     */
    EMPLOYEE_PROBATION_CONFIRMATION("employee_leave"),
    /**
     * 员工调岗流程
     */
    EMPLOYEE_TRANSFER("employee_leave"),
    /**
     * 员工离职流程
     */
    EMPLOYEE_RESIGNATION("employee_resignation"),
    // endregion

    // region ----- <财务流程>
    /**
     * 报销流程
     */
    EXPENSE_REIMBURSEMENT("expense_reimbursement"),
    /**
     * 预支经费申请流程
     */
    FUND_ADVANCE_APPLICATION("fund_advance_application"),
    // endregion
    ;

    private final String value;

}
