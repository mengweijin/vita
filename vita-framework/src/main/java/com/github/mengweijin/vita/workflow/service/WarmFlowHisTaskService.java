package com.github.mengweijin.vita.workflow.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.workflow.domain.vo.FlowHisTaskVO;
import com.github.mengweijin.vita.workflow.enums.EWarmFlowDelFlag;
import com.github.mengweijin.vita.workflow.mapper.WarmFlowHisTaskMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.enums.NodeType;
import org.dromara.warm.flow.orm.entity.FlowHisTask;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程实例表 FlowHis Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Slf4j
@Service
@AllArgsConstructor
public class WarmFlowHisTaskService extends BaseVitaService<WarmFlowHisTaskMapper, FlowHisTask, FlowHisTaskVO> {

    private final WarmFlowHisTaskMapper warmFlowHisTaskMapper;

    public PageQuery<FlowHisTaskVO> selectPageVo(PageQuery<FlowHisTaskVO> pageQuery, FlowHisTaskVO vo) {
        vo.setDelFlag(EWarmFlowDelFlag.ZERO.getCode());
        IPage<FlowHisTaskVO> page = warmFlowHisTaskMapper.selectPageVo(pageQuery.toPage(), vo);
        return PageQuery.of(page);
    }

    /**
     * 查询审批记录
     *
     * @param instanceId 流程实例 ID
     * @param nodeType   节点类型
     * @return List<FlowHisTaskVO>
     */
    public List<FlowHisTaskVO> listApproveLog(Long instanceId, NodeType nodeType) {
        return warmFlowHisTaskMapper.selectApproveLog(instanceId, nodeType.getKey());
    }

    @Override
    public LambdaQueryWrapper<FlowHisTask> buildQueryWrapper(FlowHisTask entity) {
        return super.defaultQueryWrapper(entity);
    }

}
