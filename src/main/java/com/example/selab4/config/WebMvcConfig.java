package com.example.selab4.config;

import com.example.selab4.Interceptor.LoginIntercepter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConfig implements WebMvcConfigurer {
    public final static String SESSION_KEY = "user";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/index.html");    // 登录请求无需拦截
    }
}
