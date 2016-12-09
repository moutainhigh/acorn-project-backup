/*
 * @(#)CustomeAuthenticationFailureHandler.java 1.0 2013-7-26上午9:53:41
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.handle;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.service.UserService;

/**
 * <dl>
 * <dt><b>Title:</b></dt>
 * <dd>
 * none</dd>
 * <dt><b>Description:</b></dt>
 * <dd>
 * <p>
 * none</dd>
 * </dl>
 * 
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-7-26 上午9:53:41
 * 
 */
public class CustomeAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler
		implements  MessageSourceAware {

	private static final Logger logger = LoggerFactory.getLogger(CustomeAuthenticationFailureHandler.class);
	
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private String defaultFailureUrl;

	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		HttpSession session = request.getSession(true);

		DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
		/*
		String uid = "";
		try {
			uid = exception.getAuthentication().getPrincipal().toString();
		} catch (Exception e) {
			logger.info(e.getMessage());
			session.setAttribute("USER_LOGIN_EXCEPTION_MSG", messages.getMessage("User.is.login"));
		}

		
		if (!StringUtils.isEmpty(uid)) {
			try {
				userService.loginFailure(uid);
			} catch (ServiceException e) {
				session.setAttribute("USER_LOGIN_CREDENTIAL", e.getMessage());
				session.setAttribute("USER_LOGIN_EXCEPTION_MSG", messages.getMessage(e.getMessage(), "User not found"));
			}
		}
		
		if (exception instanceof UsernameNotFoundException) {

			session.setAttribute("USER_LOGIN_CREDENTIAL", ((UsernameNotFoundException) exception).getExtraInformation());
			
			session.setAttribute(
					"USER_LOGIN_EXCEPTION_MSG",
					((UsernameNotFoundException) exception).getExtraInformation()
							+ messages.getMessage("User.not.found", "User not found"));

		} else if (exception instanceof BadCredentialsException && !lock) {
			session.setAttribute("USER_LOGIN_EXCEPTION_MSG", exception.getMessage());
			// super.onAuthenticationFailure(request, response, exception);
		} else if (exception instanceof SessionAuthenticationException && !lock) {
			session.setAttribute("USER_LOGIN_EXCEPTION_MSG", exception.getMessage());
			// super.onAuthenticationFailure(request, response, exception);
		} else if (exception instanceof LockedException) {
			LockedException lockedException = (LockedException) exception;
			session.setAttribute("USER_LOGIN_EXCEPTION_MSG", lockedException.getMessage());
		} else if (!lock) {
			super.onAuthenticationFailure(request, response, exception);
		}*/
		
		if(exception instanceof AuthenticationServiceException) {
			session.setAttribute("USER_LOGIN_EXCEPTION_MSG", exception.getMessage());
		}

		redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
	}

	/*
	 * (非 Javadoc) <p>Title: setMessageSource</p> <p>Description: </p>
	 * 
	 * @param messageSource
	 * 
	 * @see org.springframework.context.MessageSourceAware#setMessageSource(org.
	 * springframework.context.MessageSource)
	 */
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messages = new MessageSourceAccessor(messageSource);

	}

	/**
	 * @return the defaultFailureUrl
	 */
	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	/**
	 * @param defaultFailureUrl
	 *            the defaultFailureUrl to set
	 */
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

}
