package com.zacboot.gateway.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import lombok.Data;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Data
public class JwtTool {
    private static long overtime = 1000 * 60 * 60;

    public static String createToken(String userid, String username, SecretKey key, List<String> roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(userid)
                .setSubject(username)
                .setIssuedAt(now)
                .signWith(key)
                .claim("roles", roles);
        if (overtime > 0) {
            builder.setExpiration(new Date(nowMillis + overtime));
        }
        return builder.compact();
    }

    public static boolean verityToken(String token, SecretKey key) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            if (claims != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getUserid(String token, SecretKey key) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            if (claims != null) {
                return claims.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUserName(String token, SecretKey key) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            if (claims != null) {
                return claims.getSubject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getUserRoles(String token, SecretKey key) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            if (claims != null) {
                return (List<String>) claims.get("roles");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getClaims(String token, String param, SecretKey key) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            if (claims != null) {
                return claims.get(param).toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

