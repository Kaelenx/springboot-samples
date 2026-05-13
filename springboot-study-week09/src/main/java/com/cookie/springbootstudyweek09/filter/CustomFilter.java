package com.cookie.springbootstudyweek09.filter;


import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author cookie
 */
@Slf4j
public class CustomFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("CustomFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("CustomFilter 拦截");
        chain.doFilter(request, response);
        log.info("CustomFilter 结束");
    }

    @Override
    public void destroy() {
        log.info("CustomFilter 销毁");
    }
}
