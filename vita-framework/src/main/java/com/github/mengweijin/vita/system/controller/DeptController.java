package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.DeptDO;
import com.github.mengweijin.vita.system.service.DeptService;
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
 * <p>
 *  Dept Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/dept")
public class DeptController {

    private static final String LOG_TITLE = "部门管理";

    private DeptService deptService;

    /**
     * <p>
     * Get Dept list by Dept
     * </p>
     * @param dept {@link DeptDO}
     * @return List<Dept>
     */
    @SaCheckPermission("system:dept:select")
    @GetMapping("/list")
    public List<DeptDO> list(DeptDO dept) {
        LambdaQueryWrapper<DeptDO> wrapper = deptService.getQueryWrapper(dept);
        return deptService.list(wrapper.orderByAsc(DeptDO::getSeq));
    }

    /**
     * <p>
     * Get Dept by id
     * </p>
     * @param id id
     * @return Dept
     */
    @SaCheckPermission("system:dept:select")
    @GetMapping("/{id}")
    public DeptDO getById(@PathVariable("id") Long id) {
        return deptService.getById(id);
    }

    /**
     * <p>
     * Add Dept
     * </p>
     * @param dept {@link DeptDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:dept:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody DeptDO dept) {
        boolean bool = deptService.save(dept);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Dept
     * </p>
     * @param dept {@link DeptDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:dept:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody DeptDO dept) {
        boolean bool = deptService.updateById(dept);
        return R.result(bool);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:dept:update")
    @PostMapping("/setDisabled/{id}/{disabled}")
    public R<Void> setDisabledValue(@PathVariable("id") Long id, @PathVariable("disabled") String disabled) {
        boolean bool = deptService.setDisabled(id, disabled);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Dept by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:dept:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(deptService.removeByIds(Arrays.asList(ids)));
    }

}

