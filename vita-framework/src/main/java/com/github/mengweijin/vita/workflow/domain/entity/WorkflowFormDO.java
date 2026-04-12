package com.github.mengweijin.vita.workflow.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.workflow.domain.bo.WorkflowFormBO;
import com.github.mengweijin.vita.workflow.domain.vo.WorkflowFormVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 流程表单表
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@AutoMappers({
        @AutoMapper(target = WorkflowFormBO.class),
        @AutoMapper(target = WorkflowFormVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_WORKFLOW_FORM")
public class WorkflowFormDO extends BaseEntity {

    /**
    * 表单名称
    */
    private String name;

    /**
    * 表单类型。关联字典：vt_flow_form_type
    */
    private String type;

    /**
    * 表单路由路径
    */
    private String formPath;
}
