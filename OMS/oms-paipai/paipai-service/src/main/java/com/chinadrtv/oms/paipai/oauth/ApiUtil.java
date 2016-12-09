/*
 * @(#)ApiUtil.java 1.0 2015-1-27下午2:05:07
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2015 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.oms.paipai.oauth;

import com.chinadrtv.oms.paipai.dto.OrderConfig;

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
 * @since 2015-1-27 下午2:05:07 
 * 
 */
public class ApiUtil {

	public static PaiPaiOpenApiOauth getAPI(OrderConfig oc, String url) {
		PaiPaiOpenApiOauth api = PaiPaiOpenApiOauth.createInvokeApi(url);

		api.setAppOAuthID(oc.getAppkey());
		api.setAppOAuthkey(oc.getAppSecret());
		api.setAccessToken(oc.getSessionKey());
		api.setUin(oc.getUin());
		api.setHost(oc.getUrl());
		api.setFormat("xml");
		api.setCharset("UTF-8");
		
		return api;
	}
}
