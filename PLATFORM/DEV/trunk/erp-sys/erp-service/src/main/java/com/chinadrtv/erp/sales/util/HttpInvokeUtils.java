package com.chinadrtv.erp.sales.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
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
			/* 添加请求参数到请求对象 */
			httpPost.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
			/* 发送请求并等待响应 */
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
			/* 若状态码为200 ok */
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				/* 读返回数据 */
				return EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
		}
		return null;
	}

}
