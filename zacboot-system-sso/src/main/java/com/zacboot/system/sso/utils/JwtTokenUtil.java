package com.zacboot.system.sso.utils;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.zacboot.common.base.basebeans.exceptions.BusinessException;
import com.zacboot.common.base.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.text.ParseException;
import java.util.Date;

@Slf4j
public class JwtTokenUtil {
    private static final String TOKEN_KEY_USERNAME = "sub";
    private static final String TOKEN_KEY_CREATED = "created";
    private static final String TOKEN_KEY_EXPIRATION = "expiration";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        JSONObject json = new JSONObject();
        json.putOpt(TOKEN_KEY_USERNAME, userDetails.getUsername())
                .putOpt(TOKEN_KEY_CREATED, new Date())
                .putOpt(TOKEN_KEY_EXPIRATION, generateExpirationDate());
        return generateToken(JSONUtil.toJsonStr(json));
    }

    /**
     * 根据负责生成JWT的token
     */
    private String generateToken(String payloadStr) {
        try {
            //准备JWS-header
            JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256)
                    .type(JOSEObjectType.JWT).build();
            //将负载信息装载到payload
            Payload payload = new Payload(payloadStr);
            //封装header和payload到JWS对象
            JWSObject jwsObject = new JWSObject(jwsHeader, payload);
            //创建HMAC签名器
            JWSSigner jwsSigner = new MACSigner(MD5Util.MD5Encode(secret,null));
            //签名
            jwsObject.sign(jwsSigner);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String verifyToken(String token) {
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            //创建HMAC验证器
            JWSVerifier jwsVerifier = new MACVerifier(secret);
            if (!jwsObject.verify(jwsVerifier)) {
                throw new BusinessException(401, "token签名不合法!");
            }
            String payload = jwsObject.getPayload().toString();
            JSONObject loadJson = JSONUtil.parseObj(payload);
            if (loadJson.getLong(TOKEN_KEY_EXPIRATION) < new Date().getTime()) {
                throw new BusinessException(401, "token已过期!");
            }
            return payload;
        } catch (JOSEException | BusinessException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 生成token的过期时间
     */
    private Long generateExpirationDate() {
        return System.currentTimeMillis() + expiration * 1000;
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        return null;
//        String username;
//        try {
//            Claims claims = getClaimsFromToken(token);
//            username = claims.getSubject();
//        } catch (Exception e) {
//            username = null;
//        }
//        return username;
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        return null;
//        Claims claims = getClaimsFromToken(token);
//        return claims.getExpiration();
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param oldToken 带tokenHead的token
     */
    public String refreshHeadToken(String oldToken) {
        return null;
//        if (StrUtil.isEmpty(oldToken)) {
//            return null;
//        }
//        String token = oldToken.substring(tokenHead.length());
//        if (StrUtil.isEmpty(token)) {
//            return null;
//        }
//        //token校验不通过
//        Claims claims = getClaimsFromToken(token);
//        if (claims == null) {
//            return null;
//        }
//        //如果token已经过期，不支持刷新
//        if (isTokenExpired(token)) {
//            return null;
//        }
//        //如果token在30分钟之内刚刷新过，返回原token
//        if (tokenRefreshJustBefore(token, 30 * 60)) {
//            return token;
//        } else {
//            claims.put(TOKEN_KEY_CREATED, new Date());
//            return generateToken(claims);
//        }
    }

    /**
     * 判断token在指定时间内是否刚刚刷新过
     *
     * @param token 原token
     * @param time  指定时间（秒）
     */
    private boolean tokenRefreshJustBefore(String token, int time) {
        return true;
//        Claims claims = getClaimsFromToken(token);
//        Date created = claims.get(TOKEN_KEY_CREATED, Date.class);
//        Date refreshDate = new Date();
//        //刷新时间在创建时间的指定时间内
//        if (refreshDate.after(created) && refreshDate.before(DateUtil.offsetSecond(created, time))) {
//            return true;
//        }
//        return false;
    }
}
