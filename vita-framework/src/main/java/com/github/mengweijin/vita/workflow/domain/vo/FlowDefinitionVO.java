package com.github.mengweijin.vita.workflow.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.jackson.translation.ETranslateType;
import com.github.mengweijin.vita.framework.jackson.translation.Translation;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.warm.flow.core.entity.Node;
import org.dromara.warm.flow.core.entity.User;
import org.dromara.warm.flow.orm.entity.FlowDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mengweijin
 * @since 2026/4/12
 */
@AutoMappers({
        @AutoMapper(target = FlowDefinition.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
public class FlowDefinitionVO extends BaseEntity {

    /**
     * 租户 ID
     */
    private String tenantId;

    /**
     * 删除标记
     */
    private String delFlag;

    /**
     * 流程编码
     */
    private String flowCode;

    /**
     * 流程名称
     */
    private String flowName;

    /**
     * 设计器模型（CLASSICS经典模型 MIMIC仿钉钉模型）
     */
    private String modelValue;

    /**
     * 流程类别 ID
     */
    private String category;

    /**
     * 流程类别名称
     */
    @Translation(translateType = ETranslateType.CATEGORY_ID_TO_NAME, field = "category")
    private String categoryName;

    /**
     * 流程版本
     */
    private String version;

    /**
     * 是否发布（0未开启 1开启）
     */
    private Integer isPublish;

    /**
     * 审批表单是否自定义（Y是 N否）
     */
    private String formCustom;

    /**
     * 审批表单路径
     */
    private String formPath;

    /**
     * 流程激活状态（0挂起 1激活）
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer activityStatus;

    /**
     * 监听器类型
     */
    private String listenerType;

    /**
     * 监听器路径
     */
    private String listenerPath;

    /**
     * 扩展字段，预留给业务系统使用
     */
    private String ext;

    private List<Node> nodeList = new ArrayList<>();

    private List<User> userList = new ArrayList<>();

}
