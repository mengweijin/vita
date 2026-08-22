package com.github.mengweijin.vita.system.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.system.domain.entity.FormWorkflowDO;
import com.github.mengweijin.vita.system.domain.vo.FormWorkflowVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 流程表单表 WorkflowForm Mapper
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Mapper
public interface FormWorkflowMapper extends BaseVitaMapper<FormWorkflowDO, FormWorkflowVO> {

}

