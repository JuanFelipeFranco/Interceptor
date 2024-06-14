package com.felipe.Interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Autowired
    @Qualifier("timeInterceptor")
    private HandlerInterceptor timeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        //registry.addInterceptor(timeInterceptor).addPathPatterns("/app/bar"); //solo se ejecuta en bar el interceptor
        registry.addInterceptor(timeInterceptor).addPathPatterns("/app/bar", "/app/foo"); //solo se ejecuta en bar y foo el interceptor
        //registry.addInterceptor(timeInterceptor).excludePathPatterns("/app/bar", "/app/foo"); //va a excluir  en bar y foo el interceptor, solo se ejecuta en baz
        //registry.addInterceptor(timeInterceptor).excludePathPatterns("/app/**"); //se va a ejecutar en todos

    }
}
