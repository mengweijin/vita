package com.github.mengweijin.vita.workflow.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.workflow.domain.entity.WorkflowFormDO;
import com.github.mengweijin.vita.workflow.domain.vo.WorkflowFormVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程表单表 WorkflowForm Mapper
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Mapper
public interface WorkflowFormMapper extends BaseVitaMapper<WorkflowFormDO, WorkflowFormVO> {

}

