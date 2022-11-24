package com.photo.photo_backend.utils;


import io.jsonwebtoken.*;

import java.util.Date;

public class TokenUtil {
    private static final String tokenSingKey = "photo_backend";
    // 生成token
    public static String createToken(String username) {
        Date nowDate = new Date();
        long tokenExpireTime = 1000 * 60 * 60 * 24;
        Date expireDate = new Date(nowDate.getTime() + tokenExpireTime);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, tokenSingKey)
                .compact();
    }
    // 解析token
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(tokenSingKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean verify(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return false;
        }
        return !claims.getExpiration().before(new Date());
    }

    public static String getUsername(String token) {
        Claims claims = parseToken(token);
        if (claims == null) {
            return null;
        }
        return claims.getSubject();
    }
}
