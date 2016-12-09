/*
 * $Id: BaseController.java,v 1.0 May 14, 2012 4:21:37 PM william.xu Exp $
 *
 * Copyright 2012 Asiainfo-Linkage Technologies(China),Inc. All rights reserved.
 */
package com.chinadrtv.erp.marketing.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.chinadrtv.erp.marketing.util.StringUtil;
import com.chinadrtv.erp.user.util.SecurityHelper;
import com.chinadrtv.erp.util.CodecUtils;

/**
 * @author william.xu
 * @version $Id: v 1.0 Exp $
 * Created on May 14, 2012 4:21:37 PM
 */
@Controller
public class BaseController extends MultiActionController {
	protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	public static final String LIST = "list";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	public static final String ADD = "add";
	public static final String DETAIL = "detail";
	public static final String ERROR = "error";
	
	
	public Map getParameterMap(HttpServletRequest request){
		Map paramMap = new HashMap();
		Enumeration enum1=request.getParameterNames();
		while(enum1.hasMoreElements()){
			String key=(String)enum1.nextElement();
			paramMap.put(key, inEncodingURL(convertToUTF8(request.getParameter(key))));
		}
		logger.debug("request map = " + paramMap);
		return paramMap;
	}
	
	public static String convertToUTF8(String s) {
		if ((s == null) || (s.length() == 0)){
			return s;
		}  
	    try{
	    	byte[] b = s.getBytes("ISO8859_1");
	    	for (int i = 0; i < b.length; i++){
				if (b[i] + 0 < 0){
					return new String(b, "UTF-8");
				}
	    	}
	    	b = s.getBytes("UTF-8");
	    	for (int i = 0; i < b.length; i++){
	    		if (b[i] + 0 < 0){
	    			return new String(b, "UTF-8");
	    		}
	    	} 
	    } catch (Exception e) {
	    }
	    return s;
	}
	
	public static String inEncodingURL(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8"); } catch (UnsupportedEncodingException e) {
		}
		return url;
	}
	
	
	protected String getUserId(HttpServletRequest request){
		return SecurityHelper.getLoginUser().getUserId();
	}
	
//	protected String getUserName(HttpServletRequest request){
//		return SecurityHelper.getLoginUser().getUserId();
//	}
	
	protected String getDepartmentId(HttpServletRequest request){
		return SecurityHelper.getLoginUser().getDepartment();
	}
	
	public Integer getCompanyId(HttpServletRequest request){
		return 1;
	}
	
	
	public String getReturnUrlEncode(HttpServletRequest request){
		String returnUrl="";
		
		returnUrl = request.getRequestURI();
		String queryString = request.getQueryString();
		if(!StringUtil.isNullOrBank(queryString)){
			returnUrl += "?" + queryString;
		}
		
		return CodecUtils.fuzzify(returnUrl);
	}
	
	@RequestMapping("exception")     
	public void throwException() {     
	    throw new RuntimeException("This is the runtime exception");     
	}  
	         
/*
	@ExceptionHandler(Exception.class)     
	public @ResponseBody String handleException(Exception ex) {
		logger.debug(ex.getMessage());
		ex.printStackTrace();
		return  "error";  
	}
*/	
//	@InitBinder  
//	protected void initBinder(HttpServletRequest request,  
//	            ServletRequestDataBinder binder) throws Exception {   
//	      DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//	      DateTypeEditor dateEditor = new DateTypeEditor();  
//	      binder.registerCustomEditor(Date.class, dateEditor);  
//	      super.initBinder(request, binder);   
//	}
	
}
