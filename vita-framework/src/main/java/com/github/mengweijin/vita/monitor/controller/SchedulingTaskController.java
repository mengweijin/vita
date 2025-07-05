package com.github.mengweijin.vita.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskService;
import jakarta.validation.Valid;
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
 *  SchedulingTask Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/monitor/scheduling-task")
public class SchedulingTaskController {

    private static final String LOG_TITLE = "调度任务";

    private SchedulingTaskService schedulingTaskService;

    /**
     * <p>
     * Get SchedulingTask page by SchedulingTask
     * </p>
     * @param page page
     * @param schedulingTask {@link SchedulingTaskDO}
     * @return Page<SchedulingTaskDO>
     */
    @SaCheckPermission("monitor:schedulingTask:select")
    @GetMapping("/page")
    public IPage<SchedulingTaskDO> page(Page<SchedulingTaskDO> page, SchedulingTaskDO schedulingTask) {
        LambdaQueryWrapper<SchedulingTaskDO> wrapper = schedulingTaskService.getQueryWrapper(schedulingTask);
        wrapper.orderByDesc(SchedulingTaskDO::getCreateTime);
        return schedulingTaskService.page(page, wrapper);
    }

    /**
     * <p>
     * Get SchedulingTaskDO list by SchedulingTaskDO
     * </p>
     * @param schedulingTask {@link SchedulingTaskDO}
     * @return List<SchedulingTaskDO>
     */
    @SaCheckPermission("monitor:schedulingTask:select")
    @GetMapping("/list")
    public List<SchedulingTaskDO> list(SchedulingTaskDO schedulingTask) {
        return schedulingTaskService.list(new LambdaQueryWrapper<>(schedulingTask));
    }

    /**
     * <p>
     * Get LogLogin by id
     * </p>
     * @param id id
     * @return LogLogin
     */
    @SaCheckPermission("monitor:schedulingTask:select")
    @GetMapping("/{id}")
    public SchedulingTaskDO getById(@PathVariable("id") Long id) {
        return schedulingTaskService.getById(id);
    }

    /**
     * <p>
     * Add SchedulingTaskDO
     * </p>
     * @param schedulingTaskDO {@link SchedulingTaskDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("monitor:schedulingTask:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody SchedulingTaskDO schedulingTaskDO) {
        boolean bool = schedulingTaskService.save(schedulingTaskDO);
        return R.result(bool);
    }

    /**
     * <p>
     * Update SchedulingTaskDO
     * </p>
     * @param schedulingTaskDO {@link SchedulingTaskDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("monitor:schedulingTask:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody SchedulingTaskDO schedulingTaskDO) {
        boolean bool = schedulingTaskService.updateById(schedulingTaskDO);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete SchedulingTaskDO by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("monitor:schedulingTask:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        int i = schedulingTaskService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.result(i);
    }

}

