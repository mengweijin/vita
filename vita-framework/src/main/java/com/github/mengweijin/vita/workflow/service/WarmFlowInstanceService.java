package com.github.mengweijin.vita.workflow.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.workflow.domain.vo.FlowInstanceVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.orm.entity.FlowInstance;
import org.dromara.warm.flow.orm.mapper.FlowInstanceMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程定义表 FlowDefinition Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 * @since 2026-05-30
 */
@Slf4j
@Service
@AllArgsConstructor
public class WarmFlowInstanceService extends CrudRepository<FlowInstanceMapper, FlowInstance> {

    public PageQuery<FlowInstanceVO> pageVo(PageQuery<FlowInstance> pageQuery, Wrapper<FlowInstance> queryWrapper) {
        IPage<FlowInstance> page = this.page(pageQuery.toPage(), queryWrapper);
        List<FlowInstanceVO> list = MapstructUtils.getConverter().convert(page.getRecords(), FlowInstanceVO.class);
        return PageQuery.of(page.getCurrent(), page.getSize(), page.getTotal(), list);
    }

    public LambdaQueryWrapper<FlowInstance> buildQueryWrapper(FlowInstance flowInstance) {
        LambdaQueryWrapper<FlowInstance> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(flowInstance.getId() != null, FlowInstance::getId, flowInstance.getId());
        wrapper.eq(flowInstance.getDefinitionId() != null, FlowInstance::getDefinitionId, flowInstance.getDefinitionId());
        wrapper.eq(StrUtil.isNotBlank(flowInstance.getBusinessId()), FlowInstance::getBusinessId, flowInstance.getBusinessId());
        wrapper.eq(flowInstance.getNodeType() != null, FlowInstance::getNodeType, flowInstance.getNodeType());
        wrapper.eq(StrUtil.isNotBlank(flowInstance.getNodeCode()), FlowInstance::getNodeCode, flowInstance.getNodeCode());
        wrapper.eq(StrUtil.isNotBlank(flowInstance.getNodeName()), FlowInstance::getNodeName, flowInstance.getNodeName());
        wrapper.eq(StrUtil.isNotBlank(flowInstance.getVariable()), FlowInstance::getVariable, flowInstance.getVariable());
        wrapper.eq(StrUtil.isNotBlank(flowInstance.getFlowStatus()), FlowInstance::getFlowStatus, flowInstance.getFlowStatus());
        wrapper.eq(flowInstance.getActivityStatus() != null, FlowInstance::getActivityStatus, flowInstance.getActivityStatus());
        return wrapper;
    }


}
