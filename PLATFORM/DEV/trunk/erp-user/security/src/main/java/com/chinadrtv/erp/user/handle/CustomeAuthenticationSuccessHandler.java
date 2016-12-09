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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;
import com.chinadrtv.erp.util.StringUtil;

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
public class CustomeAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
		implements  MessageSourceAware {

	private static final Logger logger = LoggerFactory.getLogger(CustomeAuthenticationSuccessHandler.class);
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

	private String defaultFailureUrl;

	@Autowired
	private UserService userService;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	 
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	            Authentication authentication) throws IOException, ServletException {

		String result = null;
		HttpSession session = request.getSession(true);
		AgentUser agentUser = (AgentUser) authentication.getPrincipal();
		try {
			result = userService.loginSuccess(agentUser.getUserId());
		} catch (ServiceException e) {
			session.setAttribute("USER_LOGIN_CREDENTIAL", e.getMessage());
			session.setAttribute("USER_LOGIN_EXCEPTION_MSG", messages.getMessage(e.getMessage(), "User not found"));

			redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
		}
		
		if (!StringUtil.isNullOrBank(result)) {
			if (result.equals("ppolicy.change.after.reset") || result.equals("ppolicy.expired")) {
				session.setAttribute("MUST_CHANGE_PWD_FLAG", "Y");
			}
			session.setAttribute("MUST_CHANGE_PWD", result);
			session.setAttribute("USER_LOGIN_EXCEPTION_MSG", messages.getMessage(result, result));
		}

		handle(request, response, authentication);
		clearAuthenticationAttributes(request);

		logger.info(agentUser.getUserId() + " is login success");
	}

	/* (非 Javadoc)
	* <p>Title: setMessageSource</p>
	* <p>Description: </p>
	* @param messageSource
	* @see org.springframework.context.MessageSourceAware#setMessageSource(org.springframework.context.MessageSource)
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
	 * @param defaultFailureUrl the defaultFailureUrl to set
	 */
	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	
}
