package com.chinadrtv.oms.paipai.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class OpenApiOauth {

    private String appOAuthID;
    private String appOAuthkey;
    private String accessToken;
    private long   uin;
    private String hostName = "api.paipai.com";
    private String format   = "xml";
    private String charset  = "gbk";
    // ���󷽷�
    private String method   = "get";

    public OpenApiOauth(String appOAuthID, String appOAuthkey, String accessToken, long uin){
        this.appOAuthID = appOAuthID;
        this.appOAuthkey = appOAuthkey;
        this.accessToken = accessToken;
        this.uin = uin;
    }

    @SuppressWarnings("unused")
    private OpenApiOauth(){
    }

    /**
     * ִ��API����
     * 
     * @param apiName OpenApi CGI, ���� /deal/sellerSearchDealList.xhtml
     * @param params OpenApi�Ĳ����б�
     * @param protocol HTTP����Э�� "http" / "https"
     * @return ���ط�������Ӧ����
     */
    public String invokeOpenApi(String apiName, HashMap<String, String> params) throws OpenApiException {
        // Ĭ�ϲ���ϵͳ�Զ�����
        params.put("appOAuthID", appOAuthID);
        params.put("accessToken", accessToken);
        params.put("uin", String.valueOf(uin));

        params.put("format", format);
        if (format.equalsIgnoreCase("json")) {
            params.put("pureData", "1");
        }
        params.put("charset", charset);

        // ָ��HTTP����Э������: "http" / "https"
        String protocol = "http";
        // ǩ����Կ
        String secret = appOAuthkey + "&";
        // ����ǩ��
        String sig = makeSign(method, apiName, params, secret);
        // sign,�Զ����
        params.put("sign", sig);
        // ƴ��URL
        String url = protocol + "://" + hostName + apiName;
        // cookie
        HashMap<String, String> cookies = null;
        // ��������
        String resp = null;
        if (method.equals("post")) {
            resp = postRequest(url, params, cookies, protocol);
        } else {
            resp = getRequest(url, params, cookies, protocol);
        }
        // ���?�ص������
        return resp;
    }

    /*
     * ���ǩ��
     * @param method HTTP���󷽷� "get" / "post"
     * @param url_path CGI����,
     * @param params URL�������
     * @param secret ��Կ
     * @return ǩ��ֵ
     * @throws OpensnsException ��֧��ָ�������Լ���֧��ָ���ļ��ܷ���ʱ�׳��쳣��
     */
    private String makeSign(String method, String url_path, HashMap<String, String> params, String secret)
                                                                                                          throws OpenApiException {
        String sig = "";
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(charset), mac.getAlgorithm());
            mac.init(secretKey);
            String mk = makeSource(method, url_path, params);
            System.out.println(mk);
            byte[] hash = mac.doFinal(mk.getBytes(charset));
            sig = new String(Base64Coder.encode(hash));
            // sig = encodeUrl(sig);

        } catch (Exception e) {
            throw new OpenApiException(OpenApiException.MAKE_SIGNATURE_ERROR, e);
        }
        return sig;
    }

    /*
     * URL���� (���FRC1738�淶)
     * @param input �������ַ�
     * @return �������ַ�
     * @throws OpenApiException ��֧��ָ������ʱ�׳��쳣��
     */
    private String encodeUrl(String input) throws OpenApiException {
        try {
            return URLEncoder.encode(input, charset).replace("+", "%20").replace("*", "%2A");
        } catch (UnsupportedEncodingException e) {
            throw new OpenApiException(OpenApiException.MAKE_SIGNATURE_ERROR, e);
        }
    }

    /*
     * ���ǩ������Դ��
     * @param method HTTP���󷽷� "get" / "post"
     * @param url_path CGI����,
     * @param params URL�������
     * @return ǩ������Դ��
     */
    private String makeSource(String method, String url_path, HashMap<String, String> params) throws OpenApiException,
                                                                                             UnsupportedEncodingException {
        Object[] keys = params.keySet().toArray();
        Arrays.sort(keys);
        StringBuilder buffer = new StringBuilder(128);
        buffer.append(method.toUpperCase()).append("&").append(encodeUrl(url_path)).append("&");
        StringBuilder buffer2 = new StringBuilder();
        for (int i = 0; i < keys.length; i++) {
            buffer2.append(keys[i]).append("=").append(params.get(keys[i]));// new
                                                                            // String(params.get(keys[i]).getBytes(),charset));
            if (i != keys.length - 1) {
                buffer2.append("&");
            }
        }
        System.out.println(buffer2);
        buffer.append(encodeUrl(buffer2.toString()));
        return buffer.toString();
    }

    /*
     * ����Get����
     * @param url ����URL��ַ
     * @param params �������
     * @param protocol ����Э�� "http" / "https"
     * @return ��������Ӧ��������
     * @throws OpenApiException �������ʱ�׳��쳣��
     */
    private String getRequest(String url, HashMap<String, String> params, HashMap<String, String> cookies,
                              String protocol) throws OpenApiException {
        StringBuffer showUrl = new StringBuffer(url);
        showUrl.append("?");
        // �����������
        if (params != null && !params.isEmpty()) {
            Iterator<Entry<String, String>> iter = params.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                showUrl.append(entry.getKey()).append("=").append(encodeUrl(entry.getValue())).append("&");
            }
        }
        showUrl.delete(showUrl.length() - 1, showUrl.length());
        System.out.println(showUrl);

        GetMethod getMethod = new GetMethod(showUrl.toString());
        HttpMethodParams methodParams = getMethod.getParams();
        // ����User-Agent
        getMethod.setRequestHeader("User-Agent", "Java OpenApiOauth SDK Client");

        // ����cookie
        if (cookies != null && !cookies.isEmpty()) {
            Iterator<Entry<String, String>> iter = cookies.entrySet().iterator();
            StringBuilder buffer = new StringBuilder(128);
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                buffer.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("; ");
            }
            // ����cookie����
            methodParams.setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
            // ����cookie����
            getMethod.setRequestHeader("Cookie", buffer.toString());
        }

        // ���ñ���
        methodParams.setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
        // ʹ��ϵͳ�ṩ��Ĭ�ϵĻָ�����
        methodParams.setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        try {
            try {
                HttpClient httpClient = new HttpClient();
                HttpConnectionManager connectionManager = httpClient.getHttpConnectionManager();
                HttpConnectionManagerParams connectionManagerParams = connectionManager.getParams();
                connectionManagerParams.setConnectionTimeout(3000);// ���ý������ӳ�ʱʱ��
                connectionManagerParams.setSoTimeout(3000);// ���ö���ݳ�ʱʱ��

                int statusCode = httpClient.executeMethod(getMethod);

                if (statusCode != HttpStatus.SC_OK) {
                    throw new OpenApiException(OpenApiException.NETWORK_ERROR, "Request [" + url + "] failed:"
                                                                               + getMethod.getStatusLine());
                }
                // ��ȡ����
                byte[] responseBody = getMethod.getResponseBody();
                return new String(responseBody, charset);
            } finally {
                // �ͷ�����
                getMethod.releaseConnection();
            }
        } catch (HttpException e) {
            // ����������쳣��������Э�鲻�Ի��߷��ص�����������
            throw new OpenApiException(OpenApiException.NETWORK_ERROR, "Request [" + url + "] failed:" + e.getMessage());
        } catch (IOException e) {
            // ���������쳣
            throw new OpenApiException(OpenApiException.NETWORK_ERROR, "Request [" + url + "] failed:" + e.getMessage());
        }
    }

    /*
     * ����POST����
     * @param url ����URL��ַ
     * @param params �������
     * @param protocol ����Э�� "http" / "https"
     * @return ��������Ӧ��������
     * @throws OpenApiException �������ʱ�׳��쳣��
     */
    private String postRequest(String url, HashMap<String, String> params, HashMap<String, String> cookies,
                               String protocol) throws OpenApiException {
        // url=url+"?charset="+charset;

        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);

        // �����������
        if (params != null && !params.isEmpty()) {
            NameValuePair[] data = new NameValuePair[params.size()];
            Iterator<Entry<String, String>> iter = params.entrySet().iterator();
            int i = 0;
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                // if("charset".equalsIgnoreCase(entry.getKey())){
                // continue;
                // }
                data[i] = new NameValuePair(entry.getKey(), entry.getValue());
                ++i;
            }
            postMethod.setRequestBody(data);
        }

        // ����cookie
        if (cookies != null && !cookies.isEmpty()) {
            Iterator<Entry<String, String>> iter = cookies.entrySet().iterator();
            StringBuilder buffer = new StringBuilder(128);
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = iter.next();
                buffer.append((String) entry.getKey()).append("=").append((String) entry.getValue()).append("; ");
            }
            // ����cookie����
            postMethod.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
            // ����cookie����
            postMethod.setRequestHeader("Cookie", buffer.toString());
        }

        // ����User-Agent
        postMethod.setRequestHeader("User-Agent", "Java OpenApiOauth SDK Client");
        // ���ý������ӳ�ʱʱ��
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
        // ���ö���ݳ�ʱʱ��
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(3000);
        // ���ñ���
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
        // ʹ��ϵͳ�ṩ��Ĭ�ϵĻָ�����
        postMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        try {
            try {
                int statusCode = httpClient.executeMethod(postMethod);
                if (statusCode != HttpStatus.SC_OK) {
                    throw new OpenApiException(OpenApiException.NETWORK_ERROR, "Request [" + url + "] failed:"
                                                                               + postMethod.getStatusLine());
                }
                // ��ȡ����
                byte[] responseBody = postMethod.getResponseBody();
                return new String(responseBody, charset);
            } finally {
                // �ͷ�����
                postMethod.releaseConnection();
            }
        } catch (HttpException e) {
            // ����������쳣��������Э�鲻�Ի��߷��ص�����������
            throw new OpenApiException(OpenApiException.NETWORK_ERROR, "Request [" + url + "] failed:" + e.getMessage());
        } catch (IOException e) {
            // ���������쳣
            throw new OpenApiException(OpenApiException.NETWORK_ERROR, "Request [" + url + "] failed:" + e.getMessage());
        }
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
