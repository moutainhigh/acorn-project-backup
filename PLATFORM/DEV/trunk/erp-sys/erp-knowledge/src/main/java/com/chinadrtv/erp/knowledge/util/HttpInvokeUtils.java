package com.chinadrtv.erp.knowledge.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * TODO 暂时解决http请求，后可改造成使用连接池
 * 
 * @author dengqianyong
 * 
 */
public final class HttpInvokeUtils {
	
	public static String httpGet(String uri) {
		HttpGet httpGet = new HttpGet(uri);
		try {
			HttpEntity entity = invoke(httpGet);
			if (entity != null) return EntityUtils.toString(entity);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 发送http post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String httpPost(String url, Map<String, String> params) {
		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()) {
				parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
			HttpEntity entity = invoke(httpPost);
			if (entity != null) return EntityUtils.toString(entity);
		} catch (Exception e) {
		}
		return null;
	}
	
	private static HttpEntity invoke(HttpUriRequest request)
			throws ClientProtocolException, IOException {
		HttpResponse httpResponse = new DefaultHttpClient().execute(request);
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			return httpResponse.getEntity();
		}
		return null;
	}

}
