package com.github.mengweijin.quickboot.jpa.page;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Meng Wei Jin
 **/
@ControllerAdvice
public class PageResponseBodyAdvice implements ResponseBodyAdvice<Page> {

    /**
     * 判断是否支持要转换的参数类型
     *
     * @param methodParameter methodParameter
     * @param aClass aClass
     * @return boolean
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return Page.class.isAssignableFrom(methodParameter.getParameterType());
    }

    /**
     * 当支持后进行相应的转换
     */
    @Override
    public Page beforeBodyWrite(Page page, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        Pager pager = new Pager<>();
        // 前台分页一般从1开始，Jpa分页从0开始计数，这里做个转换
        pager.setCurrent(page.getNumber() + 1)
              .setSize(page.getSize())
              .setTotal(page.getTotalElements())
              .setDataList(page.getContent());
        return pager;
    }
}
