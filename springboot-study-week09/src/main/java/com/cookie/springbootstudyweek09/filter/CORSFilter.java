package com.cookie.springbootstudyweek09.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Slf4j
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("CORSFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // 允许所有源访问
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 允许所有请求方法访问
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        // 允许指定的请求头访问
        httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");

        // 浏览器预检 OPTIONS 不带业务 Authorization，需在后续认证过滤器之前直接放行
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("CORSFilter 销毁");
    }
}