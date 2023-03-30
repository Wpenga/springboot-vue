package com.system.springboot.config;

import com.system.springboot.common.interceptor.Jwtlnterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//注册拦截器
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtlnterceptor())
                .addPathPatterns("/**")    // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns("/user/login","/user/register","/**/export","/**/impost","/file/**");//不拦截
    }
    @Bean
    public  Jwtlnterceptor jwtlnterceptor(){
        return  new Jwtlnterceptor();
    }
}
