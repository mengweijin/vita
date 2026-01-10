package com.github.mengweijin.vita.framework.scheduler.task;

import cn.hutool.v7.core.date.DateFormatPool;
import cn.hutool.v7.core.date.TimeUtil;
import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.scheduler.ISchedulingTask;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.monitor.domain.entity.LogDO;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.service.LogSystemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 系统日志定时清理任务，防止数据库日志增长过大。
 * @author mengweijin
 * @since 2025/6/22
 */
@Slf4j
@Component
@AllArgsConstructor
public class SystemLogCleanTask implements ISchedulingTask {

    /**
     * 系统日志最大保留时间（单位：天）
     */
    private static final String DAYS = "days";

    private LogSystemService logSystemService;

    @Override
    public String run(SchedulingTaskDO task, Map<?, ?> args) {
        String daysString = StrUtil.toStringOrNull(args.get(DAYS));
        if(!NumberUtil.isNumber(daysString)) {
            String msg = I18nUtils.msg("system.scheduling.task.config.incorrect", task.getName(), task.getBeanName(), task.getArgs());
            throw new ServerException(msg);
        }
        int days = NumberUtil.parseInt(daysString);
        LocalDateTime minusTime = LocalDate.now().minusDays(days).atTime(0, 0, 0);

        LambdaQueryWrapper<LogDO> wrapper = Wrappers.lambdaQuery();
        wrapper.le(LogDO::getCreateTime, minusTime);

        int deleted = logSystemService.getBaseMapper().delete(wrapper);
        return String.format("%s records before time [%s] were deleted.", deleted, TimeUtil.format(minusTime, DateFormatPool.NORM_DATETIME_FORMATTER));
    }
}
