/*
 * @(#)EncryptUtil.java 1.0 2014-5-19下午5:10:44
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.gonghang.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.chinadrtv.gonghang.dal.model.OrderConfig;

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
 * @since 2014-5-19 下午5:10:44 
 * 
 */
@SuppressWarnings("restriction")
public class EncryptUtil {

	public static final String KEY_MAC = "HmacSHA256";
	
	public static String generateSign(String appKey, String authCode, String appSecret, String method, String reqData)
			throws Exception {
		
		// Hmac加密
		String data = "app_key=" + appKey + "&auth_code=" + authCode + /*"&method="+ method +*/"&req_data=" + reqData;

		byte[] bytes = encryptHMAC(data.getBytes("UTF-8"), appSecret);
		BASE64Encoder be = new BASE64Encoder();
		
		// 作为sign的密文内容
		return be.encode(bytes);
	}
	
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey sk = new SecretKeySpec(key.getBytes("UTF-8"), KEY_MAC);
		Mac mac = Mac.getInstance(sk.getAlgorithm());
		mac.init(sk);
		return mac.doFinal(data);
	}
	
	/**
	 * <p>生成8位数随机数</p>
	 * @param i
	 * @return
	 */
	public static String getFixedRandom() {

		UUID uuid = UUID.randomUUID();
		String rndUid = uuid.toString();
		
		Integer rnd = Math.abs(rndUid.hashCode());
		String val = (String.valueOf(rnd) + "01").substring(0, 8);
		
		return val;
	}

	/**
	 * <p></p>
	 * @param config
	 * @param method
	 * @param reqData
	 * @return
	 * @throws Exception 
	 */
	public static String generateRequestUrl(OrderConfig config, String method, String reqData) throws Exception {
		SimpleDateFormat sdfTimestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfHour = new SimpleDateFormat("yyyyMMddHH");

		String reqSid = sdfHour.format(new Date()) + EncryptUtil.getFixedRandom();
		String sign = EncryptUtil.generateSign(config.getAppKey(), config.getAccessToken(), config.getAppSecret(),
				method, reqData);

		String requestUrl = config.getUrl() + "?sign=" + sign + "&timestamp=" + sdfTimestamp.format(new Date())
				+ "&version=1.0" + "&format=xml&req_sid=" + reqSid + "&app_key=" + config.getAppKey() + "&auth_code="
				+ config.getAccessToken() + "&method=" + method + "&req_data=" + reqData;

		return requestUrl;
	}
}
