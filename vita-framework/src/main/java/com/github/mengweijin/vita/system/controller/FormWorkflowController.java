package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.FormWorkflowBO;
import com.github.mengweijin.vita.system.domain.entity.FormWorkflowDO;
import com.github.mengweijin.vita.system.domain.vo.FormWorkflowVO;
import com.github.mengweijin.vita.system.service.FormWorkflowService;
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
 * 流程表单表 Form Controller
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/form-workflow")
public class FormWorkflowController {

    private static final String LOG_TITLE = "表单管理";

    private FormWorkflowService formWorkflowService;

    @SaCheckPermission("system:formWorkflow:select")
    @GetMapping("/page")
    public PageQuery<FormWorkflowVO> pageRootTree(PageQuery<FormWorkflowDO> page, FormWorkflowDO form) {
        LambdaQueryWrapper<FormWorkflowDO> wrapper = formWorkflowService.buildQueryWrapper(form);
        return formWorkflowService.pageVo(page, wrapper);
    }

    /**
     * Get FormVO list by FormDO
     *
     * @param form {@link FormWorkflowDO}
     * @return List<FormVO>
     */
    @SaCheckPermission("system:formWorkflow:select")
    @GetMapping("/list")
    public List<FormWorkflowVO> list(FormWorkflowDO form) {
        return formWorkflowService.listVo(Wrappers.lambdaQuery(form));
    }

    /**
     * Get FormVO by id
     *
     * @param id id
     * @return FormVO
     */
    @GetMapping("/{id}")
    public FormWorkflowVO getById(@PathVariable("id") Long id) {
        return formWorkflowService.getVoById(id);
    }

    /**
     * Add form
     *
     * @param form {@link FormWorkflowDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:formWorkflow:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody FormWorkflowBO form) {
        boolean bool = formWorkflowService.save(form);
        return R.result(bool);
    }

    /**
     * Update form
     *
     * @param form {@link FormWorkflowBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:formWorkflow:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody FormWorkflowBO form) {
        boolean bool = formWorkflowService.updateById(form);
        return R.result(bool);
    }

    /**
     * Remove Form by id(s), Multiple ids can be separated by commas ",".
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:formWorkflow:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        boolean bool = formWorkflowService.removeByIds(Arrays.asList(ids));
        return R.result(bool);
    }

}

