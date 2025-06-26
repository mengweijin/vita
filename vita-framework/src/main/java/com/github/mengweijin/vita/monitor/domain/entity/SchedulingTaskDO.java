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
@TableName("VT_SCHEDULING_TASK")
public class SchedulingTaskDO extends BaseEntity {

    /**
    * 任务名称
    */
    private String name;

    /**
     * CRON 表达式
     */
    private String cron;

    /**
     * 任务实现类的 Bean 名称（Bean 需要实现 ISchedulingTask 类）
     */
    private String beanName;

    /**
    * 是否禁用。[Y, N]
    */
    private String disabled;

    /**
    * 备注
    */
    private String remark;
}
