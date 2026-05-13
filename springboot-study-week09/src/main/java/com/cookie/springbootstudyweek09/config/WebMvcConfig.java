package com.cookie.springbootstudyweek09.config;

import com.cookie.springbootstudyweek09.interceptor.AuthInterceptor;
import com.cookie.springbootstudyweek09.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final LogInterceptor logInterceptor;
    private final AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器的执行顺序，和代码写在哪里无关，取决于 order 的值，值越小优先级越高
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/api/**")
                .order(2);

        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/api/**")
                .order(1);
    }
}
