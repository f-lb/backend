package com.backend.filb.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private SecretKey key = Jwts.SIG.HS256.key().build();

    public String createJWT(Long id) {
        return Jwts.builder()
                .claim("id", id)
                .signWith(key)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 10000))
                .compact();
    }

    public String getJWT() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");

        System.out.println("token = " + token);

        return token.replace("Bearer ", "");
    }

    public String getMemberId() {
        String accessToken = getJWT();
        System.out.println(accessToken);
        if (accessToken == null) {
            throw new JwtException("토큰이 유효하지 않습니다.");
        }
        if (accessToken.isEmpty()) {
            throw new JwtException("토큰이 유효하지 않습니다.");
        }
        Jws<Claims> jws;

        try {
            jws = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(accessToken);
        } catch (JwtException e) {
            throw new RuntimeException(e);
        }

        return jws.getPayload()
                .get("id", String.class);
    }
}