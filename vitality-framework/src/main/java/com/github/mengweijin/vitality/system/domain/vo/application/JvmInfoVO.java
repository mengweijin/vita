package com.github.mengweijin.vitality.system.domain.vo.application;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.dromara.hutool.core.date.DateFormatPool;
import org.dromara.hutool.core.date.TimeUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.extra.management.JavaRuntimeInfo;
import org.dromara.hutool.extra.management.JvmInfo;
import org.dromara.hutool.extra.management.RuntimeInfo;
import org.dromara.hutool.extra.management.UserInfo;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;

/**
 * @author mengweijin
 * @since 2023/6/8
 */
@Data
public class JvmInfoVO {
    private String jvmName;
    private String jvmVersion;
    private String jvmVendor;
    private String javaHome;
    private String projectHome;

    /**
     * JVM的启动时间
     */
    @JsonFormat(pattern = DateFormatPool.NORM_DATETIME_PATTERN)
    private LocalDateTime startTime;
    /**
     * JVM的运行时间（单位：秒）
     */
    private Long runTimeSeconds;
    /**
     * 当前JVM运行的PID
     */
    private Long currentProcessIdentifier;

    private String maxMemory;
    private String totalMemory;
    private String freeMemory;
    private String usableMemory;

    private String startArgs;

    public JvmInfoVO() {
        JvmInfo jvmInfo = new JvmInfo();
        this.jvmName = jvmInfo.getName();
        this.jvmVersion = jvmInfo.getVersion();
        this.jvmVendor = jvmInfo.getVendor();
        this.javaHome = new JavaRuntimeInfo().getHomeDir();
        this.projectHome = new UserInfo().getCurrentDir();

        this.startTime = TimeUtil.of(ManagementFactory.getRuntimeMXBean().getStartTime());
        this.runTimeSeconds = TimeUtil.between(this.startTime, LocalDateTime.now()).toSeconds();
        this.currentProcessIdentifier = Long.parseLong(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);

        RuntimeInfo runtimeInfo = new RuntimeInfo();
        this.maxMemory = FileUtil.readableFileSize(runtimeInfo.getMaxMemory());
        this.totalMemory = FileUtil.readableFileSize(runtimeInfo.getTotalMemory());
        this.freeMemory = FileUtil.readableFileSize(runtimeInfo.getFreeMemory());
        this.usableMemory = FileUtil.readableFileSize(runtimeInfo.getUsableMemory());

        this.startArgs = ManagementFactory.getRuntimeMXBean().getInputArguments().toString();
    }
}
