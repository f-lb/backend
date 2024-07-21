package com.backend.filb.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private SecretKey key = Jwts.SIG.HS256.key().build();

    @Value("${token.expiration}")
    private int tokenExpiration;

    public String createJWT(String email) {
        return Jwts.builder()
                .claim("email", email)
                .signWith(key)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .compact();
    }

    public String getJWT() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");

        System.out.println("token = " + token);

        return token.replace("Bearer ", "");
    }

    public void checkTokenValidation(String accessToken){
        if (accessToken == null) {
            throw new JwtException("토큰이 유효하지 않습니다.");
        }
        if (accessToken.isEmpty()) {
            throw new JwtException("토큰이 유효하지 않습니다.");
        }
    }

    public String getMemberId() {
        String accessToken = getJWT();
        checkTokenValidation(accessToken);
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
