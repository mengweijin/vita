package com.github.mengweijin.vita.form.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.form.domain.bo.FormBO;
import com.github.mengweijin.vita.form.domain.entity.FormDO;
import com.github.mengweijin.vita.form.domain.vo.FormVO;
import com.github.mengweijin.vita.form.service.FormService;
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
@RequestMapping("/form/manage")
public class FormController {

    private static final String LOG_TITLE = "表单管理";

    private FormService formService;

    /**
     * Get FormVO page by FormDO
     * @param page page
     * @param form {@link FormDO}
     * @return PageQuery<FormVO>
     */
    @SaCheckPermission("form:manage:select")
    @GetMapping("/page")
    public PageQuery<FormVO> page(PageQuery<FormDO> page, FormDO form) {
        LambdaQueryWrapper<FormDO> wrapper = formService.buildQueryWrapper(form);
        return formService.pageVo(page, wrapper);
    }

    @SaCheckPermission("form:manage:select")
    @GetMapping("/page/root")
    public PageQuery<FormVO> pageRootNode(PageQuery<FormDO> page, FormDO form) {
        LambdaQueryWrapper<FormDO> wrapper = formService.buildRootQueryWrapper(form);
        return formService.pageVo(page, wrapper);
    }

    @SaCheckPermission("form:manage:select")
    @GetMapping("/list/children/by/parentId/{parentId}")
    public List<FormVO> listChildrenByParentId(@PathVariable("parentId") Long parentId) {
        return formService.listChildrenByParentId(parentId);
    }

    /**
     * Get FormVO list by FormDO
     * @param form {@link FormDO}
     * @return List<FormVO>
     */
    @SaCheckPermission("form:manage:select")
    @GetMapping("/list")
    public List<FormVO> list(FormDO form) {
        return formService.listVo(Wrappers.lambdaQuery(form));
    }

    /**
     * Get FormVO by id
     * @param id id
     * @return FormVO
     */
    @GetMapping("/{id}")
    public FormVO getById(@PathVariable("id") Long id) {
        return formService.getVoById(id);
    }

    /**
     * Add form
     * @param form {@link FormDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("form:manage:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody FormBO form) {
        boolean bool = formService.saveByBo(form);
        return R.result(bool);
    }

    /**
     * Update form
     * @param form {@link FormBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("form:manage:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody FormBO form) {
        boolean bool = formService.updateByBoById(form);
        return R.result(bool);
    }

    /**
     * Remove Form by id(s), Multiple ids can be separated by commas ",".
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("form:manage:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        boolean bool = formService.removeByIds(Arrays.asList(ids));
        return R.result(bool);
    }

}

