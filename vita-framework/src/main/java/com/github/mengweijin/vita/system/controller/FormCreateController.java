package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.FormCreateBO;
import com.github.mengweijin.vita.system.domain.entity.FormCreateDO;
import com.github.mengweijin.vita.system.domain.vo.FormCreateVO;
import com.github.mengweijin.vita.system.service.FormCreateService;
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
 * 表单管理表 FormCreate Controller
 *
 * @author mengweijin
 * @since 2026-08-20
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/form-create")
public class FormCreateController {

    private static final String LOG_TITLE = "动态表单";

    private FormCreateService formCreateService;

    /**
     * Get FormCreateVO page by FormCreateDO
     *
     * @param page       page
     * @param formCreate {@link FormCreateDO}
     * @return PageQuery<FormCreateVO>
     */
    @SaCheckPermission("system:formCreate:select")
    @GetMapping("/page")
    public PageQuery<FormCreateVO> page(PageQuery<FormCreateDO> page, FormCreateDO formCreate) {
        LambdaQueryWrapper<FormCreateDO> wrapper = formCreateService.buildQueryWrapper(formCreate);
        return formCreateService.pageVo(page, wrapper);
    }

    /**
     * Get FormCreateVO list by FormCreateDO
     *
     * @param formCreate {@link FormCreateDO}
     * @return List<FormCreateVO>
     */
    @SaCheckPermission("system:formCreate:select")
    @GetMapping("/list")
    public List<FormCreateVO> list(FormCreateDO formCreate) {
        return formCreateService.listVo(Wrappers.lambdaQuery(formCreate));
    }

    /**
     * Get FormCreateVO by id
     *
     * @param id id
     * @return FormCreateVO
     */
    @GetMapping("/{id}")
    public FormCreateVO getById(@PathVariable("id") Long id) {
        return formCreateService.getVoById(id);
    }

    @GetMapping("/query/by/code/{code}")
    public FormCreateVO getVoByCode(@PathVariable("code") String code) {
        return formCreateService.getVoByCode(code);
    }

    /**
     * Add FormCreate
     *
     * @param formCreate {@link FormCreateDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:formCreate:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody FormCreateBO formCreate) {
        boolean bool = formCreateService.save(formCreate);
        return R.result(bool);
    }

    /**
     * Update FormCreate
     *
     * @param formCreate {@link FormCreateBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:formCreate:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody FormCreateBO formCreate) {
        boolean bool = formCreateService.updateById(formCreate);
        return R.result(bool);
    }

    /**
     * Remove FormCreate by id(s), Multiple ids can be separated by commas ",".
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:formCreate:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        boolean bool = formCreateService.removeByIds(Arrays.asList(ids));
        return R.result(bool);
    }

}

