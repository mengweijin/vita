package com.github.mengweijin.vita.framework.satoken;

import cn.dev33.satoken.exception.SaTokenContextException;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import com.github.mengweijin.vita.system.constant.VitaConst;
import com.github.mengweijin.vita.system.domain.vo.user.UserSessionVO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

/**
 * {@link StpUtil}
 * {@link cn.dev33.satoken.stp.StpLogic}
 * @author mengweijin
 */
@SuppressWarnings({"unused"})
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHelper {

    /**
     * 获取登录用户
     */
    public static UserSessionVO getSessionUser() {
        SaSession saSession;
        try {
            saSession = StpUtil.getTokenSession();
        } catch (SaTokenContextException e) {
            return null;
        }
        if(saSession == null) {
            return null;
        }
        return (UserSessionVO) saSession.get(SaSession.USER);
    }

    public static Long getSessionUserId() {
        return Optional.ofNullable(getSessionUser()).map(UserSessionVO::getUserId).orElse(null);
    }

    public static String getSessionUsername() {
        return Optional.ofNullable(getSessionUser()).map(UserSessionVO::getUsername).orElse(null);
    }

    public static String getToken() {
        return StpUtil.getTokenValue();
    }

    public static List<String> getPermissionList() {
        return StpUtil.getPermissionList();
    }

    public static List<String> getRoleList() {
        return StpUtil.getRoleList();
    }

    /**
     * 是否为管理员
     *
     * @return boolean
     */
    public static boolean isAdmin() {
        UserSessionVO loginUser = getSessionUser();
        if(loginUser != null) {
            return isAdmin(loginUser.getUserId(), loginUser.getUsername());
        }
        return false;

    }

    /**
     * 是否为管理员
     *
     * @return boolean
     */
    public static boolean isAdmin(Long userId, String username) {
        return VitaConst.USER_ADMIN_ID == userId && VitaConst.USER_ADMIN_USERNAME.equals(username);
    }
}
