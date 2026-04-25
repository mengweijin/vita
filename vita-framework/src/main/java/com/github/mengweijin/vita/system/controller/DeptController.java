package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.DeptBO;
import com.github.mengweijin.vita.system.domain.entity.DeptDO;
import com.github.mengweijin.vita.system.domain.vo.DeptVO;
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
 * Dept Controller
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
     *
     * @param dept {@link DeptDO}
     * @return List<Dept>
     */
    @SaCheckPermission("system:dept:select")
    @GetMapping("/list")
    public List<DeptVO> list(DeptDO dept) {
        LambdaQueryWrapper<DeptDO> wrapper = deptService.buildQueryWrapper(dept);
        return deptService.listVo(wrapper.orderByAsc(DeptDO::getSeq));
    }

    /**
     * <p>
     * Get Dept by id
     * </p>
     *
     * @param id id
     * @return Dept
     */
    @GetMapping("/{id}")
    public DeptVO getById(@PathVariable("id") Long id) {
        return deptService.getVoById(id);
    }

    /**
     * <p>
     * Add Dept
     * </p>
     *
     * @param bo {@link DeptDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:dept:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody DeptBO bo) {
        boolean bool = deptService.saveByBo(bo);
        return R.result(bool);
    }

    /**
     * <p>
     * Update Dept
     * </p>
     *
     * @param bo {@link DeptBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:dept:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody DeptBO bo) {
        boolean bool = deptService.updateByBoById(bo);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete Dept by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:dept:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(deptService.removeByIds(Arrays.asList(ids)));
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.ENABLE)
    @SaCheckPermission("system:dept:update")
    @PostMapping("/enable/{id}")
    public R<Void> enable(@PathVariable("id") Long id) {
        boolean bool = deptService.enable(id);
        return R.result(bool);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.DISABLE)
    @SaCheckPermission("system:dept:update")
    @PostMapping("/disable/{id}")
    public R<Void> disable(@PathVariable("id") Long id) {
        boolean bool = deptService.disable(id);
        return R.result(bool);
    }
}

