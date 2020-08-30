package com.zac.flycloud.utils;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class TokenUtil {

    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static String createToken(String key,String sign){
        return encoder.encode(key+sign);
    }

    public static boolean checkToken(String token,String key,String sign){
        return encoder.matches(token,createToken(key,sign));
    }
}
