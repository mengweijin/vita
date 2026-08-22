package com.github.mengweijin.vita.workflow.service;

import cn.hutool.v7.core.io.IoUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.workflow.domain.vo.FlowDefinitionVO;
import com.github.mengweijin.vita.workflow.mapper.WarmFlowDefinitionMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.orm.entity.FlowDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 流程定义表 FlowDefinition Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 * @since 2026-04-12
 */
@Slf4j
@Service
@AllArgsConstructor
public class WarmFlowDefinitionService extends BaseVitaService<WarmFlowDefinitionMapper, FlowDefinition, FlowDefinitionVO> {

    public PageQuery<FlowDefinitionVO> pageVo(PageQuery<FlowDefinition> pageQuery, Wrapper<FlowDefinition> queryWrapper) {
        IPage<FlowDefinition> page = this.page(pageQuery.toPage(), queryWrapper);
        return MapstructUtils.convertToPageQuery(page, FlowDefinitionVO.class);
    }

    public LambdaQueryWrapper<FlowDefinition> buildQueryWrapper(FlowDefinition flowDefinition) {
        LambdaQueryWrapper<FlowDefinition> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(flowDefinition.getId() != null, FlowDefinition::getId, flowDefinition.getId());
        wrapper.eq(StrUtil.isNotBlank(flowDefinition.getModelValue()), FlowDefinition::getModelValue, flowDefinition.getModelValue());
        wrapper.eq(StrUtil.isNotBlank(flowDefinition.getCategory()), FlowDefinition::getCategory, flowDefinition.getCategory());
        wrapper.eq(StrUtil.isNotBlank(flowDefinition.getVersion()), FlowDefinition::getVersion, flowDefinition.getVersion());
        wrapper.eq(flowDefinition.getIsPublish() != null, FlowDefinition::getIsPublish, flowDefinition.getIsPublish());
        wrapper.eq(StrUtil.isNotBlank(flowDefinition.getFormCustom()), FlowDefinition::getFormCustom, flowDefinition.getFormCustom());
        wrapper.eq(StrUtil.isNotBlank(flowDefinition.getFormPath()), FlowDefinition::getFormPath, flowDefinition.getFormPath());
        wrapper.eq(flowDefinition.getActivityStatus() != null, FlowDefinition::getActivityStatus, flowDefinition.getActivityStatus());
        wrapper.eq(StrUtil.isNotBlank(flowDefinition.getListenerType()), FlowDefinition::getListenerType, flowDefinition.getListenerType());
        wrapper.eq(StrUtil.isNotBlank(flowDefinition.getListenerPath()), FlowDefinition::getListenerPath, flowDefinition.getListenerPath());
        wrapper.eq(StrUtil.isNotBlank(flowDefinition.getExt()), FlowDefinition::getExt, flowDefinition.getExt());
        wrapper.eq(StrUtil.isNotBlank(flowDefinition.getDelFlag()), FlowDefinition::getDelFlag, flowDefinition.getDelFlag());
        wrapper.eq(StrUtil.isNotBlank(flowDefinition.getTenantId()), FlowDefinition::getTenantId, flowDefinition.getTenantId());
        wrapper.like(StrUtil.isNotBlank(flowDefinition.getFlowCode()), FlowDefinition::getFlowCode, flowDefinition.getFlowCode());
        wrapper.like(StrUtil.isNotBlank(flowDefinition.getFlowName()), FlowDefinition::getFlowName, flowDefinition.getFlowName());
        return wrapper;
    }

    @Transactional(rollbackFor = Exception.class)
    public void importDefinition(List<MultipartFile> list) {
        InputStream inputStream = null;
        try {
            for (MultipartFile multipartFile : list) {
                inputStream = multipartFile.getInputStream();
                FlowEngine.defService().importIs(inputStream);
            }
        } catch (IOException e) {
            throw new ServerException(e);
        } finally {
            IoUtil.closeQuietly(inputStream);
        }
    }
}
