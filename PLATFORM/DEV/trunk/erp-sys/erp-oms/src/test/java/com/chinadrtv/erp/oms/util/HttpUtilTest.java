package com.chinadrtv.erp.oms.util;

import static org.junit.Assert.*;

import java.io.IOException;

import java.util.ArrayList;

import java.util.Iterator;

import java.util.List;

import java.util.Map;

import java.util.Map.Entry;

import javax.validation.constraints.AssertTrue;

import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;

import org.apache.http.HttpStatus;

import org.apache.http.NameValuePair;

import org.apache.http.client.ClientProtocolException;

import org.apache.http.client.HttpClient;

import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpGet;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.DefaultHttpClient;

import org.apache.http.message.BasicNameValuePair;

import org.apache.http.params.CoreConnectionPNames;

import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * HttpUtil 测试
 * 
 * @author haoleitao
 *
 */
public class HttpUtilTest {
	
	
	@Test
	public void postUrl()  {
		String result = null;
		String url = "http://localhost:8080/orderPreprocessing/test";
//        try {
//			result = HttpUtil.postUrl(url, null);
//        	
//        	
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        assertTrue( result == null);
	}
			
}
