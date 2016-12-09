package com.chinadrtv.erp.marketing.util;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

import java.util.Iterator;

import java.util.List;

import java.util.Map;

import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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


/**
 * HttpUtil
 * 
 * @author haoleitao
 *
 */
public class HttpUtil {
	
	private static final Log log = LogFactory.getLog(HttpUtil.class);
	/**
	 * http访问数据，默认返回的数据为UTF-8编码
	 * 
	 * 
	 * @param url
	 * 
	 *            url地址
	 * 
	 * @param method
	 * 
	 *            提交方式
	 * 
	 * @return
	 * 
	 * @throws IOException
	 */

	public static String getUrl(String url) throws IOException {

		HttpGet request = new HttpGet(url);

		HttpClient httpClient = new DefaultHttpClient();

		HttpResponse response = httpClient.execute(request);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

			return EntityUtils.toString(response.getEntity());

		} else {

			return "";

		}

	}

	/**
	 * 
	 * post提交数据
	 * 
	 * 
	 * 
	 * @param url
	 * 
	 *            提交地址
	 * 
	 * @param params
	 * 
	 *            参数
	 * 
	 * @return
	 * 
	 * @throws ClientProtocolException
	 * 
	 * @throws IOException
	 */

	public static String postUrl(String url, Map<String, String> params)

	throws ClientProtocolException, IOException {

		return postUrl(url, params, "UTF-8");

	}

	/**
	 * 
	 * post提交数据
	 * 
	 * 
	 * 
	 * @param url
	 * 
	 *            提交地址
	 * 
	 * @param params
	 * 
	 *            参数
	 * 
	 * @param encoding
	 * 
	 *            参数编码
	 * 
	 * @return
	 * 
	 * @throws ClientProtocolException
	 * 
	 * @throws IOException
	 */

	public static String postUrl(String url, Map<String, String> params,

	String encoding) throws ClientProtocolException, IOException {

		List<NameValuePair> param = new ArrayList<NameValuePair>(); // 参数

		// param.add(new BasicNameValuePair("par", "request-post"));

		// 这个参数我不知道是不是非要加这个

		// 添加参数

		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();

		while (iterator.hasNext()) {

			Entry<String, String> entry = iterator.next();

			param.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

		}

		HttpPost request = new HttpPost(url);

		HttpEntity entity = new UrlEncodedFormEntity(param, encoding);
        StringEntity se = new StringEntity("","UTF-8");
       
		request.setEntity(entity);
	

		HttpClient client = new DefaultHttpClient();

		client.getParams().setParameter(

		CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间

		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000); // 读取超时

		HttpResponse response = client.execute(request);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

			return EntityUtils.toString(response.getEntity());

		} else {

			return null;

		}

	}
	
	public static String postUrl(String url, String content,
			String encoding) throws ClientProtocolException, IOException {
				HttpPost request = new HttpPost(url);	
		        StringEntity se = new StringEntity(content,"UTF-8");
		        se.setContentType("application/xml");
				request.setEntity(se);
			

				HttpClient client = new DefaultHttpClient();

				client.getParams().setParameter(

				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 设置请求超时时间

				client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000); // 读取超时

				HttpResponse response = client.execute(request);

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

					return EntityUtils.toString(response.getEntity());

				} else {

					return null;

				}

			}
	
	 public static void ajaxReturn(HttpServletResponse response,String message,String contentType){
	    	
         try {
             response.setContentType(contentType);
             response.setCharacterEncoding("UTF-8");
             PrintWriter  out = response.getWriter();
             out.write(message);
             out.flush();
             out.close();

         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
    }

}
