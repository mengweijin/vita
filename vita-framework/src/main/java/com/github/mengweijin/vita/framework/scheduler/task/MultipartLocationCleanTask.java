package com.github.mengweijin.vita.framework.scheduler.task;

import cn.hutool.v7.core.date.TimeUtil;
import cn.hutool.v7.core.io.file.FileUtil;
import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.scheduler.ISchedulingTask;
import com.github.mengweijin.vita.framework.util.I18nUtils;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 定时清理 ${spring.servlet.multipart.location} 临时目录，避免临时目录堆积过多无用文件。
 * @author mengweijin
 * @since 2025/6/22
 */
@Slf4j
@Component
@AllArgsConstructor
public class MultipartLocationCleanTask implements ISchedulingTask {

    /**
     * 清理临时文件超过的时长（单位：小时）
     */
    private static final String HOURS = "hours";

    private MultipartProperties multipartProperties;

    @Override
    public String run(SchedulingTaskDO task, Map<?, ?> args) {
        String hoursString = StrUtil.toStringOrNull(args.get(HOURS));
        if(!NumberUtil.isNumber(hoursString)) {
            String msg = I18nUtils.msg("system.scheduling.task.config.incorrect", task.getName(), task.getBeanName(), task.getArgs());
            throw new ServerException(msg);
        }
        int hours = NumberUtil.parseInt(hoursString);
        LocalDateTime minusTime = LocalDateTime.now().minusHours(hours);

        String location = multipartProperties.getLocation();
        AtomicReference<Long> count = new AtomicReference<>(0L);
        FileUtil.loopFiles(location).forEach(f -> {
            LocalDateTime lastModified = TimeUtil.of(f.lastModified());
            if(f.isFile() && lastModified.isBefore(minusTime)) {
                boolean deleted = f.delete();
                if(deleted) {
                    count.getAndSet(count.get() + 1);
                    log.debug("Deleted file: {}, file last modified time: {}", f.getAbsolutePath(), lastModified);
                }
            }
        });

        String path = Paths.get(location).toAbsolutePath().toString();
        return I18nUtils.msg("system.scheduling.task.multipart.location.clean.message", path, count, minusTime);
    }
}
