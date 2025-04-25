package com.github.mengweijin.vita.monitor.domain.vo.application;

import lombok.Data;
import org.dromara.hutool.extra.management.oshi.CpuInfo;
import org.dromara.hutool.extra.management.oshi.OshiUtil;

import java.io.Serializable;

/**
 * @author mengweijin
 * @since 2023/6/8
 */
@Data
public class CpuInfoVO implements Serializable {

    /**
     * CPU核心数
     */
    private Integer cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double user;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    /**
     * CPU型号信息
     */
    private String cpuModel;

    public CpuInfoVO() {
        CpuInfo cpuInfo = OshiUtil.getCpuInfo();
        this.cpuNum = cpuInfo.getCpuNum();
        this.total = cpuInfo.getToTal();
        this.sys = cpuInfo.getSys();
        this.user = cpuInfo.getUser();
        this.wait = cpuInfo.getWait();
        this.free = cpuInfo.getFree();
        this.cpuModel = cpuInfo.getCpuModel();
    }
}
