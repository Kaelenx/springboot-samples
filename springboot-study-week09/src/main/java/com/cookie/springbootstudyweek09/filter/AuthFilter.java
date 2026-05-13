package com.cookie.springbootstudyweek09.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;

@Slf4j
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
        log.info("AuthFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 获取请求和响应对象
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 获取请求头中的授权令牌
        String authToken = httpRequest.getHeader("Authorization");
        // 检查是否包含特定的授权令牌
        if ("admin".equals(authToken)) {
            chain.doFilter(request, response);
        } else {
            // 返回未授权响应状态
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            // 向客户端输出未授权信息
            httpResponse.getWriter().write("Unauthorized");
        }
    }

    @Override
    public void destroy() {
        log.info("AuthFilter 销毁");
    }
}