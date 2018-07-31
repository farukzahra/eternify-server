package com.fmz.eternify.utils;

public class CryptMD5 {

	public static String encrypt(String password) {
		String sign = password.toUpperCase();
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(sign.getBytes());
			byte[] hash = md.digest();
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
			sign = hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;
	}

	public static void main(String[] args) {
		System.out.println(CryptMD5.encrypt("SENHA02"));
	}
}
