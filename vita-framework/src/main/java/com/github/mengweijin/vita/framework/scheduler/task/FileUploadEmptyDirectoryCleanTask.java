package com.github.mengweijin.vita.framework.scheduler.task;

import cn.hutool.v7.core.io.file.FileUtil;
import com.github.mengweijin.vita.framework.properties.VitaProperties;
import com.github.mengweijin.vita.framework.scheduler.ISchedulingTask;
import com.github.mengweijin.vita.monitor.domain.entity.SchedulingTaskDO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

/**
 * 文件上传存储路径下的空文件夹清理任务，防止文件删除后，遗留的空文件夹太多。
 * @author mengweijin
 * @since 2026/1/3
 */
@Slf4j
@Component
@AllArgsConstructor
public class FileUploadEmptyDirectoryCleanTask implements ISchedulingTask {

    private VitaProperties vitaProperties;

    @Override
    public String run(SchedulingTaskDO task, Map<?, ?> args) {
        String uploadPath = vitaProperties.getUploadPath();
        File file = FileUtil.file(uploadPath);
        FileUtil.cleanEmpty(file);
        return "The empty file directory were deleted.";
    }
}
