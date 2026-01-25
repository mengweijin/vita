package com.github.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 *
 * @author mengweijin
 * @since 2026/1/18
 */
@Slf4j
@Component
public class DemoApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("Run DemoApplicationRunner......");
    }
}
