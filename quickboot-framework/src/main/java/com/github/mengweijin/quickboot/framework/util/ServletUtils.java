package com.github.mengweijin.quickboot.framework.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @author
 * Servlet工具类
 **/
public class ServletUtils extends ServletUtil {

    /**
     * session中存储的当前登录用户变量名
     */
    public static final String SESSION_USER = "SESSION_USER";

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
        return StrUtil.blankToDefault(getParameter(name), defaultValue);
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

    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string   待渲染的字符串
     */
    public static void renderString(HttpServletResponse response, String string) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().print(string);
    }

    public static void renderObject(HttpServletResponse response, Object object) throws IOException {
        ObjectMapper objectMapper = SpringUtils.getBean(ObjectMapper.class);
        renderString(response, objectMapper.writeValueAsString(object));
    }
}
