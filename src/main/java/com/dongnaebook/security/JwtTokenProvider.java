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
        List<SimpleGrantedAuthority> authorities = roles.stream() // ← 여기서 NPE
                .map(SimpleGrantedAuthority::new)
                .toList();

        System.out.println("[getAuthentication] email: " + email);
        System.out.println("[getAuthentication] claims: " + claims);


        User principal = new User(email, "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }
    //토큰 생성
    public String generateToken(String email, List<String> roles) {
        long now = System.currentTimeMillis();
        long exp = 1000 * 60 * 60 * 24;
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);

        SecretKey Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        System.out.println("[generateToken] 발급 email: " + email);
        String token = Jwts.builder()
//                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now+exp))
                .signWith(Key, SignatureAlgorithm.HS256)
                .compact();
        System.out.println("[generateToken] 발급된 토큰: " + token);

        return token;
    }
}
