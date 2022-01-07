package com.github.mengweijin.quickboot.auth.async;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.useragent.UserAgent;
import com.github.mengweijin.quickboot.auth.entity.LoginLog;
import com.github.mengweijin.quickboot.auth.enums.LoginStatus;
import com.github.mengweijin.quickboot.auth.enums.LoginType;
import com.github.mengweijin.quickboot.auth.service.LoginLogService;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quickboot.framework.util.ServletUtils;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;

/**
 * @author mengweijin
 * @date 2022/1/2
 */
@Slf4j
@Component
public class LoginLogTask {

    @Async
    public Future<String>
    addFailureLoginLog(HttpServletRequest request, String username, String message, String ip) {
        return this.addLoginLog(request, username, message, LoginType.LOGIN, LoginStatus.FAILURE, ip);
    }

    @Async
    public Future<String> addSuccessLoginLog(HttpServletRequest request, String username, String ip) {
        return this.addLoginLog(request, username, null, LoginType.LOGIN, LoginStatus.SUCCESS, ip);
    }

    @Async
    public Future<String> addLogoutLog(HttpServletRequest request, String username, String ip) {
        return this.addLoginLog(request, username, null, LoginType.LOGOUT, LoginStatus.SUCCESS, ip);
    }


    private Future<String> addLoginLog(HttpServletRequest request, String username, String message, LoginType loginType, LoginStatus loginStatus, String ip) {
        try {
            log.debug("Add login log.");
            UserAgent userAgent = ServletUtils.getUserAgent(request);
            if(StrUtil.isBlank(ip)) {
                ip = ServletUtils.getClientIP(request);
            }

            LoginLog loginLog = new LoginLog();
            loginLog.setUsername(username);
            loginLog.setLoginType(loginType);
            loginLog.setLoginStatus(loginStatus);
            loginLog.setIp(ip);
            loginLog.setOs(userAgent.getOs().getName());
            loginLog.setBrowser(userAgent.getBrowser().getName());
            loginLog.setMessage(message);

            LoginLogService loginLogService = SpringUtils.getBean(LoginLogService.class);
            loginLogService.save(loginLog);
        } catch (Throwable e) {
            log.error("Add Login Log Exception!", e);
            return new AsyncResult<>(Const.FAILURE);
        }

        return new AsyncResult<>(Const.SUCCESS);
    }
}
