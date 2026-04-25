package com.github.mengweijin.vita;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 注意 1：SDK 化引入时，@ComponentScan 和 @MapperScan 需要增加你自己工程的包扫描路径。
 * 注意 2：@MapperScan 路径需要限制只能扫描 mapper 目录下的文件。
 *
 * @author mengweijin
 */
@Slf4j
@ComponentScan(basePackages = {"com.github.mengweijin.vita"})
@MapperScan(basePackages = {"com.github.mengweijin.vita.**.mapper"})
@SpringBootApplication
public class VitaAdminApplication {

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();
            SpringApplication.run(VitaAdminApplication.class, args);
            long end = System.currentTimeMillis();
            log.info("-----------------------------------------------------------------------");
            log.info("----------- Started Vita in {} seconds", (end - start) / 1000D);
            log.info("----------- Vita startup success!");
            log.info("-----------------------------------------------------------------------");
        } catch (Exception e) {
            log.error("----------------------------------------------------------------------");
            log.error("---------- Vita startup failed!");
            log.error("----------------------------------------------------------------------");
        }
    }
}
