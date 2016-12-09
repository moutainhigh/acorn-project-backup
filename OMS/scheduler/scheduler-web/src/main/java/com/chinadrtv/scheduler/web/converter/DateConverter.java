package com.chinadrtv.scheduler.web.converter;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * @author kevin
 * @since 2013-5-3
 * @version 1.0.0
 */
public class DateConverter implements WebBindingInitializer {

	/*
	 * spring3 mvc 的日期传递[前台-后台]bug:
	 * 
	 * @see
	 * org.springframework.web.bind.support.WebBindingInitializer#initBinder
	 * (org.springframework.web.bind.WebDataBinder,
	 * org.springframework.web.context.request.WebRequest)
	 */
	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.registerCustomEditor(Date.class, new DateEditor());

	}

}
