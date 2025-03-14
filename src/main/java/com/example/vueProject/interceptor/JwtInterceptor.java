package com.example.vueProject.interceptor;
import com.example.vueProject.jwt.JwtMaker;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtMaker jwtMaker;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();
        if (uri.startsWith("/api/auth/login") ||
            uri.startsWith("/api/auth/register") ||
            uri.startsWith("/api/auth/refresh") ||
            uri.startsWith("/api/public")) {
            return true;
        }

        // Authorization 헤더에서 Bearer 토큰 추출
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                // 토큰이 유효한지 확인
                if (jwtMaker.validateToken(token)) {
                    // 토큰이 유효하면 요청 진행
                    return true;
                }
            } catch (Exception e) {
                // 예외 발생 시 토큰 검증 실패
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Unauthorized: Invalid token");
                return false;
            }
        }

        // Authorization 헤더가 없거나 유효하지 않은 경우
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Unauthorized: Missing or invalid Authorization header");
        return false;
    }
}
