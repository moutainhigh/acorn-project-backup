package com.chinadrtv.erp.sales.controller;


import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: liuhaidong
 * Date: 13-5-14
 * Time: 下午4:41
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class IndexController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	






    @RequestMapping("/home/home")
    public ModelAndView home() throws Exception {
    	ModelAndView mav = new ModelAndView("home/home");

        return mav;
    }



    @RequestMapping("/test")
    public String test() throws Exception {
        return "common/test";
    }


    
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,
			@ModelAttribute("BASE64_PASSWORD") String password)
			throws Exception {
		logger.info("index is display");

		ModelAndView mav = new ModelAndView("index");



		return mav;
	}
    






}