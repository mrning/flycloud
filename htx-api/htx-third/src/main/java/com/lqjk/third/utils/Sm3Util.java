package com.lqjk.third.utils;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Shawffer
 * @description:
 * @date: 2022-07-19
 * @time: 17:35
 */
public class Sm3Util {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = "UTF-8";

    /**
     * 无密钥加密
     * @param text
     * @return
     */
    public static String encrypt(String text) throws UnsupportedEncodingException {
        String resultHexStr = "";
        byte[] srcData = text.getBytes(ENCODING);
        byte[] resultHash = hash(srcData);
        resultHexStr = ByteUtils.toHexString(resultHash);
        return resultHexStr;
    }

    public static byte[] hash(byte[] srcData){
        SM3Digest digest = new SM3Digest();
        digest.update(srcData, 0, srcData.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    /**
     * 有密钥加密
     * @param key
     * @param srcData
     * @return
     */
    public static byte[] hmac(byte[] key, byte[] srcData){
        KeyParameter keyParameter = new KeyParameter(key);
        SM3Digest digest = new SM3Digest();
        HMac mac = new HMac(digest);
        mac.init(keyParameter);
        mac.update(srcData, 0, srcData.length);
        byte[] result = new byte[mac.getMacSize()];
        mac.doFinal(result, 0);
        return result;
    }

    /**
     * 校验
     * @param srcStr
     * @param sm3HexString
     * @return
     * @throws UnsupportedEncodingException
     */
    public static boolean verify(String srcStr, String sm3HexString) throws UnsupportedEncodingException {
        boolean flag;
        byte[] srcData = srcStr.getBytes(ENCODING);
        byte[] sm3Hash = ByteUtils.fromHexString(sm3HexString);
        byte[] newHash = hash(srcData);
        flag = Arrays.equals(sm3Hash, newHash);
        return flag;
    }

}
