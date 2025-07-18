package com.dongnaebook.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Collections;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    //토큰 검증
    public boolean validateToken(String token) {
        try{
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            System.out.println("[validateToken] 토큰 검증 성공! token: " + token);
            return true;
        } catch (JwtException | IllegalArgumentException e){
            System.err.println("[validateToken] 토큰 검증 실패! token: " + token + " / reason: " + e.getMessage());
            return false;
        }
    }
    //인증 객체 생성
    public Authentication getAuthentication(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        String email = claims.getSubject();
        Long userId = claims.get("userId", Long.class);

        System.out.println("[getAuthentication] email: " + email);
        System.out.println("[getAuthentication] claims: " + claims);

//        User principal = new User(email, "", Collections.emptyList());
        CustomUserDetails principal = new CustomUserDetails(userId, email);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }
    //토큰 생성
    public String generateToken(Long userId, String email) {
        long now = System.currentTimeMillis();
        long exp = 1000 * 60 * 60 * 24;

        SecretKey Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        System.out.println("[generateToken] 발급 email: " + email);
        String token = Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now+exp))
                .signWith(Key, SignatureAlgorithm.HS256)
                .compact();
        System.out.println("[generateToken] 발급된 토큰: " + token);

        return token;
    }
}
