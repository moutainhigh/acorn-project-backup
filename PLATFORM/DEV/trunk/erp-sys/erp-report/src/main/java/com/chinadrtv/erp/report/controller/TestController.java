package com.chinadrtv.erp.report.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("test")
public class TestController {
	
	/**
	 * debug
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/debug")
	public String debug(Model model){
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("username", userDetails.getUsername());
		return "test/debug";
	}
	
	/**
	 * debug a
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/a")
	public String a(Model model){
		return "test/a";
	}
	
	/**
	 * debug b
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/b")
	public String b(Model model){
		return "test/b";
	}

	/**
	 * debug c
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/c")
	public String c(Model model){
		return "test/c";
	}
	
	/**
	 * debug d
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/d")
	public String d(Model model){
		return "test/d";
	}
	
}
