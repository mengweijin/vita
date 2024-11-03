package com.github.mengweijin.vitality.system.controller;

import com.github.mengweijin.vitality.framework.sse.SseConnector;
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
@RequestMapping("/sse")
public class SseController {

    private SseConnector sseConnector;

    /**
     * @param token token
     * @return SseEmitter
     */
    @GetMapping("/connect")
    public SseEmitter connect(String token) {
        return sseConnector.connect(token);
    }

}
