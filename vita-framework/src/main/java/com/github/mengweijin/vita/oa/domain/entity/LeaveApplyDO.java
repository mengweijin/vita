package com.github.mengweijin.vita.oa.domain.entity;

import cn.hutool.v7.core.date.DateFormatPool;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import com.github.mengweijin.vita.framework.mybatis.typehandler.CommaDelimitedListTypeHandler;
import com.github.mengweijin.vita.oa.domain.bo.LeaveApplyBO;
import com.github.mengweijin.vita.oa.domain.vo.LeaveApplyVO;
import io.github.linpeilie.annotations.AutoMapper;
import io.github.linpeilie.annotations.AutoMappers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 员工请假申请表
 *
 * @author mengweijin
 * @since 2026-05-16
 */
@AutoMappers({
        @AutoMapper(target = LeaveApplyBO.class),
        @AutoMapper(target = LeaveApplyVO.class),
})
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_OA_LEAVE_APPLY")
public class LeaveApplyDO extends BaseEntity {

    /**
     * 请假类型。关联字典：vt_oa_leave_type
     */
    private String leaveType;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = DateFormatPool.NORM_DATETIME_MINUTE_PATTERN)
    @JsonFormat(pattern = DateFormatPool.NORM_DATETIME_MINUTE_PATTERN)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = DateFormatPool.NORM_DATETIME_MINUTE_PATTERN)
    @JsonFormat(pattern = DateFormatPool.NORM_DATETIME_MINUTE_PATTERN)
    private LocalDateTime endTime;

    /**
     * 请假天数
     */
    private Integer leaveDays;

    /**
     * 请假原因
     */
    private String remark;

    /**
     * 附件 ID(s)（数据库中以逗号分隔存储）
     */
    @TableField(typeHandler = CommaDelimitedListTypeHandler.class)
    private List<String> attachmentIds;

    /**
     * 工作流实例ID
     */
    private String workflowId;
}
