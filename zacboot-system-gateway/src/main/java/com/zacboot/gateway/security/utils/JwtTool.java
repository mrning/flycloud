package com.zacboot.gateway.security.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.zacboot.common.base.constants.CommonConstant;
import com.zacboot.common.base.utils.RedisUtil;
import com.zacboot.common.base.utils.SpringContextUtils;
import lombok.Data;

import java.security.SecureRandom;
import java.util.Map;

@Data
public class JwtTool {
    private static long overtime = 1000 * 60 * 60;

    public static String createToken(Map<String, Object> jsonObject) {
        // 创建头部对象
        try {
            SecureRandom random = new SecureRandom();
            byte[] sharedSecret = new byte[32];
            random.nextBytes(sharedSecret);

            RedisUtil redisUtil = SpringContextUtils.getApplicationContext().getBean(RedisUtil.class);
            JWSHeader jwsHeader = new JWSHeader.Builder(JWSAlgorithm.HS256).type(JOSEObjectType.JWT).build();
            Payload payload = new Payload(jsonObject);
            JWSSigner jwsSigner = new MACSigner(sharedSecret);
            JWSObject jwsObject = new JWSObject(jwsHeader,payload);
            jwsObject.sign(jwsSigner);
            String token = jwsObject.serialize();
            redisUtil.hset(CommonConstant.PREFIX_USER_TOKEN,token,sharedSecret);

            return token;
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verityToken(String token) {
        try {
            RedisUtil redisUtil = SpringContextUtils.getApplicationContext().getBean(RedisUtil.class);
            byte[] sharedSecret = (byte[]) redisUtil.hget(CommonConstant.PREFIX_USER_TOKEN,token);
            JWSObject jwsObject = JWSObject.parse(token);

            JWSVerifier verifier = new MACVerifier(sharedSecret);
            return jwsObject.verify(verifier);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Object getPayLoadByKey(String token,String payLoadKey) {
        try {
            if(verityToken(token)){
                JWSObject jwsObject = JWSObject.parse(token);
                return jwsObject.getPayload().toJSONObject().get(payLoadKey);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }
}

