package com.github.mengweijin.vita.workflow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.v7.core.collection.CollUtil;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.workflow.domain.vo.FlowInstanceVO;
import com.github.mengweijin.vita.workflow.service.WarmFlowInstanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.entity.Task;
import org.dromara.warm.flow.core.service.InsService;
import org.dromara.warm.flow.core.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 流程实例表 Flow Instance Controller
 * <p>
 *
 * @author mengweijin
 * @since 2026-05-30
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/workflow/instance")
public class WarmFlowInstanceController {

    private static final String LOG_TITLE = "流程实例";

    private final InsService insService;

    private final TaskService taskService;

    private final WarmFlowInstanceService warmFlowInstanceService;

    /**
     * 所有流程实例分页查询
     *
     * @param pageQuery PageQuery<Instance>
     * @param vo        vo
     * @return PageQuery
     */

    @SaCheckPermission("workflow:instance:select")
    @GetMapping("/page")
    public PageQuery<FlowInstanceVO> page(PageQuery<FlowInstanceVO> pageQuery, FlowInstanceVO vo) {
        return warmFlowInstanceService.selectPageVo(pageQuery, vo);
    }

    /**
     * 我的流程分页查询
     *
     * @param pageQuery PageQuery<Instance>
     * @param vo        vo
     * @return PageQuery
     */
    @GetMapping("/page/myFlow")
    public PageQuery<FlowInstanceVO> pageMyFlow(PageQuery<FlowInstanceVO> pageQuery, FlowInstanceVO vo) {
        Long userId = LoginHelper.getSessionUserId();
        vo.setCreateBy(userId);
        return this.page(pageQuery, vo);
    }

    @GetMapping("/query/{id}")
    public FlowInstanceVO queryById(@PathVariable("id") Long id) {
        return warmFlowInstanceService.getVoById(id);
    }

    /**
     * 提交流程
     *
     * @param instanceId 流程实例 ID
     * @return R<Instance>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/submit/{instanceId}")
    public R<Instance> submit(@PathVariable("instanceId") Long instanceId) {
        List<Task> taskList = taskService.getByInsId(instanceId);
        Task first = CollUtil.getFirst(taskList);
        Instance instance = taskService.pass(first.getId(), Const.EMPTY, null);
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
    @PostMapping("/revoke/{instanceId}")
    public R<Instance> revoke(@PathVariable("instanceId") Long instanceId,
                              @RequestBody(required = false) FlowParams flowParams) {
        Instance instance = taskService.revoke(instanceId, flowParams);
        return R.ok(instance);
    }

    /**
     * 终止流程
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("workflow:instance:termination")
    @PostMapping("/termination/{id}")
    public R<Instance> termination(@PathVariable Long id, @RequestBody FlowParams flowParams) {
        if (LoginHelper.isAdmin()) {
            flowParams.ignore(true);
        }
        Instance instance = taskService.terminationByInsId(id, flowParams);
        return R.ok(instance);
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("workflow:instance:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable Long[] ids) {
        boolean removed = insService.remove(Arrays.asList(ids));
        return R.result(removed);
    }
}

