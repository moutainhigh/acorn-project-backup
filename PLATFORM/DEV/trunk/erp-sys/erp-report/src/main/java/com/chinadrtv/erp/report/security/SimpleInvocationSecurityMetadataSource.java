package com.chinadrtv.erp.report.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import com.chinadrtv.erp.report.entity.User;
import com.chinadrtv.erp.report.service.UserService;
import com.chinadrtv.erp.report.util.CommonDefined;

/**
 * 资源源数据定义，即定义某一资源可以被哪些角色访问
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 */
@Service("securityMetadataSource")   
public class SimpleInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	protected final Log logger = LogFactory.getLog(this.getClass());
	
	private final static String USERNAME_KEY = "r_username";
	private final static String PASSWORD_KEY = "r_password";
	
	@Autowired
	private UserService userService;
	
	private AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

    /**
     * 构造函数
     */
    public SimpleInvocationSecurityMetadataSource() {
        loadResourceDefine();
    }

    /**
     * 
     */
    private void loadResourceDefine() {
//        resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
//        Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>();
//        ConfigAttribute ca = new SecurityConfig("ROLE_TEST");
//        atts.add(ca);
//        resourceMap.put("/index.jsp", atts);
//        resourceMap.put("/i.jsp", atts);
    }

    /* (non-Javadoc)
     * @see org.springframework.security.access.SecurityMetadataSource#getAttributes(java.lang.Object)
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException{
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if(authentication!=null && "anonymousUser".equals(authentication.getPrincipal())){
    		FilterInvocation fi = (FilterInvocation) object;
    		HttpServletRequest request = fi.getHttpRequest();
    		String username = request.getParameter(USERNAME_KEY);
    		String password = request.getParameter(PASSWORD_KEY);
            User user = userService.findByUsernameAndPassword(username,password);   
            if (user!=null) {   
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword());
                authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authRequest);
            }else{
        		String url = request.getRequestURI();
        		String query = request.getQueryString();
        		if(url!=null && !"".equals(url) && !"/".equals(url)){
        			if(query!=null && !"".equals(query)){
        				url += "?" + query;
        			}
        			request.getSession().setAttribute(CommonDefined.PRE_REQUEST, url);
        		}
            	logger.debug("user not found");
            }
    	}

        return this.getAllConfigAttributes();
    }

    /* (non-Javadoc)
     * @see org.springframework.security.access.SecurityMetadataSource#supports(java.lang.Class)
     */
    public boolean supports(Class<?> clazz) {
        return true;
    }
    
    /* (non-Javadoc)
     * @see org.springframework.security.access.SecurityMetadataSource#getAllConfigAttributes()
     */
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

}
