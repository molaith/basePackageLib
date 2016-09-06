package com.example.basepackagelib.tools;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.util.Base64;

public class Encryption {
	public static void main(String args[]) throws Exception {
		String data = "2fbwW9+8vPId2/foafZq6Q==";
		String jiami = encrypt(data);
		String jiemi = desEncrypt(jiami);
		System.out.println(jiemi);
		// System.out.println(desEncrypt());
	}

	public static String encrypt(String data) throws Exception {
		try {
			String key = "1234567812345678";
			String iv = "1234567812345678";
			data = data.replaceAll("\n", "");
			data=data.trim();
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();

			byte[] dataBytes = data.getBytes();
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength
						+ (blockSize - (plaintextLength % blockSize));
			}

			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
			byte[] encrypted = cipher.doFinal(plaintext);

			// return new sun.misc.BASE64Encoder().encode(encrypted);
			String res = new String(Base64.encode(encrypted, Base64.DEFAULT));
			res = res.replaceAll("\n", "");
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String desEncrypt(String data) throws Exception {
		try {

			String key = "1234567812345678";
			String iv = "1234567812345678";

			byte[] encrypted1 = Base64.decode(data, Base64.DEFAULT);

			// int length = encrypted1.length;
			// int yushu = length % 16;
			// if (yushu > 0) {
			// int bu=16-yushu;
			// byte[] buby=new byte[bu];
			// encrypted1=encrypted1+buby;
			// }
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			// Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
			IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original);
			return originalString;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
