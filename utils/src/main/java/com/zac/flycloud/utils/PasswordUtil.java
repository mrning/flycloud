package com.zac.flycloud.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.*;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.DES;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import com.alibaba.nacos.client.naming.utils.SignUtil;
import com.alibaba.nacos.common.util.Md5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
    public static String createToken(String username) {
        byte[] signByte = sign.sign(username.getBytes());
        // 公钥加密
        byte[] encode = algorithm.encrypt(signByte, KeyType.PublicKey);
        return HexUtil.encodeHexStr(encode);
    }

    /**
     * 根据token获取用户名
     *
     * @param token
     */
    public static Boolean verifyToken(String token) {
        if(StringUtils.isBlank(token)){
            return false;
        }
        String signData = (String) SpringContextUtils.getBean(RedisUtil.class).get(token);
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
