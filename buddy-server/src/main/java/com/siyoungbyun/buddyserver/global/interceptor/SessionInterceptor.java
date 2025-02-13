package com.siyoungbyun.buddyserver.global.interceptor;

import com.siyoungbyun.buddyserver.global.exception.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.HandlerInterceptor;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             @NotNull HttpServletResponse response,
                             @NotNull Object handler) throws UnauthorizedException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            throw new UnauthorizedException("권한이 없습니다. 다시 로그인해주세요.");
        }
        Long userId = (Long) session.getAttribute("user");
        request.setAttribute("userId", userId);
        return true;
    }
}