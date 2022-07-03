package com.zac.flycloud.common.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;

public class PasswordUtil {

    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * 根据密码明文获取加密后的密码
     *
     * @param passwordPlainText 密码明文
     * @return
     */
    public static String getPasswordEncode(String passwordPlainText) {
        return encoder.encode(passwordPlainText);
    }

    /**
     * 校验密码是否匹配
     *
     * @param passwordPlainText 明文
     * @param passwordCypher    密文
     * @return 匹配结果
     */
    public static Boolean getPasswordMatch(String passwordPlainText, String passwordCypher) {
        return encoder.matches(passwordPlainText, passwordCypher);
    }

    /**
     * 生成token
     *
     * @param username
     * @return
     */
    public static String createToken(String username,String key) {
        // 公钥加密
        byte[] encode = (username+key).getBytes(StandardCharsets.UTF_8);
        return HexUtil.encodeHexStr(encode);
    }

    /**
     * 根据token获取用户名
     *
     * @param token
     */
    public static Boolean verifyToken(String token,String key) {
        if(StringUtils.isBlank(token)){
            return false;
        }
        String signData = SpringContextUtils.getBean(RedisUtil.class).get(token) + key;
        // 私钥解密
        byte[] signByte = HexUtil.decodeHex(token);
        return StringUtils.toEncodedString(signByte, CharsetUtil.CHARSET_UTF_8).equals(signData);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getPasswordMatch("123456",getPasswordEncode("123456")));
//        String token = createToken("abc");
//        System.out.println(token);
//        System.out.println(getUserNameByToken(token));
    }
}
