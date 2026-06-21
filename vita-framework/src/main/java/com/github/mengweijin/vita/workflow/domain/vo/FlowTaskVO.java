package com.github.mengweijin.vita.workflow.domain.vo;

import com.github.mengweijin.vita.framework.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.warm.flow.core.entity.User;
import org.dromara.warm.flow.orm.entity.FlowTask;

import java.util.List;

/**
 *
 * @author mengweijin
 * @since 2026/5/30
 */
@AutoMappers({
        @AutoMapper(target = FlowTask.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
public class FlowTaskVO extends BaseEntity {

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 删除标记 0 1
     */
    private String delFlag;

    /**
     * 对应flow_definition表的id
     */
    private Long definitionId;

    /**
     * 流程实例表id
     */
    private Long instanceId;

    /**
     * 节点编码
     */
    private String nodeCode;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）
     */
    private Integer nodeType;

    /**
     * 流程状态（0待提交 1审批中 2审批通过 4终止 5作废 6撤销 8已完成 9已退回 10失效 11拿回）
     */
    private String flowStatus;

    /**
     * 审批表单是否自定义（Y=是 N=否）
     */
    private String formCustom;

    /**
     * 审批表单
     */
    private String formPath;

    // region ----- 非实体类字段

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 业务id
     */
    private String businessId;

    /**
     * 权限标识 permissionFlag的list形式
     */
    private List<String> permissionList;

    /**
     * 流程用户列表
     */
    private List<User> userList;

    // endregion
}
