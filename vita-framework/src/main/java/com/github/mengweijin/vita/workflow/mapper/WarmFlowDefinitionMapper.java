package com.github.mengweijin.vita.workflow.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.workflow.domain.vo.FlowDefinitionVO;
import org.apache.ibatis.annotations.Mapper;
import org.dromara.warm.flow.orm.entity.FlowDefinition;

/**
 * <p>
 * FlowInstance Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface WarmFlowDefinitionMapper extends BaseVitaMapper<FlowDefinition, FlowDefinitionVO> {
}

