package com.chinadrtv.erp.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CommonController {
	
//	@Autowired
//	PostPriceRemoteService postPriceRemoteService;

	/**
	 * 登录
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login")
	public String login(Model model){
		return "account/login";
	}
	
	/**
	 * 登录校验
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/home")
	public String loginValidation(Model model){
		return "account/home";
	}
	
	/**
	 * 注册
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/register")
	public String register(Model model){
		return "account/register";
	}
	
}
