package com.github.mengweijin.vita.framework.util;

import cn.hutool.v7.core.io.IoUtil;
import cn.hutool.v7.core.io.file.FileUtil;
import cn.hutool.v7.core.math.NumberUtil;
import cn.hutool.v7.core.net.url.UrlEncoder;
import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.core.text.StrUtil;
import cn.hutool.v7.http.meta.HttpHeaderUtil;
import cn.hutool.v7.http.server.servlet.ServletUtil;
import cn.hutool.v7.http.useragent.UserAgent;
import cn.hutool.v7.http.useragent.UserAgentUtil;
import com.github.mengweijin.vita.framework.exception.ServerException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

/**
 * @author
 * Servlet工具类
 **/
public class ServletUtils extends ServletUtil {

    /**
     * 获取UserAgent
     */
    public static UserAgent getUserAgent(HttpServletRequest request){
        return UserAgentUtil.parse(request.getHeader("User-Agent"));
    }

    /**
     * 获取UserAgent
     */
    public static UserAgent getUserAgent(){
        return getUserAgent(getRequest());
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue) {
        return CharSequenceUtil.defaultIfBlank(getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name) {
        String parameter = getParameter(name, null);
        return parameter == null ? null : NumberUtil.parseInt(parameter);
    }

    public static Integer getParameterToInt(String name, int defaultValue) {
        String parameter = getParameter(name, String.valueOf(defaultValue));
        return NumberUtil.parseInt(parameter);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取servletContext
     */
    public static ServletContext getServletContext() {
        return getSession().getServletContext();
    }

    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 是否包含某个参数名
     */
    public static boolean containsParameter(HttpServletRequest request, String parameterName){
        Enumeration<String> enumeration = request.getParameterNames();
        while(enumeration.hasMoreElements()) {
            if(enumeration.nextElement().equals(parameterName)){
                return true;
            }
        }
        return false;
    }

    public static void write(HttpServletRequest request, HttpServletResponse response, File file, String fileName, boolean isAttachment) {
        fileName = StrUtil.defaultIfBlank(fileName, file.getName());
        BufferedInputStream in = FileUtil.getInputStream(file);
        write(request, response, in, fileName, isAttachment);
    }

    public static void write(HttpServletRequest request, HttpServletResponse response, InputStream in, String fileName, boolean isAttachment) {
        OutputStream out = null;
        try {
            initHeader(request, response, fileName, isAttachment);
            out = response.getOutputStream();
            IoUtil.copy(in, out);
        } catch (IOException e) {
            throw new ServerException(e);
        } finally {
            IoUtil.closeQuietly(out);
            IoUtil.closeQuietly(in);
        }
    }

    public static void write(final HttpServletResponse response, final String text) {
        write(response, text, MediaType.APPLICATION_JSON_VALUE);
    }

    public static void initHeader(HttpServletRequest request, HttpServletResponse response, String fileName, boolean isAttachment) {
        String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        if(StrUtil.isNotBlank(fileName)) {
            contentType = request.getServletContext().getMimeType(fileName);
        }

        response.setHeader(HttpHeaders.CONTENT_TYPE, contentType);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // fileName表示下载的文件名
        if (isAttachment) {
            // 指示客户端应将响应体作为附件保存到本地，而不是尝试显示它。
            // Content-Disposition: attachment
            // Content-Disposition: attachment; filename="example.pdf"
            String attachmentDisposition = HttpHeaderUtil.createAttachmentDisposition(fileName, StandardCharsets.UTF_8);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, attachmentDisposition);
        } else {
            String encodeFileName = UrlEncoder.encodeAll(fileName, StandardCharsets.UTF_8);
            // Content-Type: video/mp4
            // Content-Disposition: inline; filename="movie.mp4"
            // 指示客户端应尝试在浏览器窗口内部显示内容（如：pdf, mp4 等），除非浏览器无法直接显示，才触发文件下载。
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + encodeFileName);
        }
    }

    /**
     * 设置 Range 各种响应头
     * <p>
     * Content-Disposition 是一个 HTTP 响应头（有时也用于请求头，但较少见），它主要用于指示客户端（通常是浏览器）应该如何展示服务器返回的响应内容。
     * 其核心作用是：控制内容是应该内联显示在浏览器中（Inline），还是应该作为附件被下载（Attachment）。
     * <p>
     * Content-Disposition: inline
     * 含义： 指示客户端应尝试在浏览器窗口内部显示内容。
     * 行为： 如果浏览器有能力处理该内容类型（如 text/html, image/jpeg, application/pdf, video/mp4, Content-Type: audio/mpeg），它就会直接显示。如果没有能力处理，则会触发下载。
     * 示例： 访问一个普通的网页（HTML）或查看一张图片时，服务器返回的就是 inline 模式。
     * <p>
     * Content-Type: video/mp4
     * Content-Disposition: inline; filename="movie.mp4"
     * <p>
     * Content-Type: audio/mpeg
     * Content-Disposition: inline; filename="music.mp3"
     * <p>
     * Content-Disposition: attachment
     * Content-Disposition: attachment; filename="example.pdf"
     * 含义： 指示客户端应将响应体作为附件保存到本地，而不是尝试显示它。
     * 行为： 浏览器会总是弹出“文件下载”对话框，或直接在浏览器的下载管理器中进行下载，而不会尝试解析和显示内容。即使用户的浏览器能够打开这种文件类型（如 PDF），它也会被下载。
     * filename 参数：这是一个可选的参数，用于建议浏览器保存文件时使用的默认文件名。这个文件名可以包含扩展名，浏览器通常会据此决定文件的保存类型和图标。
     *
     * @param resourceRegion resourceRegion
     * @param response       HttpServletResponse
     * @param isAttachment   true/false。是否为浏览器强制文件下载，而不是尝试显示它。
     */
    public static void initRangeHeader(HttpServletRequest request, HttpServletResponse response, String fileName, ResourceRegion resourceRegion, boolean isAttachment) {
        try {
            fileName = StrUtil.defaultIfBlank(fileName, resourceRegion.getResource().getFilename());

            // 设置公共 header
            initHeader(request, response, fileName, isAttachment);

            // 总大小
            long contentLength = resourceRegion.getResource().contentLength();
            // 计算范围信息
            long start = resourceRegion.getPosition();
            long end = start + resourceRegion.getCount() - 1;

            //http状态码要为206：表示获取部分内容
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
            //支持断点续传，获取部分字节内容：
            response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");

            // 设置本次响应的内容长度
            response.setContentLengthLong(resourceRegion.getCount());
            // Content-Length，本次下载的长度
            response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(resourceRegion.getCount()));
            // Content-Range，格式为：bytes [start]-[end]/[total]
            String contentRange = StrUtil.format("bytes {}-{}/{}", start, end, contentLength);
            response.setHeader(HttpHeaders.CONTENT_RANGE, contentRange);
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }
}
