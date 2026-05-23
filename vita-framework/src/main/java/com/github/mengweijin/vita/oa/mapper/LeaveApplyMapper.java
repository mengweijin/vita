package com.github.mengweijin.vita.oa.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.oa.domain.entity.LeaveApplyDO;
import com.github.mengweijin.vita.oa.domain.vo.LeaveApplyVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工请假申请表 LeaveApply Mapper
 *
 * @author mengweijin
 * @since 2026-05-16
 */
@Mapper
public interface LeaveApplyMapper extends BaseVitaMapper<LeaveApplyDO, LeaveApplyVO> {

}

