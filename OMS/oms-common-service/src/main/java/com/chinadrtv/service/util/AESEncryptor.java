/*
 * @(#)AESEncryptor.java 1.0 2014-10-8上午11:08:09
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.service.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014-10-8 上午11:08:09 
 * 
 */
public class AESEncryptor {
	
	private static String password = "";
	
	private AESEncryptor(){
		
	}
	
	static{
		//TODO Fixing the issue of non unicode OS encryption
		InputStream is = AESEncryptor.class.getResourceAsStream("/key.key");
		
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		
		try {
			br = new BufferedReader(new InputStreamReader(is));
			
			String line = null;
			while((line = br.readLine()) != null) {
				sb.append(line);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		String chars = sb.toString();
		password = chars.substring(11, 23);
	}
	
	
	@SuppressWarnings("restriction")
	public static String encrypt(String content) {
		
		try {
			Cipher aesECB = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			aesECB.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = aesECB.doFinal(content.getBytes());
			
			return new BASE64Encoder().encode(result);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
 
	@SuppressWarnings("restriction")
	public static String decrypt(String content) {
		
		try {
			// 创建密码器
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
			
			//初始化
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = new BASE64Decoder().decodeBuffer(content);
			
			//解密
			return new String(cipher.doFinal(result));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
