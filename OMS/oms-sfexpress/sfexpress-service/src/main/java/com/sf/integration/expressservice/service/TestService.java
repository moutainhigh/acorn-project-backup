/*
 * @(#)TestService.java 1.0 2014-6-23下午1:14:12
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.sf.integration.expressservice.service;

import java.rmi.RemoteException;

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
 * @since 2014-6-23 下午1:14:12 
 * 
 */
public class TestService {

	/**
	 * <p></p>
	 * @param args
	 */
	public static void main(String[] args) {
		IServiceProxy proxy = new IServiceProxy();
		proxy.setEndpoint("http://bsp-test.sf-express.com:9090/bsp-ois/ws/expressService?wsdl");

		IService iservice = proxy.getIService();
		String data = "<Request service=\"OrderService\" lang=\"zh-CN\"><Head>SHXGWR,ibLICsNlt-m1</Head><Body><Order orderid=\"ATV113824339\" express_type=\"1\" j_company=\"橡果\" j_contact=\"橡果\" j_tel=\"400-6668888\" j_address=\"上海市青浦区华新镇华卫路18号\" d_company=\"\" d_contact=\"李云清\" d_tel=\"\" d_mobile=\"13523843623 13523849996\" d_address=\"四川省眉山市东坡区象耳镇刘庙村\" parcel_quantity=\"1\" pay_method=\"\" remark=\"\" /><Cargo name=\"YEJZYT26\" count=\"1\"></Cargo><Cargo name=\"YEJDGDC\" count=\"1\"></Cargo><Order></Body>";
		String response = null;
		try {
			response = iservice.sfexpressService(data);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		System.out.println(response);
	}

}
