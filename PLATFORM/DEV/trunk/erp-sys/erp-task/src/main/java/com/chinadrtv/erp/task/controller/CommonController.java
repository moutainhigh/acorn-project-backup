package com.chinadrtv.erp.task.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chinadrtv.erp.task.quartz.service.SchedulerService;
import com.chinadrtv.erp.task.service.PostPriceService;
import com.chinadrtv.erp.task.service.UserService;

@Controller
public class CommonController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	UserService userService;
	
	@Autowired
	PostPriceService postPriceService;
	
	@Autowired
	private SchedulerService schedulerService;
	
	@RequestMapping(value="/home")
	public String home(Model model){
		return "home";
	}
	
	@RequestMapping(value="/triggerList")
	public String triggerList(Model model){
		return "triggerList";
	}
	
	@RequestMapping(value="/jobClassList")
	public String jobClassList(Model model){
		return "jobClassList";
	}

}
