package com.github.mengweijin.vita.workflow.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.workflow.domain.vo.FlowInstanceVO;
import com.github.mengweijin.vita.workflow.domain.vo.FlowTaskVO;
import com.github.mengweijin.vita.workflow.enums.EWarmFlowDelFlag;
import com.github.mengweijin.vita.workflow.mapper.WarmFlowInstanceMapper;
import com.github.mengweijin.vita.workflow.mapper.WarmFlowTaskMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.service.TaskService;
import org.dromara.warm.flow.orm.entity.FlowInstance;
import org.springframework.stereotype.Service;

/**
 * 流程实例表 FlowInstance Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Slf4j
@Service
@AllArgsConstructor
public class WarmFlowTaskService extends BaseVitaService<WarmFlowInstanceMapper, FlowInstance, FlowInstanceVO> {

    private final TaskService taskService;

    private final WarmFlowTaskMapper warmFlowTaskMapper;

    public PageQuery<FlowTaskVO> selectPageVo(PageQuery<FlowTaskVO> pageQuery, FlowTaskVO vo) {
        vo.setDelFlag(EWarmFlowDelFlag.ZERO.getCode());
        IPage<FlowTaskVO> page = warmFlowTaskMapper.selectPageVo(pageQuery.toPage(), vo);
        return PageQuery.of(page);
    }

    @Override
    public LambdaQueryWrapper<FlowInstance> buildQueryWrapper(FlowInstance entity) {
        return super.defaultQueryWrapper(entity);
    }
}
