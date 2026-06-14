package com.github.mengweijin.vita.workflow.controller;

import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.enums.FlowStatus;
import org.dromara.warm.flow.core.service.InsService;
import org.dromara.warm.flow.core.utils.page.Page;
import org.dromara.warm.flow.orm.entity.FlowInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 所有流程实例分页查询
     *
     * @param pageQuery PageQuery<Instance>
     * @param instance  Instance
     * @return PageQuery
     */
    @GetMapping("/page")
    public PageQuery<Instance> page(PageQuery<Instance> pageQuery, FlowInstance instance) {
        Page<Instance> page = insService.page(instance, pageQuery.toWarmFlowPage());
        return PageQuery.of(page);
    }

    /**
     * 我的流程分页查询
     *
     * @param pageQuery PageQuery<Instance>
     * @param instance  Instance
     * @return PageQuery<Instance>
     */
    @GetMapping("/page/myFlow")
    public PageQuery<Instance> pageMyFlow(PageQuery<Instance> pageQuery, FlowInstance instance) {
        Long userId = LoginHelper.getSessionUserId();
        instance.setCreateBy(userId.toString());
        Page<Instance> page = insService.page(instance, pageQuery.toWarmFlowPage());
        return PageQuery.of(page);
    }

    /**
     * 草稿流程
     *
     * @param businessId 业务ID
     * @param params     流程参数
     * @return R<Instance>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/draft")
    public R<Instance> draft(@RequestParam("businessId") String businessId, FlowParams params) {
        params.flowStatus(FlowStatus.TOBESUBMIT.getKey());
        Instance instance = insService.start(businessId, params);
        return R.ok(instance);
    }

    /**
     * 启动流程
     *
     * @param businessId 业务ID
     * @param params     流程参数
     * @return R<Instance>
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.OTHER)
    @PostMapping("/start")
    public R<Instance> start(@RequestParam("businessId") String businessId, FlowParams params) {
        Instance instance = insService.start(businessId, params);
        return R.ok(instance);
    }
}

