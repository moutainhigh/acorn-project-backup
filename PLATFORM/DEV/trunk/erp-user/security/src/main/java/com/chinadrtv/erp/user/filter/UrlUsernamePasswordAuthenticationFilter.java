package com.chinadrtv.erp.user.filter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import com.chinadrtv.erp.util.Base64Encryptor;
import com.chinadrtv.erp.util.IpUtils;

/**
 * 
 * @author dengqianyong
 *
 */
public class UrlUsernamePasswordAuthenticationFilter extends GenericFilterBean {
	
	private AuthenticationManager authenticationManager;

	@Override
    public void afterPropertiesSet() {
        Assert.notNull(authenticationManager, "authenticationManager must be specified");
    }
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        
        String decryptString = request.getQueryString();
		
        // 用户没登录并且带有加密字符串时拦截请求
		if (SecurityContextHolder.getContext().getAuthentication() == null
				&& StringUtils.isNotEmpty(decryptString)) {
			
        	
        	String[] arr = null;
        	if (StringUtils.isNotBlank(decryptString)) {
        		try {
        			arr = StringUtils.split(Base64Encryptor.decrypt(decryptString), "|");;
				} catch (Exception e) {
				}
        	}
        	
        	if (arr != null && arr.length == 4) {
        		if (isSameIp(arr[2], request) && isSameDay(arr[3])) { // 只处理同一IP并且同一天来的请求
        			String username = arr[0];
                	String password;
	        		try {
	        			password = Base64Encryptor.decrypt(arr[1]);
	        		} catch (Exception e1) {
	        			password = "";
	        		}
	        		
	        		Authentication authentication = new UsernamePasswordAuthenticationToken(
		        			username.trim(), password.trim());
		
		        	try {
		        		authentication = authenticationManager.authenticate(authentication);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					} catch (AuthenticationException e) {
						
					}
        		}
        	}
        }
        
        chain.doFilter(request, response);
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}
	
	private boolean isSameIp(String urlIp, HttpServletRequest request) {
		String requestIp = IpUtils.getRequestIp(request);
		return requestIp != null && requestIp.equals(urlIp);
	}
	
	private boolean isSameDay(String urlDay) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String day = format.format(new Date());
		return urlDay != null && urlDay.equals(day);
	}
}
