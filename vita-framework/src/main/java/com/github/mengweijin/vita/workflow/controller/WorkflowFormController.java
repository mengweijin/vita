package com.github.mengweijin.vita.workflow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.workflow.domain.bo.WorkflowFormBO;
import com.github.mengweijin.vita.workflow.domain.entity.WorkflowFormDO;
import com.github.mengweijin.vita.workflow.domain.vo.WorkflowFormVO;
import com.github.mengweijin.vita.workflow.service.WorkflowFormService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * 流程表单表 WorkflowForm Controller
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/workflow/workflow-form")
public class WorkflowFormController {

    private static final String LOG_TITLE = "流程表单";

    private WorkflowFormService workflowFormService;

    /**
     * Get WorkflowFormVO page by WorkflowFormDO
     * @param page page
     * @param workflowForm {@link WorkflowFormDO}
     * @return PageQuery<WorkflowFormVO>
     */
    @SaCheckPermission("workflow:workflowForm:select")
    @GetMapping("/page")
    public PageQuery<WorkflowFormVO> page(PageQuery<WorkflowFormDO> page, WorkflowFormDO workflowForm) {
        LambdaQueryWrapper<WorkflowFormDO> wrapper = workflowFormService.buildQueryWrapper(workflowForm);
        return workflowFormService.pageVo(page, wrapper);
    }

    /**
     * Get WorkflowFormVO list by WorkflowFormDO
     * @param workflowForm {@link WorkflowFormDO}
     * @return List<WorkflowFormVO>
     */
    @SaCheckPermission("workflow:workflowForm:select")
    @GetMapping("/list")
    public List<WorkflowFormVO> list(WorkflowFormDO workflowForm) {
        return workflowFormService.listVo(Wrappers.lambdaQuery(workflowForm));
    }

    /**
     * Get WorkflowFormVO by id
     * @param id id
     * @return WorkflowFormVO
     */
    @GetMapping("/{id}")
    public WorkflowFormVO getById(@PathVariable("id") Long id) {
        return workflowFormService.getVoById(id);
    }

    /**
     * Add WorkflowForm
     * @param workflowForm {@link WorkflowFormDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("workflow:workflowForm:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody WorkflowFormBO workflowForm) {
        boolean bool = workflowFormService.saveByBo(workflowForm);
        return R.result(bool);
    }

    /**
     * Update WorkflowForm
     * @param workflowForm {@link WorkflowFormBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("workflow:workflowForm:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody WorkflowFormBO workflowForm) {
        boolean bool = workflowFormService.updateByBoById(workflowForm);
        return R.result(bool);
    }

    /**
     * Remove WorkflowForm by id(s), Multiple ids can be separated by commas ",".
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("workflow:workflowForm:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        boolean bool = workflowFormService.removeByIds(Arrays.asList(ids));
        return R.result(bool);
    }

}

