package com.example.basepackagelib.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import android.util.Log;

public class UrlJiaMi {
	public static String KEY = "foodminus";

	public static String urlJiaMi(String input, String urlstr) {
		UUID uid = UUID.randomUUID();
		// String key = uid.toString();
		String key = uid.toString() + System.currentTimeMillis() + "";
		String sign = input + key + KEY;
		sign = sign.replaceAll("\n", "");
		sign = getMD5Str(sign);
		urlstr = urlstr + "?v=1&" + "sign=" + sign + "&key=" + key;
		Log.d("zyj::", urlstr + "--urlstr");
		return urlstr;
	}
	
	public static String paramJiaMi(String input){
		UUID uid = UUID.randomUUID();
		// String key = uid.toString();
		String key = uid.toString() + System.currentTimeMillis() + "";
		String sign = input + key + KEY;
		sign = sign.replaceAll("\n", "");
		sign = getMD5Str(sign);
		return "?v=1&" + "sign=" + sign + "&key=" + key;
		 
	}
	
	/**
	 * MD5 加密
	 */
	public static String getMD5Str(String str) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(
						Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString().toLowerCase();
	}
}
