package com.chinadrtv.erp.util;

import org.springframework.security.crypto.codec.Base64;

public class Base64Encryptor {
	
	public static String encrypt(String key) throws Exception {
		return new String(encode(key.getBytes()));
	}
	
	public static String decrypt(String enKey) throws Exception {
		return new String(decode(enKey.getBytes()));
	}
	
	private static byte[] encode(byte[] bytes) {
		return Base64.encode(bytes);
	}
	
	private static byte[] decode(byte[] bytes) {
		return Base64.decode(bytes);
	}
	
	public static void main(String[] args) throws Exception {
		String str = "http://192.168.3.171:8086/welcome?MTI2NTB8TVRJek5EVTJ8MTI3LjAuMC4xfDIwMTQtMDEtMTc=";
		
		System.out.println(Base64Encryptor.decrypt("MTI2NTB8TVRJek5EVTJ8MTI3LjAuMC4xfDIwMTQtMDEtMTc="));
	}
}
