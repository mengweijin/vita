package com.github.mengweijin.vita.workflow.variable;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.jackson.wrapper.AbstractObjectMapperWrapper;
import com.github.mengweijin.vita.framework.util.ObjectMapperUtils;
import com.github.mengweijin.vita.workflow.enums.EWorkflowCode;
import lombok.AllArgsConstructor;
import org.dromara.warm.flow.core.entity.Definition;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.service.DefService;
import org.dromara.warm.flow.core.service.InsService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 *
 * @author mengweijin
 * @since 2026/8/15
 */
@Component
@AllArgsConstructor
public class WorkflowVariableHandler {

    private static final TypeReference<Map<String, Object>> TYPE_REFERENCE = new TypeReference<>() {
    };

    private final InsService insService;

    private final DefService defService;

    private final List<IWorkflowVariable<?>> workflowVariableList;

    public Map<String, Object> getVariable(Long instanceId) {
        Instance instance = insService.getById(instanceId);
        Definition definition = defService.getById(instance.getDefinitionId());
        String flowCode = definition.getFlowCode();
        EWorkflowCode workflowCode = EWorkflowCode.fromValue(flowCode);

        IWorkflowVariable<?> workflowVariable = this.getWorkflowVariableInstance(workflowCode);

        BaseEntity entity = workflowVariable.getByBusinessId(instance.getBusinessId());
        AbstractObjectMapperWrapper objectMapper = ObjectMapperUtils.getObjectMapperWrapper();
        String json = objectMapper.writeValueAsString(entity);
        return objectMapper.readValue(json, TYPE_REFERENCE);
    }

    private IWorkflowVariable<?> getWorkflowVariableInstance(EWorkflowCode workflowCode) {
        return workflowVariableList.stream()
                .filter(i -> workflowCode == i.workflowCode())
                .findFirst()
                .orElseThrow();
    }
}
