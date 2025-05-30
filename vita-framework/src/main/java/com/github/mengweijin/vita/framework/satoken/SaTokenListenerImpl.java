package com.github.mengweijin.vita.framework.satoken;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.listener.SaTokenListenerForLog;
import cn.dev33.satoken.stp.parameter.SaLoginParameter;
import com.github.mengweijin.vita.framework.util.ServletUtils;
import com.github.mengweijin.vita.system.enums.ELoginType;
import com.github.mengweijin.vita.monitor.service.LogLoginService;
import com.github.mengweijin.vita.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Refer to {@link SaTokenListenerForLog}. It can only be reached if the operation succeeds.
 * @author mengweijin
 * @since 2023/7/8
 */
@Component
@AllArgsConstructor
public class SaTokenListenerImpl implements SaTokenListener {

    private LogLoginService logLoginService;

    private UserService userService;

    /**
     * 每次登录时触发。 这里的 loginId 即为用户登录名 username
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginParameter loginParameter) {
        logLoginService.addLoginLogAsync((String) loginId, ELoginType.LOGIN, null, ServletUtils.getRequest());
        userService.checkAndSendPasswordLongTimeNoChangeMessageAsync((String) loginId);
    }

    /** 每次注销时触发 */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        logLoginService.addLoginLogAsync((String) loginId, ELoginType.LOGOUT, null, ServletUtils.getRequest());
    }

    /** 每次被踢下线时触发 */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        logLoginService.addLoginLogAsync((String) loginId, ELoginType.KICK_OUT, null, ServletUtils.getRequest());
    }

    /** 每次被顶下线时触发 */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        logLoginService.addLoginLogAsync((String) loginId, ELoginType.REPLACED, null, ServletUtils.getRequest());
    }

    /** 每次被封禁时触发 */
    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {
        // ignore
    }

    /** 每次被解封时触发 */
    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {
        // ignore
    }

    /** 每次二级认证时触发 */
    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {
        // ignore
    }

    /** 每次退出二级认证时触发 */
    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {
        // ignore
    }

    /** 每次创建Session时触发 */
    @Override
    public void doCreateSession(String id) {
        // ignore
    }

    /** 每次注销Session时触发 */
    @Override
    public void doLogoutSession(String id) {
        // ignore
    }

    /** 每次Token续期时触发 */
    @Override
    public void doRenewTimeout(String loginType, Object loginId, String tokenValue, long timeout) {
        // ignore
    }
}
