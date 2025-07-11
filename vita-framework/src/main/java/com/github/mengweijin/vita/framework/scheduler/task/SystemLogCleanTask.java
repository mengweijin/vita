package com.github.mengweijin.vita.framework.scheduler.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.scheduler.ISchedulingTask;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.monitor.domain.entity.LogDO;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import com.github.mengweijin.vita.monitor.service.LogSystemService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.date.DateFormatPool;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.math.NumberUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

/**
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
    private static final String LOG_RETAINED_DAYS_KEY = "logRetainedDays";

    private LogSystemService logSystemService;


    @Override
    public String run(SchedulingTaskDO task, Map<?, ?> args) {
        String daysString = StrUtil.toStringOrNull(args.get(LOG_RETAINED_DAYS_KEY));
        if(!NumberUtil.isNumber(daysString)) {
            String msg = I18nUtils.msg("system.scheduling.task.config.incorrect", task.getName(), task.getBeanName(), task.getArgs());
            throw new ServerException(msg);
        }
        int days = NumberUtil.parseInt(daysString);
        LocalDateTime minusTime = LocalDateTime.now().minusDays(days);

        LambdaQueryWrapper<LogDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.le(LogDO::getCreateTime, minusTime);

        int deleted = logSystemService.getBaseMapper().delete(wrapper);
        return String.format("%s records before time [%s] were deleted.", deleted, TimeUtil.format(minusTime, DateFormatPool.NORM_DATETIME_FORMATTER));
    }
}
