/**
 * 
 */
package com.chinadrtv.uam.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;

import com.chinadrtv.uam.model.auth.Role;
import com.chinadrtv.uam.model.auth.res.ResAction;
import com.chinadrtv.uam.service.ResourceService;

/**
 * @author dengqianyong
 *
 */
public class UamMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private ResourceService resourceService;
	
	private boolean allowAnonymousIfProtectedResourceNotFound = false;
	
	private static final Collection<ConfigAttribute> denyAttrs = new ArrayList<ConfigAttribute>();
	static {
		denyAttrs.add(new SecurityConfig("ACCESS_DENY"));
	}
	
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		Collection<ConfigAttribute> results = new HashSet<ConfigAttribute>();
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		
		Map<String, Collection<ConfigAttribute>> resourcesMap = loadResourcesDefinition();
		for (Iterator<String> iter = resourcesMap.keySet().iterator();iter.hasNext();) {
			String protectedUrl = iter.next();
            if(new AntPathRequestMatcher(protectedUrl).matches(request)) {
            	results.addAll(resourcesMap.get(protectedUrl));
            }
		}
		
		if (results.size() != 0) 
			return results;
		return allowAnonymousIfProtectedResourceNotFound ? results : denyAttrs;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
	private Map<String, Collection<ConfigAttribute>> loadResourcesDefinition() {
		Map<String, Collection<ConfigAttribute>> resourcesMap = new HashMap<String, Collection<ConfigAttribute>>();
		
		List<ResAction> actions = resourceService.loadUamResources();
		for (ResAction action : actions) {
			List<ConfigAttribute> attrs = new ArrayList<ConfigAttribute>();
			for (Role role : action.getRoles()) {
				attrs.add(new SecurityConfig(role.getName()));
			}
			resourcesMap.put(action.getActionUrl(), attrs);
		}
		return resourcesMap;
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	/**
	 * 是否允许在受保护资源没有配置的情况下，可匿名访问该资源<br>
	 * 如果允许，无需登录可访问没有配置的资源<br>
	 * 否则，访问该资源时将被拒绝访问
	 * 
	 * @param isAllowed
	 */
	public void setAllowAnonymousIfProtectedResourceNotFound(boolean isAllowed) {
		this.allowAnonymousIfProtectedResourceNotFound = isAllowed;
	}
	
}
