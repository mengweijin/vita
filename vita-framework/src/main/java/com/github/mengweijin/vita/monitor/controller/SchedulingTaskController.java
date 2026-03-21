package com.github.mengweijin.vita.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.monitor.domain.bo.SchedulingTaskBO;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.domain.vo.SchedulingTaskVO;
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
import java.util.Set;

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
    public PageQuery<SchedulingTaskVO> page(PageQuery<SchedulingTaskDO> page, SchedulingTaskDO schedulingTask) {
        LambdaQueryWrapper<SchedulingTaskDO> wrapper = schedulingTaskService.buildQueryWrapper(schedulingTask);
        wrapper.orderByDesc(SchedulingTaskDO::getCreateTime);
        return schedulingTaskService.pageVo(page, wrapper);
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
    public List<SchedulingTaskVO> list(SchedulingTaskDO schedulingTask) {
        return schedulingTaskService.listVo(Wrappers.lambdaQuery(schedulingTask));
    }

    /**
     * <p>
     * Get LogLogin by id
     * </p>
     * @param id id
     * @return LogLogin
     */
    @GetMapping("/{id}")
    public SchedulingTaskVO getById(@PathVariable("id") Long id) {
        return schedulingTaskService.getVoById(id);
    }

    @GetMapping("/query/taskBeanNames")
    public Set<String> queryTaskBeanNames() {
        return schedulingTaskService.getTaskBeanNames();
    }

    /**
     * <p>
     * Add SchedulingTaskDO
     * </p>
     * @param bo {@link SchedulingTaskBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("monitor:schedulingTask:create")
    @PostMapping("/create")
    public R<Void> create(@Valid @RequestBody SchedulingTaskBO bo) {
        boolean bool = schedulingTaskService.saveByBo(bo);
        return R.result(bool);
    }

    /**
     * <p>
     * Update SchedulingTaskDO
     * </p>
     * @param bo {@link SchedulingTaskBO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("monitor:schedulingTask:update")
    @PostMapping("/update")
    public R<Void> update(@Valid @RequestBody SchedulingTaskBO bo) {
        boolean bool = schedulingTaskService.updateByBoById(bo);
        return R.result(bool);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.DISABLE)
    @SaCheckPermission("monitor:schedulingTask:update")
    @PostMapping("/disable/{id}")
    public R<Void> disable(@PathVariable("id") Long id) {
        schedulingTaskService.disableById(id);
        return R.ok();
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.ENABLE)
    @SaCheckPermission("monitor:schedulingTask:update")
    @PostMapping("/enable/{id}")
    public R<Void> enable(@PathVariable("id") Long id) {
        schedulingTaskService.enableById(id);
        return R.ok();
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

    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @SaCheckPermission("monitor:schedulingTask:run")
    @PostMapping("/run/{id}")
    public R<Void> run(@PathVariable("id") Long id) {
        schedulingTaskService.run(id);
        return R.ok();
    }
}

