package com.amazonaws.lambda.getlorryreceiptverification.services;

import java.security.NoSuchAlgorithmException;

public class CryptoUtility {
	
	public static void main(String[] args) {
		System.out.println(encrypt("00X4Sun_May_09_11:08:32_UTC_2021616870"));
	}

	public static String encrypt(String plainText) {
		String cipherText = null;

		try {
			cipherText = byteArrayToHexString(computeHash(plainText));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return cipherText;
	}

	private static byte[] computeHash(String x) throws NoSuchAlgorithmException {
		java.security.MessageDigest d = null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(x.getBytes());
		return d.digest();
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer sb = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			if (v < 16) {
				sb.append('0');
			}
			sb.append(Integer.toHexString(v));
		}
		return sb.toString().toUpperCase();
	}

}
