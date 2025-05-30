package com.github.mengweijin.vita.monitor.domain.vo.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.dromara.hutool.extra.management.HostInfo;
import org.dromara.hutool.extra.management.OsInfo;
import org.dromara.hutool.extra.management.oshi.OshiUtil;

import java.io.Serializable;

/**
 * @author mengweijin
 * @since 2023/6/8
 */
@Data
public class ServerInfoVO implements Serializable {

    @JsonIgnore
    private HostInfo hostInfo = new HostInfo();

    private String manufacturer = OshiUtil.getSystem().getManufacturer();

    private String hostName = hostInfo.getName();

    private String hostAddress = hostInfo.getAddress();

    private String operationSystem = OshiUtil.getOs().toString();

    private String operationSystemArch = new OsInfo().getArch();

}
