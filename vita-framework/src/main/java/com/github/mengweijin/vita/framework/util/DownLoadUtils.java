package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.io.IoUtil;
import cn.hutool.v7.core.io.file.FileUtil;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.system.domain.entity.FileDO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

/**
 * All contentType refer to MimeTypeMappings.properties in tomcat.
 * {@link org.springframework.http.MediaType}
 * {@link cn.hutool.v7.http.meta.ContentType}
 *
 * @author mengweijin
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DownLoadUtils {

    public static void download(HttpServletRequest request, HttpServletResponse response, Supplier<FileDO> supplier) {
        write(request, response, true, supplier);
    }

    public static void preview(HttpServletRequest request, HttpServletResponse response, Supplier<FileDO> supplier) {
        write(request, response, false, supplier);
    }

    /**
     * 文件下载，支持断点续传
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param supplier 函数式接口，需要开发者自定义逻辑。返回一个 InputStream 流对象。
     */
    private static void write(HttpServletRequest request, HttpServletResponse response, boolean isAttachment, Supplier<FileDO> supplier) {
        FileDO fileDO = supplier.get();
        String fileName = fileDO.getName();

        File file = FileUtil.file(fileDO.getStoragePath());
        if(!file.exists()) {
            throw new ServerException("No file exists in storage path " + fileDO.getStoragePath());
        }

        String rangeHeader = request.getHeader(HttpHeaders.RANGE);
        if (rangeHeader == null) {
            ServletUtils.write(request, response, file, fileName, isAttachment);
            return;
        }

        writeRange(request, response, rangeHeader, file, fileName, isAttachment);
    }

    /**
     * 在深入 {@link HttpRange} 之前，有必要了解其背后的 HTTP 协议标准（RFC 7233）。
     * 客户端请求： 客户端通过 Range 头来指定请求的范围。
     * 格式： Range: <unit>=<range-start>-<range-end>
     * <unit>： 通常是 bytes（字节）。
     * <range-start> 和 <range-end>： 指定范围的起始和结束位置（从0开始计数）。结束位置可以省略，表示直到资源末尾。
     * 示例：
     * Range: bytes=0-499 -> 请求前500个字节。
     * Range: bytes=500-999 -> 请求第500到第999个字节。
     * Range: bytes=500- -> 请求从第500个字节到文件末尾的所有字节。
     * Range: bytes=-500 -> 请求最后500个字节。
     * Range: bytes=0-0 -> 请求第一个字节。
     * Range: bytes=0-499, 1000-1499 -> 请求多个范围（第一个500字节和第二个500字节）。
     * 服务端响应：
     * 如果支持范围请求，服务端会返回状态码 206 Partial Content。
     * 响应头中包含 Content-Range，告知客户端返回的是哪个范围以及资源的总大小。格式：Content-Range: bytes <start>-<end>/<total-size>
     * 如果请求的范围无效（例如超出资源大小），服务端会返回状态码 416 Range Not Satisfiable。
     * <p>
     * 前端请求方式：
     * 携带 header: 'Range': `bytes=${startByte}-`
     * 比如：Range: bytes=1-500
     *
     * @param rangeHeader  rangeHeader
     * @param response     HttpServletResponse
     * @param file         File
     * @param isAttachment true/false。是否为浏览器强制文件下载，而不是尝试显示它。
     */
    private static void writeRange(HttpServletRequest request, HttpServletResponse response, String rangeHeader, File file, String fileName, boolean isAttachment) {
        try {
            // 解析 Range 头
            List<HttpRange> ranges = HttpRange.parseRanges(rangeHeader);

            // 只处理单个范围请求，多数播放器也这么请求。（暂不支持多范围请求）
            if (ranges.size() != 1) {
                // 如果需要处理多范围请求，逻辑会更复杂，需要返回 multipart/byteranges 内容
                response.sendError(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.value(), HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE.getReasonPhrase());
                return;
            }

            HttpRange range = ranges.get(0);
            FileSystemResource fileSystemResource = new FileSystemResource(file);

            // ResourceRegion 传入 resource 需要支持随机访问特性
            ResourceRegion resourceRegion = range.toResourceRegion(fileSystemResource);

            // 设置响应头
            ServletUtils.initRangeHeader(request, response, fileName, resourceRegion, isAttachment);

            // 响应资源
            IoUtil.copy(fileSystemResource.getInputStream(), response.getOutputStream());
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }


}
