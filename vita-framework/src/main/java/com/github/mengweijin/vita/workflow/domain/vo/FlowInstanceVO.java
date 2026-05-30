package com.github.mengweijin.vita.workflow.domain.vo;

import com.github.mengweijin.vita.framework.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.warm.flow.orm.entity.FlowInstance;

/**
 *
 * @author mengweijin
 * @since 2026/5/30
 */
@AutoMappers({
        @AutoMapper(target = FlowInstance.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
public class FlowInstanceVO extends BaseEntity {

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 对应flow_definition表的id
     */
    private Long definitionId;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）
     */
    private Integer nodeType;

    /**
     * 流程节点编码   每个流程的nodeCode是唯一的,即definitionId+nodeCode唯一,在数据库层面做了控制
     */
    private String nodeCode;

    /**
     * 流程节点名称
     */
    private String nodeName;

    /**
     * 流程变量
     */
    private String variable;

    /**
     * 流程状态（0待提交 1审批中 2审批通过 4终止 5作废 6撤销 8已完成 9已退回 10失效 11拿回）
     */
    private String flowStatus;

    /**
     * 流程激活状态（0挂起 1激活）
     */
    private Integer activityStatus;

    /**
     * 审批表单是否自定义（Y=是 N=否）
     */
    private String formCustom;

    /**
     * 审批表单是否自定义（Y=是 N=否）
     */
    private String formPath;

    /**
     * 流程定义json
     */
    private String defJson;

    /**
     * 扩展字段，预留给业务系统使用
     */
    private String ext;

}
