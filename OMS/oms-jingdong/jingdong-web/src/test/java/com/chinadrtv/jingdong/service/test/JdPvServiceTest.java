package com.chinadrtv.jingdong.service.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.chinadrtv.jingdong.model.JingdongOrderConfig;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.JdException;
import com.jd.open.api.sdk.request.udp.DataserviceShopsalesbydayGetRequest;
import com.jd.open.api.sdk.request.udp.DataserviceShopsalesbymonthGetRequest;
import com.jd.open.api.sdk.response.udp.DataserviceShopsalesbydayGetResponse;
import com.jd.open.api.sdk.response.udp.DataserviceShopsalesbymonthGetResponse;

/**
 * Created with (JD). User: 刘宽 Date: 13-10-29 橡果国际-系统集成部 Copyright (c) 2012-2013
 * ACORN, Inc. All rights reserved.
 */
public class JdPvServiceTest extends TestCase {

	@Test
	public void testMok() throws Exception {
		JingdongOrderConfig jdorderconfig = new JingdongOrderConfig();
		jdorderconfig.setUrl("http://gw.api.360buy.com/routerjson");
		jdorderconfig.setAppkey("C159FD262FF5D1067796B5F0D99724DC");
		jdorderconfig.setAppSecret("284bc3d62b8a475285b92c502c822c22");
		// jdorderconfig.setSessionKey("8f22665d-774e-4cea-a925-312915a36288");
		jdorderconfig.setSessionKey("8d33915f-7ba4-42bc-9bab-7edb3d4c4794");

		JdClient client = new DefaultJdClient(jdorderconfig.getUrl(), jdorderconfig.getSessionKey(),
				jdorderconfig.getAppkey(), jdorderconfig.getAppSecret());

		DataserviceShopsalesbydayGetRequest request = new DataserviceShopsalesbydayGetRequest();
		// request.setShopId(123);
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// request.setTimeId(sdf.format(new Date()));
		request.setTimeId("2014-05-13");
		DataserviceShopsalesbydayGetResponse response = client.execute(request);

		System.out.println(response);
	}

	@Test
	public void testLoadMonthData() throws JdException {

		JingdongOrderConfig jdorderconfig = new JingdongOrderConfig();
		jdorderconfig.setUrl("http://gw.api.360buy.com/routerjson");
		/*
		 * jdorderconfig.setAppkey("C159FD262FF5D1067796B5F0D99724DC");
		 * jdorderconfig.setAppSecret("284bc3d62b8a475285b92c502c822c22");
		 * jdorderconfig.setSessionKey("1ad7dede-f3d1-4c5b-adee-cd7fb6a98753");
		 */
		jdorderconfig.setAppkey("C159FD262FF5D1067796B5F0D99724DC");
		jdorderconfig.setAppSecret("284bc3d62b8a475285b92c502c822c22");
		jdorderconfig.setSessionKey("8f22665d-774e-4cea-a925-312915a36288");

		JdClient client = new DefaultJdClient(jdorderconfig.getUrl(), jdorderconfig.getSessionKey(),
				jdorderconfig.getAppkey(), jdorderconfig.getAppSecret());
		DataserviceShopsalesbymonthGetRequest request = new DataserviceShopsalesbymonthGetRequest();

		// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		// request.setTimeId(sdf.format(new Date()));
		request.setTimeId("2014-01");
		DataserviceShopsalesbymonthGetResponse response = client.execute(request);

		System.out.println(response);
	}
}
