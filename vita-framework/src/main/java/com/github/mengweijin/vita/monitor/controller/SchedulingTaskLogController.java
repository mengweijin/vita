package com.github.mengweijin.vita.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskLogDO;
import com.github.mengweijin.vita.monitor.service.SchedulingTaskLogService;
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
 *  SchedulingTaskLog Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/monitor/scheduling-task-log")
public class SchedulingTaskLogController {

    private static final String LOG_TITLE = "调度任务日志";

    private SchedulingTaskLogService schedulingTaskLogService;

    /**
     * <p>
     * Get SchedulingTaskLogDO page by SchedulingTaskLogDO
     * </p>
     * @param page page
     * @param schedulingTaskLog {@link SchedulingTaskLogDO}
     * @return Page<SchedulingTaskLogDO>
     */
    @SaCheckPermission("monitor:schedulingTaskLog:select")
    @GetMapping("/page")
    public IPage<SchedulingTaskLogDO> page(Page<SchedulingTaskLogDO> page, SchedulingTaskLogDO schedulingTaskLog) {
        LambdaQueryWrapper<SchedulingTaskLogDO> wrapper = schedulingTaskLogService.getQueryWrapper(schedulingTaskLog);
        wrapper.orderByDesc(SchedulingTaskLogDO::getCreateTime);
        return schedulingTaskLogService.page(page, wrapper);
    }

    /**
     * <p>
     * Get SchedulingTaskLogDO list by SchedulingTaskLogDO
     * </p>
     * @param schedulingTaskLog {@link SchedulingTaskLogDO}
     * @return List<SchedulingTaskLogDO>
     */
    @SaCheckPermission("monitor:schedulingTaskLog:select")
    @GetMapping("/list")
    public List<SchedulingTaskLogDO> list(SchedulingTaskLogDO schedulingTaskLog) {
        return schedulingTaskLogService.list(new LambdaQueryWrapper<>(schedulingTaskLog));
    }

    /**
     * <p>
     * Get LogLogin by id
     * </p>
     * @param id id
     * @return SchedulingTaskLogDO
     */
    @SaCheckPermission("monitor:schedulingTaskLog:select")
    @GetMapping("/{id}")
    public SchedulingTaskLogDO getById(@PathVariable("id") Long id) {
        return schedulingTaskLogService.getById(id);
    }

    /**
     * <p>
     * Add SchedulingTaskLogDO
     * </p>
     * @param schedulingTaskLogDO {@link SchedulingTaskLogDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("monitor:schedulingTaskLog:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody SchedulingTaskLogDO schedulingTaskLogDO) {
        boolean bool = schedulingTaskLogService.save(schedulingTaskLogDO);
        return R.result(bool);
    }

    /**
     * <p>
     * Update SchedulingTaskLogDO
     * </p>
     * @param schedulingTaskLogDO {@link SchedulingTaskLogDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("monitor:schedulingTaskLog:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody SchedulingTaskLogDO schedulingTaskLogDO) {
        boolean bool = schedulingTaskLogService.updateById(schedulingTaskLogDO);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete SchedulingTaskLogDO by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("monitor:schedulingTaskLog:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        int i = schedulingTaskLogService.getBaseMapper().deleteByIds(Arrays.asList(ids));
        return R.result(i);
    }

}

