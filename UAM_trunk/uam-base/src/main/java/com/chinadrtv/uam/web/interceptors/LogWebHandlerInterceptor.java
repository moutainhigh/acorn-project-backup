package com.chinadrtv.uam.web.interceptors;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chinadrtv.uam.utils.RequestUUIDHolder;
import com.chinadrtv.uam.utils.StopWatchHolder;

/**
 * 
 * @author Qianyong,Deng
 * @since Oct 8, 2012
 *
 */
public class LogWebHandlerInterceptor implements HandlerInterceptor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(LogWebHandlerInterceptor.class);
	
	private static final String DELIMITERS = ",; \t\n";
	
	private static final String[] DEFAULT_EXCLUDE_STRINGS = {
			"org.springframework", "spring_security", ".FILTERED", "javax.servlet" };
	
	private String[] excludeStrings = DEFAULT_EXCLUDE_STRINGS;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod) handler;
			String call = hm.getBean().getClass().getName() + "." + hm.getMethod().getName() + "()";
			logger.info("***** {} ***** Request [{}] for URI ({}).",
					new Object[] { RequestUUIDHolder.get(), call, request.getRequestURI() });
		} else {
			logger.info("***** {} ***** Process for URI ({}).",
					RequestUUIDHolder.get(), request.getRequestURI());
		}
		
		StringBuilder sb = new StringBuilder();
		Iterator<Entry<String, String[]>> iter = request.getParameterMap().entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, String[]> entry = iter.next();
			write(sb, entry.getKey(), entry.getValue());
		}
		logger.info("***** {} ***** Parameters of request : ({}). ", RequestUUIDHolder.get(), sb.toString());
		
		sb = new StringBuilder();
		for (Enumeration<String> en = request.getAttributeNames(); en.hasMoreElements();) {
			String key = en.nextElement();
			if (isExclude(key))
				continue; // 有非用户字段跳过
			write(sb, key, request.getAttribute(key));
		}
		logger.info("***** {} ***** Attributes of request : ({}). ", RequestUUIDHolder.get(), sb.toString());
		
		StopWatchHolder.start();
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object handler, ModelAndView modelAndView) throws Exception {
		long invokeTime = StopWatchHolder.getTime();
		logger.info("***** {} ***** Invoke method completed in {} ms  ", 
				RequestUUIDHolder.get(), invokeTime);
		StopWatchHolder.reset();
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		long renderTime = StopWatchHolder.getTime();
		logger.info("***** {} ***** Render view completed in {} ms  ", 
				RequestUUIDHolder.get(), renderTime);
		
		// 页面渲染完成后删除请求上下文的UUID
		RequestUUIDHolder.remove();
		StopWatchHolder.remove();
	}
	
	public void setExcludeString(String str) {
		if (StringUtils.isNotEmpty(str)) {
			excludeStrings = org.springframework.util.StringUtils
					.tokenizeToStringArray(str, DELIMITERS, true, true);
		}
	}
	
	// 文本输出
	private void write(StringBuilder sb, String key, Object value) {
		sb.append("[");
		sb.append(key);
		sb.append(" : ");
		sb.append(isArray(value) ? writeArray(value) : value);
		sb.append("]");
	}
	
	// 将数组的各个元素值输出
	private String writeArray(Object value)	{
		StringBuilder sb = new StringBuilder();
		int length = Array.getLength(value);
		for (int pos = 0; pos < length; pos++) {
			sb.append(Array.get(value, pos));
			sb.append(pos == length -1 ? "" : ",");
		}
		return sb.toString();
	}
	
	// 判断是否是数组
	private boolean isArray(Object value) {
		return value != null && value.getClass().isArray();
	}

	// 判断是否是应该排除的字段
	private boolean isExclude(String name) {
		for (String key : excludeStrings) {
			if (StringUtils.contains(name, key)) return true;
		}
		return false;
	}
	
}
