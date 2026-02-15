package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.http.meta.HttpHeaderUtil;
import com.github.mengweijin.vita.framework.exception.ServerException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fesod.sheet.FesodSheet;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 注解：@ExcelProperty(index = 2) {@link org.apache.fesod.sheet.annotation.ExcelProperty}
 *
 * @author mengweijin
 * @since 2022/11/20
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelUtils {

    /**
     * 读 Excel
     *
     * @param filePath 文件路径
     * @param cls      对象
     *
     */
    public static <T> List<T> read(String filePath, Class<T> cls) {
        return FesodSheet.read(filePath).head(cls).sheet().doReadSync();
    }

    /**
     * 读 Excel
     *
     * @param in  InputStream 对象
     * @param cls 对象
     *
     */
    public static <T> List<T> read(InputStream in, Class<T> cls) {
        return FesodSheet.read(in).head(cls).sheet().doReadSync();
    }

    /**
     * 读 Excel
     *
     * @param request 请求对象
     * @param cls     对象
     *
     */
    public static <T> List<T> read(HttpServletRequest request, Class<T> cls) {
        try {
            return read(request.getInputStream(), cls);
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    /**
     * 写数据到指定的 Excel 文件
     *
     * @param targetFile 文件
     * @param cls        cls
     *
     */
    public static <T> void write(Class<T> cls, List<T> list, File targetFile) {
        FesodSheet.write(targetFile, cls).sheet(0).doWrite(list);
    }

    /**
     * 写数据到指定的 Excel 文件
     *
     * @param out OutputStream
     * @param cls cls
     *
     */
    public static <T> void write(Class<T> cls, List<T> list, OutputStream out) {
        FesodSheet.write(out, cls).sheet(0).doWrite(list);
    }

    /**
     * 写 Excel
     *
     * @param response 响应对象
     * @param cls      cls
     *
     */
    public static <T> void write(String fileName, Class<T> cls, List<T> list, HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            String attachmentDisposition = HttpHeaderUtil.createAttachmentDisposition(fileName, StandardCharsets.UTF_8);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, attachmentDisposition);
            write(cls, list, response.getOutputStream());
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

}
