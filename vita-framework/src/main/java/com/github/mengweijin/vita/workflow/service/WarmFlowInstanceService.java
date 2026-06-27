package com.github.mengweijin.vita.workflow.service;

import cn.hutool.v7.core.bean.BeanUtil;
import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.workflow.domain.vo.FlowInstanceVO;
import com.github.mengweijin.vita.workflow.enums.EWarmFlowDelFlag;
import com.github.mengweijin.vita.workflow.mapper.WarmFlowInstanceMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.entity.Instance;
import org.dromara.warm.flow.core.entity.Task;
import org.dromara.warm.flow.core.service.InsService;
import org.dromara.warm.flow.core.service.TaskService;
import org.dromara.warm.flow.orm.entity.FlowInstance;
import org.dromara.warm.flow.orm.entity.FlowTask;
import org.springframework.stereotype.Service;

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
public class WarmFlowInstanceService extends BaseVitaService<WarmFlowInstanceMapper, FlowInstance, FlowInstanceVO> {

    private final InsService insService;

    private final TaskService taskService;

    private final WarmFlowInstanceMapper warmFlowInstanceMapper;

    public PageQuery<FlowInstanceVO> selectPageVo(PageQuery<FlowInstanceVO> pageQuery, FlowInstanceVO vo) {
        vo.setDelFlag(EWarmFlowDelFlag.ZERO.getCode());
        IPage<FlowInstanceVO> page = warmFlowInstanceMapper.selectPageVo(pageQuery.toPage(), vo);
        return PageQuery.of(page);
    }

    /**
     * 启动流程
     *
     * @param flowCode 流程编码
     * @param entity   流程变量对象
     * @param <T>      entity 类型
     * @return Instance
     */
    public <T extends BaseEntity> Instance start(String flowCode, T entity) {
        Map<String, Object> beanMap = BeanUtil.beanToMap(entity);
        FlowParams flowParams = FlowParams.build();
        flowParams.variable(beanMap);

        return this.start(flowCode, entity.getId(), flowParams);
    }

    /**
     * 启动流程
     *
     * @param flowCode   流程编码
     * @param businessId 业务ID
     * @param flowParams 流程参数
     * @return Instance
     */
    public Instance start(String flowCode, Long businessId, FlowParams flowParams) {
        flowParams = ObjUtil.defaultIfNull(flowParams, FlowParams::build);
        // flowCode 必填
        flowParams.flowCode(flowCode);
        // handler 必填
        flowParams.handler(NumberUtil.toStr(LoginHelper.getSessionUserId()));
        Instance instance = insService.start(NumberUtil.toStr(businessId), flowParams);

        // warm-flow 不支持第一个节点退回，因此画流程图时需要额外增加一个【申请】的中间节点。
        List<Task> taskList = taskService.list(new FlowTask().setInstanceId(instance.getId()));
        taskList.stream().findFirst().ifPresent(task -> {
            // 自动跳过第一个【申请】的节点
            taskService.pass(task.getId(), "submit", null);
        });
        return instance;
    }

    @Override
    public LambdaQueryWrapper<FlowInstance> buildQueryWrapper(FlowInstance entity) {
        return super.defaultQueryWrapper(entity);
    }
}
