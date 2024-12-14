package com.github.mengweijin.vitality.framework.satoken;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器
 *
 * @author mengweijin
 * @since 2023/7/2
 */
@Configuration
public class SaTokenConfig implements WebMvcConfigurer {

    /**
     * 注册sa-token的拦截器，打开注解式鉴权功能
     * // 指定一条 match 规则。拦截的 path 列表，可以写多个
     * SaRouter.match("/**")
     * // 要执行的校验动作，可以写完整的 lambda 表达式
     * .check(r -> StpUtil.checkLogin());
     * <p>
     * knife4j:
     *      "/doc.html", "/webjars/**", "/v3/api-docs/**"
     * @param registry registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，定义详细认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                    SaRouter.match("/**").check(r -> StpUtil.checkLogin());
                    // 根据路由划分模块，不同模块不同鉴权
                    //SaRouter.match("/doc.html", r -> StpUtil.checkPermission("tool:api:view"));
                }))
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/vitality/**", "/doc.html", "/webjars/**", "/v3/api-docs/**");
    }

}
