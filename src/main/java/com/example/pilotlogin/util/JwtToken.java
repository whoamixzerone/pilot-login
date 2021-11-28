package com.example.pilotlogin.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JwtToken {

    public String createToken(String jwtSecretKey) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("iss", "ZerOne");

        Long expiredTime = 1000 * 60L * 60L * 2L;

        Date ext = new Date();
        ext.setTime(ext.getTime() + expiredTime);

        String jwt = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setSubject("user-auth")
                .setExpiration(ext)
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey.getBytes())
                .compact();

        return jwt;
    }

    public String verifyToken(String jwtSecretKey, String authorization) throws UnsupportedEncodingException {
        log.info("authorization >>> {}", authorization);
        String contentValue = null;

        try {
            DefaultJws defaultJws = (DefaultJws) Jwts.parser()
                    .setSigningKey(jwtSecretKey.getBytes(StandardCharsets.UTF_8))
                    .parseClaimsJws(authorization);

            log.info("defaultJws : {}", defaultJws);
            log.info("defaultJws body class : {}", defaultJws.getBody().getClass());

            DefaultClaims claims = (DefaultClaims) defaultJws.getBody();
            log.info("-----------------");
            contentValue = claims.getSubject();
            log.info("contentValue : {}", contentValue);
        } catch (JwtException | IllegalArgumentException e) {
            log.info("# expir : {}", e.getClass());
        }

        return contentValue;
    }
}
