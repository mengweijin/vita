package com.github.mengweijin.vita.workflow.domain.vo;

import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.warm.flow.core.enums.CooperateType;
import org.dromara.warm.flow.core.enums.SkipType;
import org.dromara.warm.flow.orm.entity.FlowHisTask;

import java.util.List;

/**
 *
 * @author mengweijin
 * @since 2026/5/30
 */
@AutoMappers({
        @AutoMapper(target = FlowHisTask.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
public class FlowHisTaskVO extends BaseEntity {

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
     * 流程实例表id
     */
    private Long instanceId;

    /**
     * 任务表id
     */
    private Long taskId;

    /**
     * 协作方式(1审批 2转办 3委派 4会签 5票签 6加签 7减签)
     * {@link CooperateType}
     */
    private Integer cooperateType;

    /**
     * 开始节点编码
     */
    private String nodeCode;

    /**
     * 开始节点名称
     */
    private String nodeName;

    /**
     * 开始节点类型（0开始节点 1中间节点 2结束节点 3互斥网关 4并行网关）
     */
    private Integer nodeType;

    /**
     * 目标节点编码
     */
    private String targetNodeCode;

    /**
     * 结束节点名称
     */
    private String targetNodeName;

    /**
     * 审批者
     */
    @Translation(translateType = ETranslateType.USER_ID_TO_NICKNAME)
    private String approver;

    /**
     * 协作人(只有转办、会签、票签、委派)
     */
    private String collaborator;

    /**
     * 跳转类型（PASS通过 REJECT退回 NONE无动作）
     * {@link SkipType}
     */
    private String skipType;

    /**
     * 流程状态（0待提交 1审批中 2审批通过 4终止 5作废 6撤销 8已完成 9已退回 10失效 11拿回）
     */
    private String flowStatus;

    /**
     * 审批意见
     */
    private String message;

    /**
     * 流程变量
     */
    private String variable;

    /**
     * 业务详情 存业务类的json
     */
    private String ext;

    /**
     * 审批表单是否自定义（Y=是 N=否）
     */
    private String formCustom;

    /**
     * 审批表单路径
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

    // endregion
}
