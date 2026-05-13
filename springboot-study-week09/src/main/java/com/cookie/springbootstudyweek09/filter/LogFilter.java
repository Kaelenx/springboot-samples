package com.cookie.springbootstudyweek09.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("LogFilter 初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 获取请求信息
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        // 获取请求路径、客户端IP、时间戳
        String path = httpRequest.getRequestURI();
        String clientIp = httpRequest.getRemoteAddr();
        LocalDateTime timestamp = LocalDateTime.now();
        log.info("请求已到达日志过滤器：: Path={},IP={},Time={}", path, clientIp, timestamp);
        // 放行到下一个过滤器
        filterChain.doFilter(request, response);
        // 处理响应
        log.info("接口执行完毕, 返回 LogFilter 处理, Time={}", LocalDateTime.now());
    }
}
