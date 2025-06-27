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
     * CRON 表达式（注意：Spring boot 3.0 后，cron 只支持 6 个字段，即不支持年份）
     */
    private String cron;

    /**
     * 任务实现类的 Bean 名称（Bean 需要实现 ISchedulingTask 类）
     */
    private String beanName;

    /**
     * 执行参数。以 JSON 字符串存储
     */
    private String args;

    /**
    * 是否禁用。[Y, N]
    */
    private String disabled;

    /**
    * 备注
    */
    private String remark;
}
