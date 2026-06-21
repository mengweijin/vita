package com.github.mengweijin.vita.workflow.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.workflow.domain.vo.FlowTaskVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dromara.warm.flow.orm.entity.FlowTask;

/**
 * <p>
 * FlowInstance Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface WarmFlowTaskMapper extends BaseVitaMapper<FlowTask, FlowTaskVO> {

    /**
     * 自定义分页查询。
     *
     * @return IPage
     */
    IPage<FlowTaskVO> selectPageVo(IPage<FlowTaskVO> page, @Param("p") FlowTaskVO vo);
}

