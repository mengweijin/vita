package com.github.mengweijin.vita.workflow.controller;

import com.github.mengweijin.vita.framework.constant.SqlConst;
import com.github.mengweijin.vita.framework.constant.VitaConst;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.HisTask;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.entity.Task;
import org.dromara.warm.flow.core.enums.FlowStatus;
import org.dromara.warm.flow.core.service.HisTaskService;
import org.dromara.warm.flow.core.service.TaskService;
import org.dromara.warm.flow.core.utils.page.Page;
import org.dromara.warm.flow.orm.entity.FlowHisTask;
import org.dromara.warm.flow.orm.entity.FlowTask;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 流程任务表 Flow Task Controller
 * <p>
 *
 * @author mengweijin
 * @since 2026-05-30
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/workflow/task")
public class WarmFlowTaskController {

    private static final String LOG_TITLE = "流程任务";

    private final TaskService taskService;

    private final HisTaskService hisTaskService;

    /**
     * 待办任务
     *
     * @param pageQuery PageQuery
     * @param task      task
     * @return PageQuery
     */
    @GetMapping("/page/backlog")
    public PageQuery<Task> pageBacklog(PageQuery<Task> pageQuery, FlowTask task) {
        Long userId = LoginHelper.getSessionUserId();
        task.setCreateBy(userId.toString());
        task.setFlowStatus(FlowStatus.APPROVAL.getKey());

        Page<Task> warmFlowPage = pageQuery.toWarmFlowPage()
                .setOrderBy(VitaConst.COLUMN_CREATE_TIME)
                .setIsAsc(SqlConst.ASC);
        Page<Task> page = taskService.page(task, warmFlowPage);
        return PageQuery.of(page);
    }

    /**
     * 已办任务
     *
     * @param pageQuery PageQuery
     * @param hisTask   HisTask
     * @return PageQuery
     */
    @GetMapping("/page/done")
    public PageQuery<HisTask> pageDone(PageQuery<HisTask> pageQuery, FlowHisTask hisTask) {
        Long userId = LoginHelper.getSessionUserId();
        hisTask.setCreateBy(userId.toString());
        hisTask.setFlowStatus(FlowStatus.PASS.getKey());

        Page<HisTask> warmFlowPage = pageQuery.toWarmFlowPage()
                .setOrderBy(VitaConst.COLUMN_CREATE_TIME)
                .setIsAsc(SqlConst.DESC);
        Page<HisTask> page = hisTaskService.page(hisTask, warmFlowPage);
        return PageQuery.of(page);
    }

    /**
     * 流程通过
     *
     * @param taskId   任务ID
     * @param message  消息
     * @param variable 变量
     * @return R<Instance>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/pass")
    public R<Instance> pass(@RequestParam(name = "taskId") Long taskId,
                            @RequestParam(name = "message", required = false) String message,
                            @RequestParam(required = false) Map<String, Object> variable) {
        Instance instance = taskService.pass(taskId, message, variable);
        return R.ok(instance);
    }

    /**
     * 流程驳回
     *
     * @param taskId   任务ID
     * @param message  消息
     * @param variable 变量
     * @return R<Instance>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/reject")
    public R<Instance> reject(@RequestParam(name = "taskId") Long taskId,
                              @RequestParam(name = "message", required = false) String message,
                              @RequestParam(required = false) Map<String, Object> variable) {
        Instance instance = taskService.reject(taskId, message, variable);
        return R.ok(instance);
    }

    /**
     * 流程撤销
     *
     * @param instanceId 实例id
     * @param flowParams 参数
     * @return R<Instance>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/revoke")
    public R<Instance> revoke(@RequestParam(name = "instanceId") Long instanceId,
                              @RequestParam(required = false) FlowParams flowParams) {
        Instance instance = taskService.revoke(instanceId, flowParams);
        return R.ok(instance);
    }

    /**
     * 流程委派
     *
     * @param taskId     任务id
     * @param flowParams 参数
     * @return R<Boolean>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/depute")
    public R<Boolean> depute(@RequestParam(name = "taskId") Long taskId,
                             @RequestParam(required = false) FlowParams flowParams) {
        boolean deputed = taskService.depute(taskId, flowParams);
        return R.result(deputed);
    }

    /**
     * 流程加签
     *
     * @param taskId     任务id
     * @param flowParams 参数
     * @return R<Boolean>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/addSignature")
    public R<Boolean> addSignature(@RequestParam(name = "taskId") Long taskId,
                                   @RequestParam(required = false) FlowParams flowParams) {
        boolean added = taskService.addSignature(taskId, flowParams);
        return R.result(added);
    }

    /**
     * 流程减签
     *
     * @param taskId     任务id
     * @param flowParams 参数
     * @return R<Boolean>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/reductionSignature")
    public R<Boolean> reductionSignature(@RequestParam(name = "taskId") Long taskId,
                                         @RequestParam(required = false) FlowParams flowParams) {
        boolean reduced = taskService.reductionSignature(taskId, flowParams);
        return R.result(reduced);
    }

    /**
     * 修改办理人
     *
     * @param taskId     任务id
     * @param flowParams 参数
     * @return R<Boolean>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/updateHandler")
    public R<Boolean> updateHandler(@RequestParam(name = "taskId") Long taskId,
                                    @RequestParam(required = false) FlowParams flowParams) {
        boolean updated = taskService.updateHandler(taskId, flowParams);
        return R.result(updated);
    }
}

