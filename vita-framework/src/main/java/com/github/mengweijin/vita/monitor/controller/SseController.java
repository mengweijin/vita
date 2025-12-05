package com.github.mengweijin.vita.monitor.controller;

import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.satoken.LoginHelper;
import com.github.mengweijin.vita.framework.sse.SseConnector;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     * 订阅 SSE 连接
     * @return SseEmitter
     */
    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        String username = LoginHelper.getLoginUser().getUsername();
        return sseConnector.connect(username);
    }

    /**
     * 断开 SSE 连接
     */
    @PostMapping("/close")
    public R<Void> closeConnection() {
        String username = LoginHelper.getLoginUser().getUsername();
        sseConnector.disconnect(username);
        return R.ok();
    }
}
