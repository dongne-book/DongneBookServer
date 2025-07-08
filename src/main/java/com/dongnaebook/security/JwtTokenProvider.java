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
            return true;
        } catch (JwtException | IllegalArgumentException e){
            return false;
        }
    }
    //인증 객체 생성
    public Authentication getAuthentication(String token) {
        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String email = claims.getSubject();

        User principal = new User(email, "", Collections.emptyList());
        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }
    //토큰 생성
    public String generateToken(String email) {
        long now = System.currentTimeMillis();
        long exp = 1000 * 60 * 60 * 24;

        SecretKey Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now+exp))
                .signWith(Key, SignatureAlgorithm.HS256)
                .compact();
    }
}
