package com.github.mengweijin.vita.workflow.service;

import cn.hutool.v7.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.workflow.domain.vo.FlowInstanceVO;
import com.github.mengweijin.vita.workflow.domain.vo.FlowTaskVO;
import com.github.mengweijin.vita.workflow.enums.EWarmFlowDelFlag;
import com.github.mengweijin.vita.workflow.mapper.WarmFlowInstanceMapper;
import com.github.mengweijin.vita.workflow.mapper.WarmFlowTaskMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.entity.Task;
import org.dromara.warm.flow.core.entity.User;
import org.dromara.warm.flow.core.service.TaskService;
import org.dromara.warm.flow.core.service.UserService;
import org.dromara.warm.flow.orm.entity.FlowInstance;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 流程实例表 FlowInstance Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Slf4j
@Service
@AllArgsConstructor
public class WarmFlowTaskService extends BaseVitaService<WarmFlowInstanceMapper, FlowInstance, FlowInstanceVO> {

    private final WarmFlowTaskMapper warmFlowTaskMapper;

    public PageQuery<FlowTaskVO> selectPageVo(PageQuery<FlowTaskVO> pageQuery, FlowTaskVO vo) {
        vo.setDelFlag(EWarmFlowDelFlag.ZERO.getCode());
        IPage<FlowTaskVO> page = warmFlowTaskMapper.selectPageVo(pageQuery.toPage(), vo);
        return PageQuery.of(page);
    }

    @Override
    public LambdaQueryWrapper<FlowInstance> buildQueryWrapper(FlowInstance entity) {
        return super.defaultQueryWrapper(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Instance reject(Long taskId, String message, Map<String, Object> variable) {
        TaskService taskService = FlowEngine.taskService();
        UserService userService = FlowEngine.userService();

        Instance instance = taskService.reject(taskId, message, variable);

        // warm-flow 退回时,默认创建了一个待办任务，但申请节点一般没设置办理人，因此这里手动往任务中设置办理人。
        List<Task> taskList = taskService.getByInsId(instance.getId());
        Task task = CollUtil.getLast(taskList);
        task.setPermissionList(Collections.singletonList(instance.getCreateBy()));
        List<User> users = userService.taskAddUser(task);
        // 保存待办任务的权限人
        FlowEngine.userService().saveBatch(users);

        return instance;
    }
}
