package com.github.mengweijin.vita.oa.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.oa.domain.bo.EmployeeLeaveBO;
import com.github.mengweijin.vita.oa.domain.entity.EmployeeLeaveDO;
import com.github.mengweijin.vita.oa.domain.vo.EmployeeLeaveVO;
import com.github.mengweijin.vita.oa.service.EmployeeLeaveService;
import com.github.mengweijin.vita.workflow.enums.EWorkflowCode;
import com.github.mengweijin.vita.workflow.service.WarmFlowInstanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.entity.Instance;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 员工请假申请表 LeaveApply Controller
 *
 * @author mengweijin
 * @since 2026-05-16
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/oa/employee-leave")
public class EmployeeLeaveController {

    private static final String LOG_TITLE = "员工请假";

    private final EmployeeLeaveService employeeLeaveService;

    private final WarmFlowInstanceService warmFlowInstanceService;

    /**
     * Get LeaveApplyVO page by LeaveApplyDO
     *
     * @param page       page
     * @param leaveApply {@link EmployeeLeaveDO}
     * @return PageQuery<LeaveApplyVO>
     */
    @GetMapping("/page")
    public PageQuery<EmployeeLeaveVO> page(PageQuery<EmployeeLeaveDO> page, EmployeeLeaveDO leaveApply) {
        LambdaQueryWrapper<EmployeeLeaveDO> wrapper = employeeLeaveService.buildQueryWrapper(leaveApply);
        return employeeLeaveService.pageVo(page, wrapper);
    }

    /**
     * Get LeaveApplyVO list by LeaveApplyDO
     *
     * @param leaveApply {@link EmployeeLeaveDO}
     * @return List<LeaveApplyVO>
     */
    @GetMapping("/list")
    public List<EmployeeLeaveVO> list(EmployeeLeaveDO leaveApply) {
        return employeeLeaveService.listVo(Wrappers.lambdaQuery(leaveApply));
    }

    /**
     * Get LeaveApplyVO by id
     *
     * @param id id
     * @return LeaveApplyVO
     */
    @GetMapping("/{id}")
    public EmployeeLeaveVO getById(@PathVariable("id") Long id) {
        return employeeLeaveService.getVoById(id);
    }

    /**
     * Add LeaveApply
     *
     * @param leaveApply {@link EmployeeLeaveDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody EmployeeLeaveBO leaveApply) {
        boolean bool = employeeLeaveService.save(leaveApply);
        return R.result(bool);
    }

    /**
     * Update LeaveApply
     *
     * @param leaveApply {@link EmployeeLeaveBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody EmployeeLeaveBO leaveApply) {
        boolean bool = employeeLeaveService.updateById(leaveApply);
        return R.result(bool);
    }

    /**
     * Remove LeaveApply by id(s), Multiple ids can be separated by commas ",".
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        boolean bool = employeeLeaveService.removeByIds(Arrays.asList(ids));
        return R.result(bool);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/saveWorkflow")
    public R<Instance> saveWorkflow(@Validated @RequestBody EmployeeLeaveBO bo) {
        boolean bool = employeeLeaveService.saveOrUpdate(bo);
        if (bool) {
            String flowCode = EWorkflowCode.EMPLOYEE_LEAVE.getValue();
            Instance instance = warmFlowInstanceService.start(flowCode, bo.getId());
            return R.ok(instance);
        }
        return R.fail("Save leave apply data failed!");
    }

}

