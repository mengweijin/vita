package com.github.mengweijin.vita.monitor.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.github.mengweijin.vita.monitor.domain.vo.application.MonitorVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mengweijin
 * @since 2022/10/30
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    @SaCheckPermission("monitor:server:view")
    @GetMapping("/info")
    public MonitorVO serverInfo() {
        return new MonitorVO();
    }

}