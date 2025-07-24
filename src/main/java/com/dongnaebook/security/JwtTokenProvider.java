package com.dongnaebook.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    private final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60;

    private final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;

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
        List<String> roles = Optional.ofNullable((List<String>) claims.get("roles"))
                .orElse(List.of());
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();

        System.out.println("[getAuthentication] email: " + email);
        System.out.println("[getAuthentication] claims: " + claims);

        User principal = new User(email, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

    public String generateAccessToken(String email, List<String> roles) {
        return generateToken(email, roles, ACCESS_TOKEN_EXPIRE_TIME, "access");
    }

    //리프레시 토큰 생성
    public String generateRefreshToken(String email) {
        return generateToken(email, List.of(), REFRESH_TOKEN_EXPIRE_TIME, "refresh");
    }

    private String generateToken(String email, List<String> roles, long expireTime, String tokenType) {
        long now = System.currentTimeMillis();
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        claims.put("tokenType", tokenType);

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        System.out.println("[generateToken] 발급 email: " + email + ", type: " + tokenType);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expireTime))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("[generateToken] 발급된 " + tokenType + " 토큰: " + token);
        return token;
    }

    public String generateToken(String email, List<String> roles) {
        return generateAccessToken(email, roles);
    }

    public String getEmailFromToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean isRefreshToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            String tokenType = (String) claims.get("tokenType");
            return "refresh".equals(tokenType);
        } catch (JwtException e) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }
}
