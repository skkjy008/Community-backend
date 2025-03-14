package com.example.vueProject.jwt;

import java.util.Date;
import org.springframework.stereotype.Component;
import com.example.vueProject.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtMaker {
	
	    private final String secret = "MYSUPERLONGSECRETKEYWITH32CHARSSDASDASDQWEVVCDFFASD"; // 실제로는 환경변수나 별도 파일에서 관리
	    private final long accessTokenExpiration = 1000 * 60 * 15;   // 15분
	    private final long refreshTokenExpiration = 1000 * 60 * 60 * 24 * 7; // 7일

	    public String generateAccessToken(Member member) {
	        Date now = new Date();
	        Date expiry = new Date(now.getTime() + accessTokenExpiration);
	        return Jwts.builder()
	            .setSubject(member.getUsername())
	            .setIssuedAt(now)
	            .setExpiration(expiry)
	            .signWith(SignatureAlgorithm.HS256, secret)
	            .compact();
	    }

	    public String generateRefreshToken(Member member) {
	        Date now = new Date();
	        Date expiry = new Date(now.getTime() + refreshTokenExpiration);
	        return Jwts.builder()
	            .setSubject(member.getUsername())
	            .setIssuedAt(now)
	            .setExpiration(expiry)
	            .signWith(SignatureAlgorithm.HS256, secret)
	            .compact();
	    }

	    public boolean validateToken(String token) {
	        try {
	            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
	            return true;
	        } catch (Exception e) {
	            // 로그 출력 및 에러 핸들링
	            return false;
	        }
	    }

	    public String getUsernameFromToken(String token) {
	        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	        return claims.getSubject();
	    }
	


}
