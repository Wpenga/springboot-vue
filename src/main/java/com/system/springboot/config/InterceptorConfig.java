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
                .excludePathPatterns("/user/login","/user/register","/**/export","/**/impost","/file/**")//不拦截
                .excludePathPatterns( "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.woff", "/**/*.ttf")  // 放行静态文件
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**", "/api", "/api-docs", "/api-docs/**");//放行swagger资源
    }
    @Bean
    public  Jwtlnterceptor jwtlnterceptor(){
        return  new Jwtlnterceptor();
    }
}
