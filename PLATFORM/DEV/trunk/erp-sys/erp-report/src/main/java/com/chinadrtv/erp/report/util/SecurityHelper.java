package com.chinadrtv.erp.report.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.InetOrgPerson;

import com.chinadrtv.erp.report.entity.User;

public class SecurityHelper {

    /**
     * 获取登录用户
     * @return
     */
    public static User getLoginUser() {
    	User user = null;
        if (SecurityContextHolder.getContext() == null || SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof String && principal.equals("anonymousUser")) {
            return null;
        }
        if(principal instanceof InetOrgPerson){
        	user = new User();
        	InetOrgPerson person= (InetOrgPerson)principal;
        	user.setId(person.getUid());
        	user.setName(person.getUsername());
        }else{
        	user = (User) principal;
        }
        return user;
    }

    /**
     * 是否登录
     * @return
     */
    public static boolean isLosgin() {
        return SecurityContextHolder.getContext() == null ||
                SecurityContextHolder.getContext().getAuthentication() == null ||
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null;
    }
}
