package com.github.mengweijin.vita.monitor.domain.vo.application;

import lombok.Data;
import cn.hutool.v7.extra.management.oshi.OshiUtil;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PhysicalMemory;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @since 2023/6/8
 */
@Data
public class MemoryInfoVO implements Serializable {

    private String global;

    private String physical;

    private String virtual;

    public MemoryInfoVO() {
        GlobalMemory memory = OshiUtil.getMemory();
        global = memory.toString();
        physical = memory.getPhysicalMemory().stream().map(PhysicalMemory::toString)
                .collect(Collectors.joining("\n"));
        virtual = memory.getVirtualMemory().toString();
    }
}
