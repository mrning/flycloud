package com.zac.flycloud.utils;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    /**
     * 根据密码明文获取加密后的密码
     * @param passwordPlainText 密码明文
     * @return
     */
    public static String getPasswordEncode(String passwordPlainText){
        return encoder.encode(passwordPlainText);
    }

    /**
     * 校验密码是否匹配
     * @param passwordPlainText 明文
     * @param passwordCypher 密文
     * @return 匹配结果
     */
    public static Boolean getPasswordMatch(String passwordPlainText,String passwordCypher){
        return encoder.matches(passwordPlainText, passwordCypher);
    }
}
