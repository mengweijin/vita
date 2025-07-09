package com.github.mengweijin.vita.framework;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * @author mengweijin
 * @since 2023/6/6
 */
@Slf4j
@Component
@AllArgsConstructor
public class VitaApplicationRunner implements ApplicationRunner {

    private MultipartProperties multipartProperties;

    private SqlSessionFactory sqlSessionFactory;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.printDatabaseProductName();
        this.initMultipartTempLocation();
    }

    private void printDatabaseProductName() throws SQLException {
        Connection connection = SqlSessionUtils.getSqlSession(sqlSessionFactory).getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        String databaseProductName = metaData.getDatabaseProductName();
        log.info("Product name of the database is: {}", databaseProductName);
    }

    /**
     * 临时文件存储位置。避免临时文件被系统清理从而报错。
     * Linux 系统默认在 /tmp 目录，并且系统会定时清理。
     * Windows 系统默认应该在 C:\Users\AppData\Local\Temp 目录。
     * <p>
     * 为了解决临时文件被系统清理而导致系统报错，因此自定义指定到其它目录，并且需要在系统启动时初始化好目录，否则第一次上传会找不到文件夹而自动创建，但此时第一次文件上传实际没有上传到后端。
     * spring.servlet.multipart.location=${user.dir}/temp
     */
    private void initMultipartTempLocation() {
        String location = multipartProperties.getLocation();
        if(StrUtil.isNotBlank(location)) {
            File tempLocation = FileUtil.file(location);
            if(!tempLocation.exists()) {
                FileUtil.mkdir(location);
                log.info("Make multipart temp location: {}", location);
            } else {
                log.info("Multipart temp location already exists in location: {}", location);
            }
        }
    }

}
