package com.github.mengweijin.vita.workflow.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.workflow.domain.vo.FlowInstanceVO;
import com.github.mengweijin.vita.workflow.service.WarmFlowInstanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.orm.entity.FlowDefinition;
import org.dromara.warm.flow.orm.entity.FlowInstance;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    private final WarmFlowInstanceService warmFlowInstanceService;

    /**
     * Get FlowInstanceVO page by FlowInstance
     *
     * @param pageQuery    pageQuery
     * @param flowInstance {@link FlowDefinition}
     * @return PageQuery<FlowDefinitionVO>
     */
    @SaCheckPermission("workflow:flowInstance:select")
    @GetMapping("/page")
    public PageQuery<FlowInstanceVO> page(PageQuery<FlowInstance> pageQuery, FlowInstance flowInstance) {
        LambdaQueryWrapper<FlowInstance> wrapper = warmFlowInstanceService.buildQueryWrapper(flowInstance);
        return warmFlowInstanceService.pageVo(pageQuery, wrapper);
    }

}

