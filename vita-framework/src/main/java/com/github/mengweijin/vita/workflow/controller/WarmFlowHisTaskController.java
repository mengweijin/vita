package com.github.mengweijin.vita.workflow.controller;

import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.workflow.domain.vo.FlowHisTaskVO;
import com.github.mengweijin.vita.workflow.service.WarmFlowHisTaskService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.enums.NodeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/workflow/his-task")
public class WarmFlowHisTaskController {

    private WarmFlowHisTaskService warmFlowHisTaskService;

    /**
     * 已办任务
     *
     * @param pageQuery PageQuery
     * @param vo        FlowTaskVO
     * @return PageQuery<FlowHisTaskVO>
     */
    @GetMapping("/page")
    public PageQuery<FlowHisTaskVO> page(PageQuery<FlowHisTaskVO> pageQuery, FlowHisTaskVO vo) {
        Long userId = LoginHelper.getSessionUserId();
        // 只查询当前登录用户的已办任务
        vo.setApprover(userId.toString());
        // 只查询中间节点
        vo.setNodeType(NodeType.BETWEEN.getKey());
        return warmFlowHisTaskService.selectPageVo(pageQuery, vo);
    }
}
