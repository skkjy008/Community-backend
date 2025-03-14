package com.example.vueProject.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
	
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 86400000; // 1일 (밀리초 단위)

    /**
     * JWT 토큰 생성
     *
     * @param username 사용자 이름
     * @return 생성된 JWT 토큰
     */
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    /**
     * JWT 토큰 검증
     *
     * @param token 검증할 JWT 토큰
     * @return 토큰에서 추출한 Claims
     * @throws JwtException 유효하지 않은 토큰일 경우 예외 발생
     */
    public static Claims validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 토큰에서 사용자 이름 추출
     *
     * @param token JWT 토큰
     * @return 사용자 이름
     */
    public static String getUsernameFromToken(String token) {
        return validateToken(token).getSubject();
    }
}
