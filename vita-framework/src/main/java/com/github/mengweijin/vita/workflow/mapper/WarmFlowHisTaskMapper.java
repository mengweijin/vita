package com.github.mengweijin.vita.workflow.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.workflow.domain.vo.FlowHisTaskVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dromara.warm.flow.orm.entity.FlowHisTask;

/**
 * <p>
 * FlowHis Mapper
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Mapper
public interface WarmFlowHisTaskMapper extends BaseVitaMapper<FlowHisTask, FlowHisTaskVO> {

    /**
     * 自定义分页查询。
     *
     * @return IPage
     */
    IPage<FlowHisTaskVO> selectPageVo(IPage<FlowHisTaskVO> page, @Param("p") FlowHisTaskVO vo);
}

