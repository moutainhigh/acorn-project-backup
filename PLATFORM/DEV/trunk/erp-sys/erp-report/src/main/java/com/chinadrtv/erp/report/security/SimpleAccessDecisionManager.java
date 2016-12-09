package com.chinadrtv.erp.report.security;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 访问决策器，决定某个用户具有的角色，是否有足够的权限去访问某个资源
 * @author zhangguosheng
 */
@Service("simpleAccessDecisionManager")   
public class SimpleAccessDecisionManager implements AccessDecisionManager {
	
	protected final Log logger = LogFactory.getLog(this.getClass());

    /* (non-Javadoc)
     * @see org.springframework.security.access.AccessDecisionManager#decide(org.springframework.security.core.Authentication, java.lang.Object, java.util.Collection)
     */
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if(configAttributes == null){
            return ;
        }
        logger.debug(object.toString());
        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        while(ite.hasNext()){
            ConfigAttribute ca = ite.next();
            String needRole = ((SecurityConfig)ca).getAttribute();
            for(GrantedAuthority ga : authentication.getAuthorities()){
                if(needRole.equals(ga.getAuthority())){  //ga is user's role.
                    return;
                }
            }
        }
        throw new AccessDeniedException("no right");
    }

    /* (non-Javadoc)
     * @see org.springframework.security.access.AccessDecisionManager#supports(org.springframework.security.access.ConfigAttribute)
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    /* (non-Javadoc)
     * @see org.springframework.security.access.AccessDecisionManager#supports(java.lang.Class)
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }


}

