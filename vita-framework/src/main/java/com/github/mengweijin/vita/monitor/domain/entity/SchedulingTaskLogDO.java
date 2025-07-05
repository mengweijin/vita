package com.github.mengweijin.vita.monitor.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.mengweijin.vita.framework.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("VT_SCHEDULING_TASK_LOG")
public class SchedulingTaskLogDO extends BaseEntity {

    /**
     * 调度任务ID
     */
    private Long schedulingTaskId;

    /**
     * 任务执行状态。字典：vt_scheduling_task_status {@link com.github.mengweijin.vita.system.enums.ESchedulingTaskStatus}
     */
    private String status;

    /**
     * 任务是否执行成功。vt_succeeded [Y, N]
     */
    private String success;

    /**
     * 执行消耗时间（毫秒）
     */
    private Long costTime;

    /**
     * 实际执行参数。以 JSON 字符串存储
     */
    private String args;

    /**
     * 执行成功或失败时的附加信息
     */
    private String message;

}
