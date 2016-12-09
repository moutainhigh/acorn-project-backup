package com.chinadrtv.uam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	@RequestMapping(value = "/index")
	public String viewIndex() {
		return "default";
	}
	
	@RequestMapping(value = "/login")
	public String viewLoginPage() {
		return "login";
	}

	@RequestMapping(value = "/home")
	public String viewHomePage() {
		return "home/home";
	}

}
