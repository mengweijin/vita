package com.github.mengweijin.vita.framework.repeatsubmit.inteceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.mengweijin.vita.framework.domain.P;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.repeatsubmit.RepeatSubmit;
import com.github.mengweijin.vita.framework.util.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.reflect.Method;

/**
 * 防止重复提交拦截器
 */
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (handler instanceof HandlerMethod handlerMethod) {
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request, annotation)) {
                    R<Void> r = R.fail(HttpStatus.BAD_REQUEST.value(), annotation.message());
                    ServletUtils.write(response, P.getObjectMapperWrapper().writeValueAsString(r));
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证是否重复提交由子类实现具体地防重复提交的规则
     *
     * @param request HttpServletRequest
     * @param repeatSubmit RepeatSubmit
     * @return boolean
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit repeatSubmit) throws JsonProcessingException;
}
