package com.github.mengweijin.vita.monitor.domain.vo.application;

import cn.hutool.v7.core.io.file.FileUtil;
import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.extra.management.oshi.OshiUtil;
import lombok.Data;
import oshi.software.os.OSFileStore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @since 2023/6/8
 */
@Data
public class DiskInfoVO implements Serializable {

    /**
     * 卷名称。如：C:\
     */
    private String mountName;
    /**
     * 磁盘格式。如：NTFS
     */
    private String diskFormat;
    /**
     * 磁盘名称。如：本地磁盘(C:)
     */
    private String diskName;
    /**
     * 磁盘总容量
     */
    private String total;
    /**
     * 磁盘剩余容量
     */
    private String free;
    /**
     * 磁盘已使用容量
     */
    private String used;
    /**
     * 磁盘使用率。如：30%
     */
    private String usageRate;

    public static List<DiskInfoVO> init() {
        List<DiskInfoVO> dataList = new ArrayList<>();
        List<OSFileStore> fileStores = OshiUtil.getOs().getFileSystem().getFileStores();
        fileStores.forEach(fs -> {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            DiskInfoVO info = new DiskInfoVO();
            info.setMountName(fs.getMount());
            info.setDiskFormat(fs.getType());
            info.setDiskName(fs.getName());
            info.setTotal(FileUtil.readableFileSize(total));
            info.setFree(FileUtil.readableFileSize(free));
            info.setUsed(FileUtil.readableFileSize(used));
            BigDecimal usageRate = NumberUtil.mul(NumberUtil.div(used, total, 4), 100).stripTrailingZeros();
            info.setUsageRate(usageRate + "%");
            dataList.add(info);
        });
        return dataList;
    }

}
