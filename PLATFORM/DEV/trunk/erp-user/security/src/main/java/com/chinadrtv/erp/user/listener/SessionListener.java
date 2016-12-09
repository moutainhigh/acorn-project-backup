/*
 * @(#)SessionListener.java 1.0 2013-12-26下午3:37:58
 *
 * 橡果国际-平台开发组
 * Copyright (c) 2012-2013 ACORN, Inc. All rights reserved.
 */
package com.chinadrtv.erp.user.listener;

import java.util.logging.Logger;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import com.chinadrtv.erp.user.handle.SalesSessionRegistry;
import com.chinadrtv.erp.util.SpringUtil;

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
 * @author zhaizhanyi
 * @version 1.0
 * @since 2013-12-26 下午3:37:58 
 * 
 */
public class SessionListener implements HttpSessionListener {

	
	 private static final Logger logger = Logger.getLogger(SessionListener.class.getName());
	 
	/* (非 Javadoc)
	* <p>Title: sessionCreated</p>
	* <p>Description: </p>
	* @param arg0
	* @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	*/ 
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		logger.info("into sessionCreated======+++++++=========");
		boolean ismark = true;
		SalesSessionRegistry sessionRegistry = (SalesSessionRegistry)SpringUtil.getBean("sessionRegistry");
		
		Object object = event.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
		if(object == null){
			logger.info("SPRING_SECURITY_CONTEXT is null======+++++++=========");
		}
		String username= getUserName(object);
		logger.info(username+":sessionCreated======+++++++=========");
        for(Object principal: sessionRegistry.getAllPrincipals()){
            if( ((UserDetails)principal).getUsername().equals(username) ){
                ismark=false;
            }
        }
        
        if(ismark){
        	if((SecurityContext)object instanceof SecurityContext ){
        		Authentication au=((SecurityContextImpl)object).getAuthentication();
	            logger.info(au.toString());
	            logger.info(au.getPrincipal().toString());
	            
	            logger.info(username+":append sessionRegistry======+++++++=========");
	            sessionRegistry.registerNewSession(event.getSession().getId(), au.getPrincipal());
	        }

	        
        	
        }else{
        	logger.info(ismark+":ismark======+++++++=========");
        }
        
		
		
	}

	/* (非 Javadoc)
	* <p>Title: sessionDestroyed</p>
	* <p>Description: </p>
	* @param arg0
	* @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	*/ 
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	 public String getUserName(Object object){

	        String username = "";
	        Authentication au = null;
	        if((SecurityContext)object instanceof SecurityContext ){
	            au=((SecurityContextImpl)object).getAuthentication();
	            logger.info(au.toString());
	            logger.info(au.getPrincipal().toString());
	        }
	        if(au.getPrincipal()   instanceof   UserDetails){
	            username   =   ((UserDetails) au.getPrincipal()).getUsername();
	        }

	        return username;
	    }
}
