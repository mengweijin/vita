package com.github.mengweijin.vita.monitor.controller;

import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.sse.SseConnector;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author mengweijin
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/monitor/sse")
public class SseController {

    private SseConnector sseConnector;

    /**
     * @return 建立SSE连接
     */
    @GetMapping(value = "/connect", produces = "text/event-stream;charset=UTF-8")
    public SseEmitter connect() {
        String username = LoginHelper.getLoginUser().getUsername();
        return sseConnector.connect(username);
    }

}
