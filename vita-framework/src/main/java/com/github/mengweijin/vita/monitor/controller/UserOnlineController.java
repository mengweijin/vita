package com.github.mengweijin.vita.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.session.SaTerminalInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.util.AESUtils;
import com.github.mengweijin.vita.system.domain.vo.SaSessionVO;
import com.github.mengweijin.vita.system.domain.vo.SaTerminalInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@RestController
@RequestMapping("/monitor/user-online")
public class UserOnlineController {

    private static final String LOG_TITLE = "在线用户";

    @SaCheckPermission("monitor:userOnline:select")
    @GetMapping("/page")
    public IPage<SaSessionVO> page(Page<SaSessionVO> page, @RequestParam(value = "keywords", defaultValue = "") String keywords) {
        long size = page.getSize();
        long start = (page.getCurrent() - 1) * size;

        List<SaSessionVO> dtoList = new ArrayList<>();

        // 分页获取所有已登录的会话id。size = -1 时，为获取所有。
        List<String> sessionIdList = StpUtil.searchSessionId(keywords, (int) start, (int) size, false);
        for (String sessionId : sessionIdList) {
            // 根据会话id，查询对应的 SaSession 对象，此处一个 SaSession 对象即代表一个登录的账号
            SaSession session = StpUtil.getSessionBySessionId(sessionId);
            dtoList.add(new SaSessionVO(session));
        }

        // 获取所有已登录的会话id
        List<String> allSessionIdList = StpUtil.searchSessionId("", 0, -1, false);
        page.setTotal(allSessionIdList.size());
        page.setRecords(dtoList);
        return page;
    }

    @SaCheckPermission("monitor:userOnline:select")
    @GetMapping("/terminal-list/{sessionId}")
    public List<SaTerminalInfo> tokenSignList(@PathVariable("sessionId") String sessionId) {
        SaSession session = StpUtil.getSessionBySessionId(sessionId);
        // 查询这个账号都在哪些设备登录了
        return session.getTerminalList();
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.OFFLINE)
    @SaCheckPermission("monitor:userOnline:kickOut")
    @PostMapping("/kick-out-by-username/{username}")
    public R<Void> kickOutByLoginId(@PathVariable("username") String loginId) {
        // 强制指定账号注销下线
        StpUtil.kickout(loginId);
        return R.ok();
    }

    @Log(title = LOG_TITLE, operationType = EOperationType.OFFLINE)
    @SaCheckPermission("monitor:userOnline:kickOut")
    @PostMapping("/kick-out-by-token")
    public R<Void> kickOutByToken(@RequestBody SaTerminalInfoVO vo) {
        // 强制指定 Token 注销下线
        StpUtil.kickoutByTokenValue(AESUtils.getAES().decryptStr(vo.getEncryptTokenValue()));
        return R.ok();
    }

}