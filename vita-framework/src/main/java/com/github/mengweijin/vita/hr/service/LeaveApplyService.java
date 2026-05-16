package com.github.mengweijin.vita.hr.service;

import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.hr.domain.entity.LeaveApplyDO;
import com.github.mengweijin.vita.hr.domain.vo.LeaveApplyVO;
import com.github.mengweijin.vita.hr.mapper.LeaveApplyMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 员工请假申请表 LeaveApply Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 *
 * @author mengweijin
 * @since 2026-05-16
 */
@Slf4j
@Service
@AllArgsConstructor
public class LeaveApplyService extends BaseVitaService<LeaveApplyMapper, LeaveApplyDO, LeaveApplyVO> {

    @Override
    public LambdaQueryWrapper<LeaveApplyDO> buildQueryWrapper(LeaveApplyDO leaveApply) {
        LambdaQueryWrapper<LeaveApplyDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(leaveApply.getId() != null, LeaveApplyDO::getId, leaveApply.getId());
        wrapper.eq(StrUtil.isNotBlank(leaveApply.getLeaveType()), LeaveApplyDO::getLeaveType, leaveApply.getLeaveType());
        wrapper.eq(leaveApply.getStartTime() != null, LeaveApplyDO::getStartTime, leaveApply.getStartTime());
        wrapper.eq(leaveApply.getEndTime() != null, LeaveApplyDO::getEndTime, leaveApply.getEndTime());
        wrapper.eq(leaveApply.getLeaveDays() != null, LeaveApplyDO::getLeaveDays, leaveApply.getLeaveDays());
        wrapper.eq(StrUtil.isNotBlank(leaveApply.getRemark()), LeaveApplyDO::getRemark, leaveApply.getRemark());
        wrapper.eq(StrUtil.isNotBlank(leaveApply.getAttachmentId()), LeaveApplyDO::getAttachmentId, leaveApply.getAttachmentId());
        wrapper.eq(StrUtil.isNotBlank(leaveApply.getWorkflowId()), LeaveApplyDO::getWorkflowId, leaveApply.getWorkflowId());
        wrapper.gt(leaveApply.getStartCreateTime() != null, LeaveApplyDO::getCreateTime, leaveApply.getStartCreateTime());
        wrapper.le(leaveApply.getEndCreateTime() != null, LeaveApplyDO::getCreateTime, leaveApply.getEndCreateTime());
        return wrapper;
    }
}
