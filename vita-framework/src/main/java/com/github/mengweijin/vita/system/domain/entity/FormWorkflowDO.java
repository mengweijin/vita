package com.github.mengweijin.vita.system.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.system.domain.bo.FormWorkflowBO;
import com.github.mengweijin.vita.system.domain.vo.FormWorkflowVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 工作流表单路由配置表
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@AutoMappers({
        @AutoMapper(target = FormWorkflowBO.class),
        @AutoMapper(target = FormWorkflowVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "VT_FORM_WORKFLOW")
public class FormWorkflowDO extends BaseEntity {

    /**
     * 表单名称
     */
    private String name;

    /**
     * 路由路径
     */
    private String routePath;

    /**
     * 备注
     */
    private String remark;

}
