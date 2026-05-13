package com.cookie.springbootstudyweek09.interceptor;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    /** 示例令牌（与 curl / 前端请求头保持一致即可） */
    public static final String HEADER_TOKEN_EXAMPLE = "admin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (HEADER_TOKEN_EXAMPLE.equals(token)) {
            return true;
        }
        log.warn("[认证拦截器] 非法或未携带令牌 uri={}", request.getRequestURI());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().write("Unauthorized");
        return false;
    }
}
