package com.chinadrtv.uam.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Utility for spring application context.
 * 
 * @author Qianyong,Deng
 * @since Sep 24, 2012
 *
 */
@Component
@SuppressWarnings("unchecked")
public final class ApplicationContextHelper implements ApplicationContextAware {

	private static ApplicationContext context;
	
	private ApplicationContextHelper() {
	}
	
	/**
	 * Get Bean
	 * 
	 * @see {@link ApplicationContext#getBean(Class)}
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(Class<T> requiredType) {
		try {
			return context.getBean(requiredType);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * Get Bean
	 * 
	 * @see {@link ApplicationContext#getBean(String, Class)}
	 * @param name
	 * @param requiredType
	 * @return
	 */
	public static <T> T getBean(String name, Class<T> requiredType) {
		try {
			return context.getBean(name, requiredType);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * Get Bean
	 * 
	 * @see {@link ApplicationContext#getBean(String)}
	 * @param name
	 * @return
	 */
	public static <T> T getBean(String name) {
		try {
			return (T) context.getBean(name);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * Get Bean
	 * 
	 * @see {@link ApplicationContext#getBean(String, Object...)}
	 * @param name
	 * @param args
	 * @return
	 */
	public static <T> T getBean(String name, Object... args) {
		try {
			return (T) context.getBean(name, args);
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * Get application context
	 * 
	 * @return
	 */
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}

}
