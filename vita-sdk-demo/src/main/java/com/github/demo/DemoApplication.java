package com.github.demo;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author mengweijin
 */
@Slf4j
@ComponentScan(basePackages = { "com.github.mengweijin.vita", "com.github.demo" })
@MapperScan(basePackages = { "com.github.mengweijin.vita.**.mapper", "com.github.demo.**.mapper" })
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		try {
            long start = System.currentTimeMillis();
			SpringApplication.run(DemoApplication.class, args);
            long end = System.currentTimeMillis();
            log.info("-----------------------------------------------------------------------");
            log.info("----------- Vita startup success!");
            log.info("----------- Started Vita in {} seconds", (end - start) / 1000D);
            log.info("-----------------------------------------------------------------------");
		} catch (Exception e) {
            log.error("----------------------------------------------------------------------");
            log.error("---------- Vita startup failed!");
            log.error("----------------------------------------------------------------------");
		}
	}
}
