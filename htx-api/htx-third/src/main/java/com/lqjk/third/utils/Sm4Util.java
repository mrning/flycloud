package com.lqjk.third.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.legacy.math.linearalgebra.ByteUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Shawffer
 * @description:
 * @date: 2022-07-19
 * @time: 17:04
 */
public class Sm4Util {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = "UTF-8";
    public static final String ALGORITHM_NAME = "SM4";

    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";

    public static final int DEFAULT_SIZE = 128;

    private static Cipher generateEcbCiper(String algorithmName, int mode, byte[] key) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME_ECB_PADDING);
        cipher.init(mode, sm4Key);
        return cipher;
    }

    public static byte[] generateKey() throws NoSuchProviderException, NoSuchAlgorithmException {
        return generateKey(DEFAULT_SIZE);
    }

    public static byte[] generateKey(int keySize) throws NoSuchProviderException, NoSuchAlgorithmException {
        KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
        kg.init(keySize, new SecureRandom());
        return kg.generateKey().getEncoded();
    }

    /**
     * 加密
     *
     * @param hexKey
     * @param text
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchProviderException
     * @throws InvalidKeyException
     */
    public static String encryptEcb(String hexKey, String text) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        String cipherText = "";
        // 16进制字符串=>byte[]
        byte[] ketData = ByteUtils.fromHexString(hexKey);
        byte[] textData = text.getBytes(ENCODING);
        //加密
        byte[] cipherArr = encryptEcbPadding(ketData, textData);
        //byte[]=>hexStr
        cipherText = ByteUtils.toHexString(cipherArr);
        return cipherText;
    }

    /**
     * 加密
     *
     * @param key
     * @param data
     * @return
     * @throws NoSuchProviderException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] encryptEcbPadding(byte[] key, byte[] data) throws NoSuchProviderException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = generateEcbCiper(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param hexKey
     * @param cipherText
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String decryptEcb(String hexKey, String cipherText) throws UnsupportedEncodingException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        String decryptStr = "";
        byte[] ketData = ByteUtils.fromHexString(hexKey);
        byte[] ciperData = ByteUtils.fromHexString(cipherText);
        byte[] srcData = decryptEcbPadding(ketData, ciperData);
        decryptStr = new String(srcData, ENCODING);
        return decryptStr;
    }

    /**
     * 解密
     *
     * @param key
     * @param ciperText
     * @return
     * @throws NoSuchProviderException
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static byte[] decryptEcbPadding(byte[] key, byte[] ciperText) throws NoSuchProviderException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = generateEcbCiper(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(ciperText);
    }

    /**
     * 加密校验
     * @param hexKey
     * @param ciperText
     * @param text
     * @return
     * @throws NoSuchPaddingException
     * @throws NoSuchAlgorithmException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws NoSuchProviderException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    public static boolean verifyEcb(String hexKey, String ciperText, String text) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, InvalidKeyException, UnsupportedEncodingException {
        boolean flag = false;
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        byte[] ciperData = ByteUtils.fromHexString(ciperText);
        byte[] decryptData = decryptEcbPadding(keyData, ciperData);
        byte[] srcData = text.getBytes(ENCODING);
        flag = Arrays.equals(decryptData, srcData);
        return flag;
    }

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException {
        System.out.println(ByteUtils.toHexString(generateKey()));
    }
}
