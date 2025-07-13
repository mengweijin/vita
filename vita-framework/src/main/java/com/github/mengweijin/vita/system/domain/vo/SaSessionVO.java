package com.github.mengweijin.vita.system.domain.vo;

import cn.dev33.satoken.session.SaSession;
import com.github.mengweijin.vita.framework.jackson.sensitive.ESensitiveStrategy;
import com.github.mengweijin.vita.framework.jackson.sensitive.Sensitive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.hutool.core.date.TimeUtil;

import java.time.LocalDateTime;

/**
 * @author mengweijin
 * @since 2024/11/10
 */
@Data
@NoArgsConstructor
public class SaSessionVO {

    /**
     * 此 SaSession 的 id
     */
    private String id;

    /**
     * 此 SaSession 的 类型
     */
    private String type;

    /**
     * 所属 loginType
     */
    private String loginType;

    /**
     * 所属 loginId （当此 SaSession 属于 Account-Session 时，此值有效）
     */
    private Object loginId;

    /**
     * 所属 Token （当此 SaSession 属于 Token-Session 时，此值有效）
     */
    @Sensitive(strategy = ESensitiveStrategy.DEFAULT)
    private String token;

    /**
     * 此 SaSession 的创建时间（13位时间戳）转为 LocalDateTime
     */
    private LocalDateTime createTime;

    /**
     * 当前账号历史总计登录设备数量（当此 SaSession 属于 Account-Session 时，此值有效）
     */
    private int historyTerminalCount;

    public SaSessionVO(SaSession session) {
        this.id = session.getId();
        this.type = session.getType();
        this.loginType = session.getLoginType();
        this.loginId = session.getLoginId();
        this.token = session.getToken();
        this.createTime = TimeUtil.of(session.getCreateTime());
        this.historyTerminalCount = session.getHistoryTerminalCount();
    }

}
