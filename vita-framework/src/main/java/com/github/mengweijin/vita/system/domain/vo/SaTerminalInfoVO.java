package com.github.mengweijin.vita.system.domain.vo;

import cn.dev33.satoken.session.SaTerminalInfo;
import com.github.mengweijin.vita.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vita.framework.jackson.sensitive.Sensitive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.date.DateUtil;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @author mengweijin
 * @since 2024/11/10
 */
@Data
@NoArgsConstructor
public class SaTerminalInfoVO {
    /**
     * 登录会话索引值 (该账号第几个登录的设备, 从 1 开始)
     */
    private int index;

    /**
     * Token 值
     */
    @Sensitive(strategy = ESensitiveStrategy.DEFAULT)
    private String tokenValue;

    /**
     * Token 加密值
     */
    @Sensitive(strategy = ESensitiveStrategy.ENCRYPT)
    private String encryptTokenValue;

    /**
     * 所属设备类型
     */
    private String deviceType;

    /**
     * 登录设备唯一标识，例如：kQwIOrYvnXmSDkwEiFngrKidMcdrgKorXmSDkwEiFngrKidM
     */
    private String deviceId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public SaTerminalInfoVO(SaTerminalInfo terminalInfo) {
        this.index = terminalInfo.getIndex();
        this.tokenValue = terminalInfo.getTokenValue();
        this.deviceType = terminalInfo.getDeviceType();
        this.deviceId = terminalInfo.getDeviceId();
        this.createTime = DateUtil.toLocalDateTime(Instant.ofEpochMilli(terminalInfo.getCreateTime()));
    }
}
