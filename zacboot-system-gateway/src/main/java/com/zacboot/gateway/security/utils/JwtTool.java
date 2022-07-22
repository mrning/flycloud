package com.zacboot.gateway.security.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;
import org.bouncycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateKey;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Data
@Component
public class JwtTool {
    private String key = "com.bxc";
    private long overtime = 1000 * 60 * 60;

    public String CreateToken(String userid, String username, List<String> roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(userid)
                .setSubject(username)
                .setIssuedAt(now)
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .claim("roles", roles);
        if (overtime > 0) {
            builder.setExpiration(new Date(nowMillis + overtime));
        }
        return builder.compact();
    }

    public boolean VerityToken(String token) {
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

    public String getUserid(String token) {
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

    public String getUserName(String token) {
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

    public List<String> getUserRoles(String token) {
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

    public String getClaims(String token, String param) {
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

