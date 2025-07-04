package com.dongnaebook.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;
//    private final String SECRET_KEY ="dongnebook-secret-key";

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
