package com.zac.flycloud.utils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {

    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    private static final AsymmetricCrypto algorithm = new AsymmetricCrypto(AsymmetricAlgorithm.RSA);
    private static final Sign sign = SecureUtil.sign(SignAlgorithm.MD5withRSA);

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
        byte[] signByte = sign.sign((username+key).getBytes());
        // 公钥加密
        byte[] encode = algorithm.encrypt(signByte, KeyType.PublicKey);
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
        byte[] signByte = algorithm.decrypt(HexUtil.decodeHex(token), KeyType.PrivateKey);
        return sign.verify(signData.getBytes(), signByte);
    }

    public static void main(String[] args) throws Exception {
//        System.out.println(getPasswordMatch("123456",getPasswordEncode("123456")));
//        String token = createToken("abc");
//        System.out.println(token);
//        System.out.println(getUserNameByToken(token));
    }
}
