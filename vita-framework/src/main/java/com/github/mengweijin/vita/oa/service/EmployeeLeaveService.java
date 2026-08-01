package com.github.mengweijin.vita.oa.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.oa.domain.entity.EmployeeLeaveDO;
import com.github.mengweijin.vita.oa.domain.vo.EmployeeLeaveVO;
import com.github.mengweijin.vita.oa.mapper.EmployeeLeaveMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 员工请假申请表 LeaveApply Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 * @since 2026-05-16
 */
@Slf4j
@Service
@AllArgsConstructor
public class EmployeeLeaveService extends BaseVitaService<EmployeeLeaveMapper, EmployeeLeaveDO, EmployeeLeaveVO> {

    @Override
    public LambdaQueryWrapper<EmployeeLeaveDO> buildQueryWrapper(EmployeeLeaveDO entity) {
        LambdaQueryWrapper<EmployeeLeaveDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(entity.getId() != null, EmployeeLeaveDO::getId, entity.getId());
        wrapper.eq(StrUtil.isNotBlank(entity.getLeaveType()), EmployeeLeaveDO::getLeaveType, entity.getLeaveType());
        wrapper.eq(entity.getStartTime() != null, EmployeeLeaveDO::getStartTime, entity.getStartTime());
        wrapper.eq(entity.getEndTime() != null, EmployeeLeaveDO::getEndTime, entity.getEndTime());
        wrapper.eq(entity.getLeaveDays() != null, EmployeeLeaveDO::getLeaveDays, entity.getLeaveDays());
        wrapper.eq(StrUtil.isNotBlank(entity.getRemark()), EmployeeLeaveDO::getRemark, entity.getRemark());
        wrapper.eq(StrUtil.isNotBlank(entity.getWorkflowId()), EmployeeLeaveDO::getWorkflowId, entity.getWorkflowId());
        wrapper.gt(entity.getStartCreateTime() != null, EmployeeLeaveDO::getCreateTime, entity.getStartCreateTime());
        wrapper.le(entity.getEndCreateTime() != null, EmployeeLeaveDO::getCreateTime, entity.getEndCreateTime());
        return wrapper;
    }
}
