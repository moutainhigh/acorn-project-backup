package com.chinadrtv.oms.paipai.oauth;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


public class PaiPaiOpenApiOauth{
	
	private String appOAuthID;
	private String appOAuthkey;
	private String accessToken;
	private long uin;
	
	private String charset="GBK";
	private String format="json";
	private String method="GET";
	
	
	private String apiPath="/item/modifyItemPic.xhtml";
	private String host="http://apitest.paipai.com";

	private Map<String, String> params=new HashMap<String, String>();
	private Map<String, FileItem> fileParams=new HashMap<String, FileItem>();
	private long timeStamp=System.currentTimeMillis();
	private String randomValue=""+(long)(Math.random()*100000000);
	
	
	public void addFileParam(String key, FileItem file){
		fileParams.put(key, file);
	}
	
	public void addParam(String key, String value){
		params.put(key, value);
	}
	
	public PaiPaiOpenApiOauth(String apiPath) {
		this.apiPath = apiPath;
		
	}
	
	public static PaiPaiOpenApiOauth createInvokeApi(String apiPath){
		return new PaiPaiOpenApiOauth(apiPath);
	}
	
	public static String invoke(PaiPaiOpenApiOauth api) throws OpenApiException, IOException{
		String url=api.host+api.apiPath;
		
		String secret = api.appOAuthkey+"&";
	    if(api.fileParams!=null && api.fileParams.size()>0){
	    	api.method = "POST";
	    }
		
	    api.params.put("format", api.format);
	    api.params.put("charset", api.charset);
	    api.params.put("timeStamp", api.timeStamp+"");
	    api.params.put("randomValue", api.randomValue);
		
	    api.params.put("appOAuthID", api.appOAuthID);
	    api.params.put("accessToken", api.accessToken);
	    api.params.put("uin", Long.toString(api.uin));
	    
	    String sign = api.makeSign(api.method, secret, api.apiPath, api.params, api.charset);
		
	    api.params.put("sign", sign);
		

		
		int connectTimeout=3000;
		int readTimeout=5000;
		String response = "";//
		
		 if(api.fileParams!=null && api.fileParams.size()>0){
			 response = WebUtils.doPost(url, api.params, api.fileParams, api.charset, connectTimeout, readTimeout);
		 }else if("POST".equalsIgnoreCase(api.method)){
			 response = WebUtils.doPost(url, api.params, api.charset, connectTimeout, readTimeout);
		 }else if("GET".equalsIgnoreCase(api.method)){
			 response = WebUtils.doGet(url, api.params, api.charset);
		 }else{
			 throw new RuntimeException("method must be get or post");
		 }

		 System.out.println(response);
		 
		 return response;
	}
	


	/* ���ǩ��
     * @param method HTTP���󷽷� "get" / "post"
     * @param url_path CGI����, 
     * @param params URL�������
     * @param secret ��Կ
     * @return ǩ��ֵ
     * @throws OpensnsException ��֧��ָ�������Լ���֧��ָ���ļ��ܷ���ʱ�׳��쳣��
     */
    private String makeSign(String method, String secret, String apiPath, Map<String, String> params, String charset) throws OpenApiException{
    	String sig ="";
        try{
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(charset), mac.getAlgorithm());
            mac.init(secretKey);
            String mk = makeSource(method, apiPath, params, charset);
            System.out.println("���ڼ���sign��Դ����"+mk);
            byte[] hash = mac.doFinal(mk.getBytes(charset));
            sig = new String(Base64Coder.encode(hash));
//            sig = encodeUrl(sig);
        }catch(Exception e){
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
    private String encodeUrl(String input, String charset) throws OpenApiException{
        try{
            return URLEncoder.encode(input, charset).replace("+", "%20").replace("*", "%2A");
        }catch(UnsupportedEncodingException e){
            throw new OpenApiException(OpenApiException.MAKE_SIGNATURE_ERROR, e);
        }
    }

    /* ���ǩ������Դ��
     * @param method HTTP���󷽷� "get" / "post"
     * @param url_path CGI����, 
     * @param params URL�������
     * @return ǩ������Դ��
     */
    private String makeSource(String method, String url_path, Map<String, String> params, String charset) throws OpenApiException, UnsupportedEncodingException{
        Object[] keys = params.keySet().toArray();
        Arrays.sort(keys);  
        StringBuilder buffer = new StringBuilder(128);
        buffer.append(method.toUpperCase()).append("&").append(encodeUrl(url_path, charset)).append("&");
        StringBuilder buffer2= new StringBuilder();
        for(int i=0; i<keys.length; i++){  
            buffer2.append(keys[i]).append("=").append(params.get(keys[i]));
            if (i!=keys.length-1){
                buffer2.append("&");
            }
        }   
        System.out.println("��������в���"+buffer2);
        buffer.append(encodeUrl(buffer2.toString(),charset));
        return buffer.toString();
    }
    
    
    
	
	public String getAppOAuthID() {
		return appOAuthID;
	}

	public void setAppOAuthID(String appOAuthID) {
		this.appOAuthID = appOAuthID;
	}

	public String getAppOAuthkey() {
		return appOAuthkey;
	}

	public void setAppOAuthkey(String appOAuthkey) {
		this.appOAuthkey = appOAuthkey;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public long getUin() {
		return uin;
	}

	public void setUin(long uin) {
		this.uin = uin;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getApiPath() {
		return apiPath;
	}

	public void setApiPath(String apiPath) {
		this.apiPath = apiPath;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getRandomValue() {
		return randomValue;
	}

	public void setRandomValue(String randomValue) {
		this.randomValue = randomValue;
	}
	
}
