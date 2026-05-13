package com.cookie.springbootstudyweek09.config;

import com.cookie.springbootstudyweek09.filter.AuthFilter;
import com.cookie.springbootstudyweek09.filter.CORSFilter;
import com.cookie.springbootstudyweek09.filter.CustomFilter;
import com.cookie.springbootstudyweek09.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;


@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter> customFilterRegistration() {
        // 创建 FilterRegistrationBean 对象
        FilterRegistrationBean<CustomFilter> registrationBean = new FilterRegistrationBean<>();
        // 设置过滤器
        registrationBean.setFilter(new CustomFilter());
        // 设置拦截的 URL 路径
        registrationBean.addUrlPatterns("/api/hello");
        // 设置过滤器的执行顺序
        registrationBean.setOrder(3);
        return registrationBean;
    }

    /**
     * 注册日志过滤器，优先级 1（最先执行）
     */
    @Bean
    public FilterRegistrationBean<LogFilter> logFilterRegistration() {
        FilterRegistrationBean<LogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LogFilter());
        registration.addUrlPatterns("/*"); // 拦截所有接口
        registration.setOrder(1); // 优先级最高
        return registration;
    }

    /**
     * 注册权限过滤器，优先级 2（日志之后执行）
     */
    @Bean
    public FilterRegistrationBean<AuthFilter> authFilterRegistration() {
        FilterRegistrationBean<AuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new AuthFilter());
        registration.addUrlPatterns("/*"); // 拦截所有接口
        registration.setOrder(2); // 优先级次之
        return registration;
    }

    @Bean
    public FilterRegistrationBean<CORSFilter> corsFilter() {
        FilterRegistrationBean<CORSFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CORSFilter());
        registrationBean.addUrlPatterns("/api/hello");
        // 数值越小越先执行：必须早于所有的过滤器，否则未认证/预检请求在中间被拦截时根本不会走到本过滤器
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registrationBean;
    }
}
