package com.zac.base.utils;

import java.security.MessageDigest;
import java.util.concurrent.ThreadLocalRandom;

public class MD5Util {

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++){
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n += 256;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin, String charsetname) {
		String resultString = null;
		try {
			resultString = origin;
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charsetname == null || "".equals(charsetname)) {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
			} else {
				resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
			}
		} catch (Exception exception) {
		}
		return resultString;
	}

	/**
	 * 生成登录验证码
	 * @param length
	 * @return
	 */
	public static String createCode(int length){
		String baseString = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";
		StringBuilder sb = new StringBuilder(length);
		if (length < 1) {
			length = 1;
		}

		int baseLength = baseString.length();

		for(int i = 0; i < length; ++i) {
			int number = ThreadLocalRandom.current().nextInt(baseLength);
			sb.append(baseString.charAt(number));
		}

		return sb.toString();

	}

	private static final String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

}
