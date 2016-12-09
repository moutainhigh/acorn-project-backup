package com.chinadrtv.erp.jms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/***
 * 
 * 
 * @author zhaizy
 * @version 1.0
 * @since 2013-3-6 下午3:05:05
 * 
 */

@Controller
public class IndexController {
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

	/***
	 * 
	* @Description: TODO
	* @param xml
	* @param response
	* @throws IOException
	* @return void
	* @throws
	 */
	@RequestMapping(value = "/index")
	public String main(HttpServletResponse response)
			throws IOException {
		
		return "index";
	}

	
}