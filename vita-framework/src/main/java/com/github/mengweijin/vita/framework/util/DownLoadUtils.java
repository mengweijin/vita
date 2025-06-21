package com.github.mengweijin.vita.framework.util;

import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.ServerException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.dromara.hutool.core.io.IoUtil;
import org.dromara.hutool.core.io.NioUtil;
import org.springframework.http.HttpHeaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

/**
 * All contentType refer to MimeTypeMappings.properties in tomcat.
 *
 * @author mengweijin
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DownLoadUtils {

    private static final String HEADER_RANGE_PREFIX = "bytes=";

    /**
     * 文件下载，断点续传
     *
     * @param fileId   文件 ID
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param function 函数式接口，需要开发者自定义逻辑。根据 fileId 返回一个 File 对象。
     */
    public static void download(Serializable fileId, HttpServletRequest request, HttpServletResponse response, Function<Serializable, File> function) {
        String range = request.getHeader(HttpHeaders.RANGE);
        if(range == null) {
            simpleDownload(function.apply(fileId), request, response);
        } else {
            rangeDownload(function.apply(fileId), request, response);
        }
    }

    public static void simpleDownload(File file, HttpServletRequest request, HttpServletResponse response) {
        try (FileInputStream in = new FileInputStream(file)) {
            simpleDownload(in, file.getName(), request, response);
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    public static void simpleDownload(InputStream in, String fileName, HttpServletRequest request, HttpServletResponse response) {
        try (OutputStream out = response.getOutputStream()) {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            // "multipart/form-data"
            String mimeType = request.getServletContext().getMimeType(fileName);
            response.setContentType(mimeType);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment;fileName=" + setFileName(request, fileName));
            IoUtil.copy(in, out);
        } catch (ClientAbortException e) {
            //捕获此异常表示用户停止下载
            log.warn("User canceled download.");
        } catch (IOException e) {
            throw new ServerException(e);
        } finally {
            IoUtil.closeQuietly(in);
        }
    }

    public static void rangeDownload(File file, HttpServletRequest request, HttpServletResponse response) {
        ContentRange range = getContentRange(file, request);

        setRangeDownloadHeader(range, request, response);

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            // 移动文件指针到指定位置（从指定位置开始读取文件）
            randomAccessFile.seek(range.getStart());

            WritableByteChannel outChannel = Channels.newChannel(response.getOutputStream());
            long length = range.getEnd() - range.getStart() + 1;
            NioUtil.copy(randomAccessFile.getChannel(), outChannel, 8192, length, null);

            log.debug("Download completed!");
        } catch (ClientAbortException e) {
            //捕获此异常表示用户停止下载
            log.info("User stopped download! start={}, end={}", range.getStart(), range.getEnd());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static String setFileName(HttpServletRequest request, String fileName) {
        String encodeFileName = fileName;
        final String agent = request.getHeader("USER-AGENT");
        if (agent != null) {
            if (agent.contains("Firefox")) {
                // 火狐浏览器
                encodeFileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            } else if (agent.contains("Chrome") || agent.contains("MSIE")) {
                // google浏览器 或 IE 浏览器
                encodeFileName = URLEncoder.encode(encodeFileName, StandardCharsets.UTF_8);
                encodeFileName = encodeFileName.replace("+", " ");
            } else {
                // 其它浏览器
                encodeFileName = URLEncoder.encode(encodeFileName, StandardCharsets.UTF_8);
            }
        }
        return encodeFileName;
    }

    private static ContentRange getContentRange(File file, HttpServletRequest request) {
        ContentRange contentRange = new ContentRange();

        long start = 0;
        long end = file.length() - 1;

        String range = request.getHeader(HttpHeaders.RANGE);
        if (range != null && range.startsWith(HEADER_RANGE_PREFIX)) {
            String[] ranges = range.substring(HEADER_RANGE_PREFIX.length()).split("-");
            //判断range的类型, 假如文件长度为8192字节
            if (ranges.length == 1) {
                //类型一：bytes=-1024
                if (range.startsWith(Const.DASH)) {
                    end = Long.parseLong(ranges[0]);
                }
                //类型二：bytes=1024-
                else if (range.endsWith(Const.DASH)) {
                    start = Long.parseLong(ranges[0]);
                }
            }
            //类型三：bytes=1024-2048
            else if (ranges.length == 2) {
                start = Long.parseLong(ranges[0]);
                end = Long.parseLong(ranges[1]);
            }

            contentRange.setRange(true);
        }

        contentRange.setFileName(file.getName());
        contentRange.setTotal(file.length());
        contentRange.setStart(start);
        contentRange.setEnd(end);

        log.debug(contentRange.toString());
        return contentRange;

    }

    /**
     * 设置各种响应头
     *
     * @param range    range
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    private static void setRangeDownloadHeader(ContentRange range, HttpServletRequest request, HttpServletResponse response) {
        //文件类型
        String contentType = request.getServletContext().getMimeType(range.getFileName());
        response.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        if (range.isRange()) {
            //http状态码要为206：表示获取部分内容
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            //支持断点续传，获取部分字节内容：
            response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
            //inline表示浏览器直接使用（attachment表示下载），fileName表示下载的文件名
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + setFileName(request, range.getFileName()));
            // Content-Length，本次下载的长度
            response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(range.getEnd() - range.getStart() + 1));
            // Content-Range，格式为：[要下载的开始位置]-[结束位置]/[文件总大小]
            response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes " + range.getStart() + "-" + range.getEnd() + "/" + range.getTotal());
        } else {
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + setFileName(request, range.getFileName()));
        }
    }

    @Data
    static class ContentRange implements Serializable {
        /**
         * 文件名称
         */
        private String fileName;

        /**
         * 文件总长度（大小）
         */
        private long total;

        /**
         * 第一个字节的位置
         */
        private long start;

        /**
         * 最后一个字节的位置
         */
        private long end;

        /**
         * 是否支持 range 下载
         */
        private boolean range;
    }
}
