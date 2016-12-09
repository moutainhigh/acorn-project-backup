/*
 * @(#)UamAuthenticationProvider.java 1.0 2014年4月25日下午2:16:58
 *
 * 橡果国际-系统集成部
 * Copyright (c) 2012-2014 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.handle;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import com.chinadrtv.erp.exception.service.ServiceException;
import com.chinadrtv.erp.user.model.AgentUser;
import com.chinadrtv.erp.user.service.UserService;

/**
 * <dl>
 *    <dt><b>Title:</b></dt>
 *    <dd>
 *    	none
 *    </dd>
 *    <dt><b>Description:</b></dt>
 *    <dd>
 *    	<p>none
 *    </dd>
 * </dl>
 *
 * @author andrew
 * @version 1.0
 * @since 2014年4月25日 下午2:16:58 
 * 
 */
public class UamAuthenticationProvider implements AuthenticationProvider {
	
	private static transient final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(UamAuthenticationProvider.class);

	@Autowired
	private UserService userService;

	@SuppressWarnings("unchecked")
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userId = authentication.getName();
		String password = authentication.getCredentials().toString();
		AgentUser agentUser = null;
		
		try {
			agentUser = userService.login(userId, password);
			
			if(null == agentUser || null == agentUser.getUserId() || "".equals(agentUser.getUserId())) {
				logger.error("validate user " + userId + " failed ");
				throw new AuthenticationServiceException("Bad credentials:" + userId);
			}
			Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) agentUser.getAuthorities();

			return new UsernamePasswordAuthenticationToken(agentUser, password, authorities);
		} catch (ServiceException e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}
	}


	/** 
	 * <p>Title: supports</p>
	 * <p>Description: </p>
	 * @param authentication
	 * @return
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */ 
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
