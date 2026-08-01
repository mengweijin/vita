package com.github.mengweijin.vita.oa.mapper;

import com.github.mengweijin.vita.framework.mybatis.BaseVitaMapper;
import com.github.mengweijin.vita.oa.domain.entity.EmployeeLeaveDO;
import com.github.mengweijin.vita.oa.domain.vo.EmployeeLeaveVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 员工请假申请表 LeaveApply Mapper
 *
 * @author mengweijin
 * @since 2026-05-16
 */
@Mapper
public interface EmployeeLeaveMapper extends BaseVitaMapper<EmployeeLeaveDO, EmployeeLeaveVO> {

}

