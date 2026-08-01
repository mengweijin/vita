package com.github.mengweijin.vita.workflow.controller;

import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.workflow.domain.vo.FlowTaskVO;
import com.github.mengweijin.vita.workflow.service.WarmFlowTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.enums.NodeType;
import org.dromara.warm.flow.core.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    private final WarmFlowTaskService warmFlowTaskService;

    /**
     * 待办任务
     *
     * @param pageQuery PageQuery
     * @param vo        FlowTaskVO
     * @return PageQuery
     */
    @GetMapping("/page")
    public PageQuery<FlowTaskVO> page(PageQuery<FlowTaskVO> pageQuery, FlowTaskVO vo) {
        vo.setProcessedBy(LoginHelper.getSessionUserId());
        vo.setNodeType(NodeType.BETWEEN.getKey());
        return warmFlowTaskService.selectPageVo(pageQuery, vo);
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
    @PostMapping("/pass/{taskId}")
    public R<Instance> pass(@PathVariable("taskId") Long taskId,
                            @RequestParam(name = "message", required = false) String message,
                            @RequestBody(required = false) Map<String, Object> variable) {
        Instance instance = taskService.pass(taskId, message, variable);
        return R.ok(instance);
    }

    /**
     * 流程驳回（退回）
     *
     * @param taskId   任务ID
     * @param message  消息
     * @param variable 变量
     * @return R<Instance>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/reject/{taskId}")
    public R<Instance> reject(@PathVariable("taskId") Long taskId,
                              @RequestParam(name = "message", required = false) String message,
                              @RequestBody(required = false) Map<String, Object> variable) {
        Instance instance = taskService.reject(taskId, message, variable);
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
    @PostMapping("/depute/{taskId}")
    public R<Boolean> depute(@PathVariable("taskId") Long taskId, @RequestBody(required = false) FlowParams flowParams) {
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
    @PostMapping("/addSignature/{taskId}")
    public R<Boolean> addSignature(@PathVariable("taskId") Long taskId, @RequestBody(required = false) FlowParams flowParams) {
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
    @PostMapping("/reductionSignature/{taskId}")
    public R<Boolean> reductionSignature(@PathVariable("taskId") Long taskId, @RequestBody(required = false) FlowParams flowParams) {
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
    @PostMapping("/updateHandler/{taskId}")
    public R<Boolean> updateHandler(@PathVariable("taskId") Long taskId, @RequestBody(required = false) FlowParams flowParams) {
        boolean updated = taskService.updateHandler(taskId, flowParams);
        return R.result(updated);
    }
}

