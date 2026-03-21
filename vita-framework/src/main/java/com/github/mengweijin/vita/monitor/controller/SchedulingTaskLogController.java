package com.github.mengweijin.vita.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.monitor.domain.bo.SchedulingTaskLogBO;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskLogDO;
import com.github.mengweijin.vita.monitor.domain.vo.SchedulingTaskLogVO;
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
    public PageQuery<SchedulingTaskLogVO> page(PageQuery<SchedulingTaskLogDO> page, SchedulingTaskLogDO schedulingTaskLog) {
        LambdaQueryWrapper<SchedulingTaskLogDO> wrapper = schedulingTaskLogService.buildQueryWrapper(schedulingTaskLog);
        wrapper.orderByDesc(SchedulingTaskLogDO::getCreateTime);
        return schedulingTaskLogService.pageVo(page, wrapper);
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
    public List<SchedulingTaskLogVO> list(SchedulingTaskLogDO schedulingTaskLog) {
        return schedulingTaskLogService.listVo(Wrappers.lambdaQuery(schedulingTaskLog));
    }

    /**
     * <p>
     * Get LogLogin by id
     * </p>
     * @param id id
     * @return SchedulingTaskLogDO
     */
    @GetMapping("/{id}")
    public SchedulingTaskLogVO getById(@PathVariable("id") Long id) {
        return schedulingTaskLogService.getVoById(id);
    }

    /**
     * <p>
     * Add SchedulingTaskLogDO
     * </p>
     * @param bo {@link SchedulingTaskLogBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("monitor:schedulingTaskLog:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody SchedulingTaskLogBO bo) {
        boolean bool = schedulingTaskLogService.saveByBo(bo);
        return R.result(bool);
    }

    /**
     * <p>
     * Update SchedulingTaskLogDO
     * </p>
     * @param bo {@link SchedulingTaskLogBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("monitor:schedulingTaskLog:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody SchedulingTaskLogBO bo) {
        boolean bool = schedulingTaskLogService.updateByBoById(bo);
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
