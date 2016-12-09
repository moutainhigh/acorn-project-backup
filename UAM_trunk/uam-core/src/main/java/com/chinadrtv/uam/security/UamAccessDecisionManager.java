package com.chinadrtv.uam.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

public class UamAccessDecisionManager implements AccessDecisionManager {

	@Override
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
		for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext();) {
			ConfigAttribute configAttribute = iter.next();
			String needPermission = configAttribute.getAttribute();
			
			//用户所拥有的权限authentication
			for(GrantedAuthority ga : authentication.getAuthorities()) {
				if(needPermission.trim().equals(ga.getAuthority().trim())) {
					return;
				}
			}
		}
		
		throw new AccessDeniedException("No permission to access the resource");

	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

}
